package ru.montgolfiere.searchquest

import android.app.Application
import ru.montgolfiere.searchquest.dagger.AndroidApplicationModule
import ru.montgolfiere.searchquest.dagger.ApplicationComponent
import ru.montgolfiere.searchquest.dagger.DaggerApplicationComponent

class QuestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent
            .builder()
            .androidApplicationModule(AndroidApplicationModule(this))
            .build()
    }

    companion object {
        lateinit var component: ApplicationComponent
    }
}