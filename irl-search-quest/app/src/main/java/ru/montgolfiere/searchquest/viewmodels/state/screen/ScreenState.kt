package ru.montgolfiere.searchquest.viewmodels.state.screen

sealed class ScreenState

object MainState : ScreenState()

object HistoryState : ScreenState()

class ViewState(val id: Int) : ScreenState()

object FinishScreenState : ScreenState()
