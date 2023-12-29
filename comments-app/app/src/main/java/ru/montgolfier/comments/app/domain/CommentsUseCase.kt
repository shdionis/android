package ru.montgolfier.comments.app.domain

import android.content.res.Resources.NotFoundException
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.montgolfier.comments.app.data.Comment
import ru.montgolfier.comments.app.repository.RemoteCommentsRepository

class CommentsUseCase(
    private val repository: RemoteCommentsRepository
) {
    private val _commentsFlow = MutableStateFlow<CommentsResult>(
        CommentsResult.LoadingData
    )
    var currentPage = 0
    suspend fun getComments(): StateFlow<CommentsResult> {
        try {
            val comments = repository.getComments(currentPage)
            _commentsFlow.emit(CommentsResult.SuccessData(comments))
        } catch (notFoundEx: NotFoundException) {
            _commentsFlow.emit(CommentsResult.NoDataError)
            Log.e(TAG, "error 404", notFoundEx)
        } catch (ex: Exception) {
            _commentsFlow.emit(CommentsResult.LoadingError)
            Log.e(TAG, "error", ex)
        }
        return _commentsFlow
    }

    suspend fun loadMoreComments() {
        currentPage++
        getComments()
    }

    sealed class CommentsResult {
        object LoadingData : CommentsResult()
        data class SuccessData(val result: List<Comment>) : CommentsResult()
        object LoadingError : CommentsResult()
        object NoDataError : CommentsResult()
    }

    companion object {
        const val TAG = "CommentsUseCase"
    }
}