package ru.montgolfiere.searchquest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.montgolfiere.searchquest.interact.QuestInteractor
import ru.montgolfiere.searchquest.model.QuestStep
import ru.montgolfiere.searchquest.viewmodels.state.DataState
import ru.montgolfiere.searchquest.viewmodels.state.ErrorState
import ru.montgolfiere.searchquest.viewmodels.state.LoadingState
import ru.montgolfiere.searchquest.viewmodels.state.State

class QuestViewModel(
    private val questInteractor: QuestInteractor
): ViewModel() {
    private val questStepLiveData = MutableLiveData<State>()
    private var currentStep: QuestStep? = null

    init {
        viewModelScope.launch {
            questInteractor.fetchActualQuestStepData().collect {
                if (it is DataState) {
                    currentStep = it.data
                }
                questStepLiveData.value = it
            }
        }
    }

    fun getActualQuestStep(): LiveData<State> {
        return questStepLiveData
    }

    fun tryAnswer(answer: String): Boolean {
        val step = currentStep
        if (step == null) {
            questStepLiveData.value = ErrorState
            return false
        }

        val isSuccess = questInteractor.checkAnswer(step, answer)
        if (isSuccess) {
            viewModelScope.launch {
                questStepLiveData.value = LoadingState
                delay(1000)
                goToNextStep()
            }
        }
        return isSuccess
    }

    private fun goToNextStep() {
        val step = currentStep
        if (step != null) {
            questInteractor.fetchNextQuestStep(step, true)
        } else {
            questStepLiveData.value = ErrorState
        }

    }
}