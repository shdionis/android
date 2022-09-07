package ru.montgolfiere.searchquest.dagger

import dagger.Component
import ru.montgolfiere.searchquest.ContainerActivity
import ru.montgolfiere.searchquest.config.QuestConfig
import ru.montgolfiere.searchquest.viewmodels.QuestViewModelFactory
import ru.montgolfiere.searchquest.views.BottomSheetView
import javax.inject.Singleton

@Component(modules = [
    AndroidApplicationModule::class,
    QuestModule::class
])
@Singleton
interface ApplicationComponent {
    fun getQuestConfig(): QuestConfig

    fun getQuestViewModelFactory(): QuestViewModelFactory

    fun inject(containerActivity: ContainerActivity)

    fun inject(bottomSheetView: BottomSheetView)
}