package ru.montgolfier.comments.app.di

import androidx.lifecycle.ViewModelProvider
import ru.montgolfier.comments.app.BuildConfig
import ru.montgolfier.comments.app.domain.CommentsUseCase
import ru.montgolfier.comments.app.repository.RemoteCommentsRepository
import ru.montgolfier.comments.app.view.MainFragmentFactory
import ru.montgolfier.comments.app.viewmodel.MainViewModelFactory

object StupidDi {
    private val remoteCommentsRepository: RemoteCommentsRepository by lazy {
        RemoteCommentsRepository(BuildConfig.COMMENTS_SERVER_BASE_URL)
    }
    private val commentsUseCase: CommentsUseCase by lazy { CommentsUseCase(remoteCommentsRepository) }

    val viewModelFactory: ViewModelProvider.Factory by lazy { MainViewModelFactory(commentsUseCase) }
    val fragmentFactory: MainFragmentFactory by lazy { MainFragmentFactory(viewModelFactory) }
}