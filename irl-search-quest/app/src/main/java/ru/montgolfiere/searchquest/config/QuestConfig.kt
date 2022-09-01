package ru.montgolfiere.searchquest.config

import android.content.Context
import android.content.Context.MODE_PRIVATE
import java.util.concurrent.TimeUnit

class QuestConfig(context: Context) {

    private val prefs = context.getSharedPreferences("quest-config", MODE_PRIVATE)

    var currentStepId: Int
        get() = prefs.getInt(CURRENT_STEP_ID_PREF, INVALID_STEP_ID)
        set(value) {
            prefs.edit().putInt(CURRENT_STEP_ID_PREF, value).apply()
        }

    companion object {
        const val INVALID_STEP_ID = -1
        private const val CURRENT_STEP_ID_PREF = "current_step_id"
        private val HINT_COOL_DOWN_THRESHOLD = TimeUnit.MINUTES.toMillis(15)
    }
}