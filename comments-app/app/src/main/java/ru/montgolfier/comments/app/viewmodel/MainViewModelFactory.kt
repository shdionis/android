package ru.montgolfier.comments.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.montgolfier.comments.app.domain.CommentsUseCase

class MainViewModelFactory(
    private val commentsUseCase: CommentsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when (modelClass.name) {
            RoutingViewModel::class.java.name -> createRoutingViewModel()
            CommentsViewModel::class.java.name -> createCommentsViewModel()
            else -> super.create(modelClass)
        }

    private fun <T> createRoutingViewModel(): T = RoutingViewModel() as T
    private fun <T> createCommentsViewModel(): T = CommentsViewModel(commentsUseCase) as T
}