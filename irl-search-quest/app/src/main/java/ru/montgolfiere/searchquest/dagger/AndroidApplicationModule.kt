package ru.montgolfiere.searchquest.dagger

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AndroidApplicationModule(
    private val applicationContext: Context
) {
    @Provides
    fun provideApplicationContext(): Context {
        return applicationContext
    }
}
