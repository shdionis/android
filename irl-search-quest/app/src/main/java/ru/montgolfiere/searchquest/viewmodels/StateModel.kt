package ru.montgolfiere.searchquest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.montgolfiere.searchquest.interact.QuestInteractor
import ru.montgolfiere.searchquest.viewmodels.state.screen.FinishScreenState
import ru.montgolfiere.searchquest.viewmodels.state.screen.MainState
import ru.montgolfiere.searchquest.viewmodels.state.screen.ScreenState

class StateModel(questInteractor: QuestInteractor) : ViewModel() {
    private val initState = if (questInteractor.isFinish()) FinishScreenState else MainState
    private val screenStateLiveData: MutableLiveData<ScreenState> = MutableLiveData(initState)
    val screenState: LiveData<ScreenState> = screenStateLiveData

    fun route(state: ScreenState) {
        screenStateLiveData.value = state
    }
}
