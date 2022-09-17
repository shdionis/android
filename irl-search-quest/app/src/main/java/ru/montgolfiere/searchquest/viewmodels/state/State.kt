package ru.montgolfiere.searchquest.viewmodels.state

import ru.montgolfiere.searchquest.model.QuestStep

sealed class State

object ErrorState : State()

object FinishState : State()

class LoadingState(
    val data: QuestStep?
) : State()

class DataState(
    val data: QuestStep
) : State()
