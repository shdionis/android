package ru.montgolfiere.searchquest.dagger

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.montgolfiere.searchquest.config.QuestConfig
import ru.montgolfiere.searchquest.fragments.QuestFragmentFactory
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
        questConfig: QuestConfig
    ): QuestViewModelFactory {
        return QuestViewModelFactory(questConfig)
    }

    @Provides
    fun provideQuestConfig(
        context: Context
    ): QuestConfig {
        return QuestConfig(context)
    }
}