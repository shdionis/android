package ru.montgolfiere.searchquest.interact

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.montgolfiere.searchquest.config.QuestConfig
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
    private val questDataFlow: MutableStateFlow<State> = MutableStateFlow(LoadingState)
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    suspend fun fetchActualQuestStepData(): StateFlow<State> {
        coroutineScope.launch {
            val step = repository.getQuestStepById(config.currentStepId)
            if (step == null) {
                questDataFlow.emit(ErrorState)
            } else {
                fetchActualQuestStepDataInternal(step.id)
            }
        }
        return questDataFlow
    }

    private suspend fun fetchActualQuestStepDataInternal(id: Int) {
        coroutineScope.launch {
            config.currentStepId = id
            val step = repository.getQuestStepById(id)
            if (step == null) {
                questDataFlow.emit(ErrorState)
            } else {
                questDataFlow.emit(DataState(step))
            }
        }
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
                questDataFlow.emit(FinishState)
            }
        }
    }
}