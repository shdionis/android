package ru.montgolfiere.searchquest.viewmodels.state

import ru.montgolfiere.searchquest.model.QuestStep

sealed class ScreenState(
    val status: StateStatus
)

class DataState(
    val data: QuestStep
) : ScreenState(StateStatus.OK)

class ErrorState(
    val cause: ErrorCauses
) : ScreenState(StateStatus.ERROR)

enum class ErrorCauses {
    NOT_FOUND,
    PARSE_ERROR
}

enum class StateStatus {
    OK, ERROR
}
