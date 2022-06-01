package ru.shaden.tasktracker.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScreenSwitchViewModel : ViewModel() {
    val selectedFlow = MutableLiveData<State?>()

    init {
        selectedFlow.value = State.ProjectListState
    }

    fun switch(state: State) {
        selectedFlow.value = state
        selectedFlow.value = null
    }
}