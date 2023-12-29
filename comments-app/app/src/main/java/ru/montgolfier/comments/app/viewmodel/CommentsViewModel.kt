package ru.montgolfier.comments.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.montgolfier.comments.app.data.Comment
import ru.montgolfier.comments.app.domain.CommentsUseCase


class CommentsViewModel(
    private val commentsUseCase: CommentsUseCase
) : ViewModel() {
    private val _liveDataComments = MutableLiveData<CommentsUiState>()

    val liveDataComments: LiveData<CommentsUiState> = _liveDataComments


    fun loadComments() {
        viewModelScope.launch {
            commentsUseCase.getComments().collect {
                when(it) {
                    is CommentsUseCase.CommentsResult.LoadingData -> {
                        _liveDataComments.value = CommentsUiState.LoadingData
                    }
                    CommentsUseCase.CommentsResult.LoadingError -> {
                        _liveDataComments.value = CommentsUiState.Error
                    }
                    CommentsUseCase.CommentsResult.NoDataError -> {
                        _liveDataComments.value = CommentsUiState.NoData
                    }
                    is CommentsUseCase.CommentsResult.SuccessData -> {
                        _liveDataComments.value = CommentsUiState.Data(it.result)
                    }
                }
            }
        }
    }

    fun loadMoreComments() {
        viewModelScope.launch {
            commentsUseCase.loadMoreComments()
        }
    }

    sealed class CommentsUiState {
        object LoadingData: CommentsUiState()
        class Data(val result: List<Comment>): CommentsUiState()
        object NoData: CommentsUiState()
        object Error: CommentsUiState()
    }
}