package ru.montgolfiere.searchquest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.montgolfiere.searchquest.interact.QuestInteractor
import ru.montgolfiere.searchquest.viewmodels.state.State

class QuestViewModel(
    private val questInteractor: QuestInteractor
): ViewModel() {
    private val questStepLiveData = MutableLiveData<State>()

    fun getActualQuestStep(): LiveData<State> {
        viewModelScope.launch {
            questInteractor.fetchActualQuestStepData().collect {
                questStepLiveData.value = it
            }
        }
        return questStepLiveData
    }

    fun checkAnswer(answer: String): Boolean = questInteractor.checkAnswer(answer)

    fun goToNextStep() {
        questInteractor.fetchNextQuestStep()
    }
}