package ru.montgolfiere.searchquest.dagger

import dagger.Component
import ru.montgolfiere.searchquest.ContainerActivity
import ru.montgolfiere.searchquest.config.QuestConfig
import javax.inject.Singleton

@Component(modules = [
    AndroidApplicationModule::class,
    QuestModule::class
])
@Singleton
interface ApplicationComponent {
    fun getQuestConfig(): QuestConfig

    fun inject(containerActivity: ContainerActivity)
}