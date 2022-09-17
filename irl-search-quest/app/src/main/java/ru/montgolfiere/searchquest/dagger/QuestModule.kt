package ru.montgolfiere.searchquest.dagger

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.montgolfiere.searchquest.config.QuestConfig
import ru.montgolfiere.searchquest.fragments.QuestFragmentFactory
import ru.montgolfiere.searchquest.interact.QuestInteractorFactory
import ru.montgolfiere.searchquest.model.repository.QuestRepository
import ru.montgolfiere.searchquest.viewmodels.QuestViewModelFactory
import javax.inject.Singleton

@Module
class QuestModule {
    @Provides
    @Singleton
    fun provideQuestFragmentFactory(
        questViewModelFactory: QuestViewModelFactory
    ): QuestFragmentFactory {
        return QuestFragmentFactory(questViewModelFactory)
    }

    @Provides
    @Singleton
    fun provideQuestViewModelFactory(
        questInteractorFactory: QuestInteractorFactory,
    ): QuestViewModelFactory {
        return QuestViewModelFactory(questInteractorFactory)
    }

    @Provides
    @Singleton
    fun provideQuestConfig(
        context: Context
    ): QuestConfig {
        return QuestConfig(context)
    }

    @Provides
    @Singleton
    fun provideQuestInteractor(
        repository: QuestRepository,
        config: QuestConfig
    ): QuestInteractorFactory {
        return QuestInteractorFactory(repository, config)
    }

    @Provides
    @Singleton
    fun provideQuestRepository(
        context: Context,
        config: QuestConfig,
    ): QuestRepository {
        return QuestRepository(context, config)
    }
}