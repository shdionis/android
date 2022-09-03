package ru.montgolfiere.searchquest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.montgolfiere.searchquest.viewmodels.state.screen.MainState
import ru.montgolfiere.searchquest.viewmodels.state.screen.ScreenState

class StateModel : ViewModel() {
    private val screenStateLiveData: MutableLiveData<ScreenState> = MutableLiveData(MainState)
    val screenState: LiveData<ScreenState> = screenStateLiveData

    fun route(state: ScreenState) {
        screenStateLiveData.value = state
    }
}
