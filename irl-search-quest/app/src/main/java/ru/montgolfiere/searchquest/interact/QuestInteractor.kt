package ru.montgolfiere.searchquest.interact

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.montgolfiere.searchquest.config.QuestConfig
import ru.montgolfiere.searchquest.model.QuestStep
import ru.montgolfiere.searchquest.model.repository.QuestParseException
import ru.montgolfiere.searchquest.model.repository.QuestRepository
import ru.montgolfiere.searchquest.model.repository.QuestStepNotFoundException
import ru.montgolfiere.searchquest.viewmodels.state.DataState
import ru.montgolfiere.searchquest.viewmodels.state.ErrorCauses
import ru.montgolfiere.searchquest.viewmodels.state.ErrorState
import ru.montgolfiere.searchquest.viewmodels.state.FinishState
import ru.montgolfiere.searchquest.viewmodels.state.LoadingState
import ru.montgolfiere.searchquest.viewmodels.state.State

class QuestInteractor(
    private val repository: QuestRepository,
    private val config: QuestConfig
) {
    private val questDataFlow: MutableStateFlow<State> = MutableStateFlow(LoadingState())
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var currentStep: QuestStep

    suspend fun fetchActualQuestStepData(): StateFlow<State> {
        val id = config.currentStepId
        fetchActualQuestStepDataInternal(id)
        return questDataFlow
    }

    private suspend fun fetchActualQuestStepDataInternal(id: Int) {
        coroutineScope.launch {
            try {
                currentStep = repository.getQuestStepById(id)
                config.currentStepId = currentStep.id
                questDataFlow.emit(DataState(currentStep))
            } catch (ex: QuestParseException) {
                questDataFlow.emit(ErrorState(ErrorCauses.PARSE_ERROR))
            } catch (ex: QuestStepNotFoundException) {
                questDataFlow.emit(ErrorState(ErrorCauses.NOT_FOUND))
            }
        }
    }

    fun checkAnswer(answer: String) =
        currentStep.answersList.find { it.trim().equals(answer.trim(), true) } != null

    fun fetchNextQuestStep() {
        val nextStepId = currentStep.nextStepId
        coroutineScope.launch {
            if (nextStepId != null) {
                fetchActualQuestStepDataInternal(nextStepId)
            } else {
                questDataFlow.emit(FinishState())
            }
        }
    }
}