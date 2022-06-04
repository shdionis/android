package ru.shaden.tasktracker.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class ScreenSwitchViewModel : ViewModel() {
    private val internalStateFlow: MutableStateFlow<State?> =
        MutableStateFlow(State.ProjectListState)
    val selectedFlow: Flow<State?> = internalStateFlow

    fun switch(state: State) {
        internalStateFlow.value = state
        internalStateFlow.value = null
    }
}