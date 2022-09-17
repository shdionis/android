package ru.montgolfiere.searchquest.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.montgolfiere.searchquest.interact.QuestInteractor
import ru.montgolfiere.searchquest.model.DataResponseCode
import ru.montgolfiere.searchquest.model.Quest
import ru.montgolfiere.searchquest.model.QuestStep
import ru.montgolfiere.searchquest.viewmodels.state.DataState
import ru.montgolfiere.searchquest.viewmodels.state.ErrorState
import ru.montgolfiere.searchquest.viewmodels.state.FinishState
import ru.montgolfiere.searchquest.viewmodels.state.LoadingState
import ru.montgolfiere.searchquest.viewmodels.state.State

class QuestViewModel(
    private val questInteractor: QuestInteractor
): ViewModel() {
    private val internalQuestStepLiveData = MutableLiveData<State>(LoadingState(null))
    private val internalQuestLiveData = MutableLiveData<Quest>()
    private var currentStep: QuestStep? = null

    val questStepLiveData = internalQuestStepLiveData
    val questLiveData = internalQuestLiveData

    init {
        viewModelScope.launch {
            questInteractor.questStepFlow.collect {
                currentStep = it.questStep
                internalQuestStepLiveData.value = when(it.code) {
                    DataResponseCode.OK -> {
                        if (it.questStep != null) {
                            DataState(it.questStep)
                        } else {
                            FinishState
                        }
                    }
                    DataResponseCode.NOT_FOUND -> {
                        ErrorState
                    }
                    DataResponseCode.PROCESSING -> {
                        LoadingState(null)
                    }
                }
            }
        }
        viewModelScope.launch {
            questInteractor.questFlow.collect {
                internalQuestLiveData.value = it
            }
        }
    }

    fun tryAnswer(answer: String): Boolean {
        val step = currentStep
        if (step == null) {
            internalQuestStepLiveData.value = ErrorState
            return false
        }

        val isSuccess = questInteractor.checkAnswer(step, answer)
        if (isSuccess) {
            viewModelScope.launch {
                internalQuestStepLiveData.value = LoadingState(step)
                delay(1000)
                goToNextStep()
            }
        }
        return isSuccess
    }

    fun fetchStepById(id: Int) {
        questInteractor.fetchStepById(id)
    }

    fun fetchActualStep() {
        questInteractor.fetchActualQuestStepData()
    }

    private fun goToNextStep() {
        val step = currentStep
        if (step != null) {
            questInteractor.fetchNextQuestStep(step, true)
        } else {
            internalQuestStepLiveData.value = ErrorState
        }
    }

    fun storeQuestModel() {
        questInteractor.storeQuestModel()
    }
}