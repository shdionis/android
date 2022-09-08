package ru.montgolfiere.searchquest.interact

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.montgolfiere.searchquest.config.QuestConfig
import ru.montgolfiere.searchquest.model.Quest
import ru.montgolfiere.searchquest.model.QuestStep
import ru.montgolfiere.searchquest.model.repository.QuestRepository
import ru.montgolfiere.searchquest.viewmodels.state.DataState
import ru.montgolfiere.searchquest.viewmodels.state.ErrorState
import ru.montgolfiere.searchquest.viewmodels.state.FinishState
import ru.montgolfiere.searchquest.viewmodels.state.LoadingState
import ru.montgolfiere.searchquest.viewmodels.state.State

class QuestInteractor(
    private val repository: QuestRepository,
    private val config: QuestConfig
) {
    private val internalQuestStepStateFlow: MutableStateFlow<State> = MutableStateFlow(LoadingState)
    private val internalQuestDataFlow: MutableStateFlow<Quest> = MutableStateFlow(repository.questData)
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val questStepFlow: Flow<State> = internalQuestStepStateFlow
    val questFlow: Flow<Quest> = internalQuestDataFlow

    fun fetchActualQuestStepData(): StateFlow<State> {
        coroutineScope.launch {
            val step = repository.getQuestStepById(config.currentStepId)
            if (step == null) {
                internalQuestStepStateFlow.emit(ErrorState)
            } else {
                fetchActualQuestStepDataInternal(step.id)
            }
        }
        return internalQuestStepStateFlow
    }

    fun fetchStepById(id: Int) {
        coroutineScope.launch {
            val step = repository.getQuestStepById(id)
            if (step == null) {
                internalQuestStepStateFlow.emit(ErrorState)
            } else {
                internalQuestStepStateFlow.emit(DataState(step))
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
                internalQuestStepStateFlow.emit(FinishState)
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