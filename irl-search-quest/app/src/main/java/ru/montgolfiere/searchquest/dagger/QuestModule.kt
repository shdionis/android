package ru.montgolfiere.searchquest.dagger

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.montgolfiere.searchquest.config.QuestConfig
import ru.montgolfiere.searchquest.fragments.QuestFragmentFactory
import ru.montgolfiere.searchquest.interact.QuestInteractor
import ru.montgolfiere.searchquest.model.repository.QuestRepository
import ru.montgolfiere.searchquest.viewmodels.QuestViewModelFactory

@Module
class QuestModule {
    @Provides
    fun provideQuestFragmentFactory(
        questViewModelFactory: QuestViewModelFactory
    ): QuestFragmentFactory {
        return QuestFragmentFactory(questViewModelFactory)
    }

    @Provides
    fun provideQuestViewModelFactory(
        questInteractor: QuestInteractor,
    ): QuestViewModelFactory {
        return QuestViewModelFactory(questInteractor)
    }

    @Provides
    fun provideQuestConfig(
        context: Context
    ): QuestConfig {
        return QuestConfig(context)
    }

    @Provides
    fun provideQuestInteractor(
        repository: QuestRepository,
        config: QuestConfig
    ): QuestInteractor {
        return QuestInteractor(repository, config)
    }

    @Provides
    fun provideQuestRepository(
        context: Context,
        config: QuestConfig,
    ): QuestRepository {
        return QuestRepository(context, config)
    }
}