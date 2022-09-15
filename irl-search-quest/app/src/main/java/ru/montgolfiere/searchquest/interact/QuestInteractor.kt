package ru.montgolfiere.searchquest.interact

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.montgolfiere.searchquest.config.QuestConfig
import ru.montgolfiere.searchquest.model.DataResponseCode
import ru.montgolfiere.searchquest.model.Quest
import ru.montgolfiere.searchquest.model.QuestStep
import ru.montgolfiere.searchquest.model.QuestStepWrapper
import ru.montgolfiere.searchquest.model.repository.QuestRepository

class QuestInteractor(
    private val repository: QuestRepository,
    private val config: QuestConfig
) {
    private val internalQuestStepFlow: MutableStateFlow<QuestStepWrapper> = MutableStateFlow(
        QuestStepWrapper(null, DataResponseCode.PROCESSING)
    )
    private val internalQuestDataFlow: MutableStateFlow<Quest> =
        MutableStateFlow(repository.questData)
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val questStepFlow: Flow<QuestStepWrapper> = internalQuestStepFlow
    val questFlow: Flow<Quest> = internalQuestDataFlow

    fun fetchActualQuestStepData(): StateFlow<QuestStepWrapper> {
        coroutineScope.launch {
            val step = repository.getQuestStepById(config.currentStepId)
            if (step == null) {
                internalQuestStepFlow.emit(QuestStepWrapper(null, DataResponseCode.NOT_FOUND))
            } else {
                fetchActualQuestStepDataInternal(step.id)
            }
        }
        return internalQuestStepFlow
    }

    fun fetchStepById(id: Int) {
        coroutineScope.launch {
            val step = repository.getQuestStepById(id)
            if (step == null) {
                internalQuestStepFlow.emit(QuestStepWrapper(null, DataResponseCode.NOT_FOUND))
            } else {
                internalQuestStepFlow.emit(QuestStepWrapper(step, DataResponseCode.OK))
            }
        }
    }

    private fun fetchActualQuestStepDataInternal(id: Int) {
        config.currentStepId = id
        fetchStepById(id)
    }

    fun checkAnswer(currentStep: QuestStep, answer: String) =
        currentStep.answersList.find { it.trim().equals(answer.trim(), true) } != null

    fun fetchNextQuestStep(currentStep: QuestStep, updateStepAsDone: Boolean = false) {
        val nextStepId = currentStep.nextStepId
        coroutineScope.launch {
            if (updateStepAsDone) {
                currentStep.isDone = true
                repository.storeQuestModel()
            }
            if (nextStepId != null) {
                fetchActualQuestStepDataInternal(nextStepId)
            } else {
                config.questIsFinish = true
                internalQuestStepFlow.emit(QuestStepWrapper(null, DataResponseCode.OK))
            }
        }
    }

    fun storeQuestModel() {
        coroutineScope.launch {
            repository.storeQuestModel()
        }
    }

    fun isFinish(): Boolean = config.questIsFinish
}