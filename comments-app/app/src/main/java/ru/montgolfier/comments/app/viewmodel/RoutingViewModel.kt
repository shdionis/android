package ru.montgolfier.comments.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class RoutingViewModel : ViewModel() {
    var initState: State? = State.GoodsState
    private val _stateFlow = MutableSharedFlow<State>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val stateFlow: SharedFlow<State> = _stateFlow
    private fun changeState(state: State) {
        viewModelScope.launch {
            _stateFlow.emit(state)
            initState = null
        }
    }

    fun goToComments() {
        changeState(State.CommentsState)
    }
    sealed class State {
        object GoodsState : State()
        object CommentsState : State()
    }
}