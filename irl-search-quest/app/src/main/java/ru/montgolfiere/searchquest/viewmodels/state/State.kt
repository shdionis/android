package ru.montgolfiere.searchquest.viewmodels.state

import ru.montgolfiere.searchquest.model.QuestStep

sealed class State(
    val status: StateStatus
)

class LoadingState: State(StateStatus.LOADING)

class FinishState: State(StateStatus.OK)

class DataState(
    val data: QuestStep
) : State(StateStatus.OK)

class ErrorState(
    val cause: ErrorCauses
) : State(StateStatus.ERROR)

enum class ErrorCauses {
    NOT_FOUND,
    PARSE_ERROR
}

enum class StateStatus {
    OK, ERROR, LOADING
}
