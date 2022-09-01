package ru.montgolfiere.searchquest.viewmodels.state

import ru.montgolfiere.searchquest.model.QuestStep

sealed class State

object ErrorState : State()

object LoadingState : State()

object FinishState : State()

class DataState(
    val data: QuestStep
) : State()
