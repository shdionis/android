package ru.montgolfiere.searchquest.config

import android.content.Context
import android.content.Context.MODE_PRIVATE
import java.util.concurrent.TimeUnit

class QuestConfig(context: Context) {

    private val prefs = context.getSharedPreferences("quest-config", MODE_PRIVATE)

    var currentStepId: Int
        get() = prefs.getInt(CURRENT_STEP_ID_PREF, INVALID_STEP_ID)
        set(value) {
            prefs.edit()
                .putInt(CURRENT_STEP_ID_PREF, value)
                .remove(CURRENT_STEP_HINT_ID_PREF)
                .apply()
            updateHintUsageTime()
        }
    var currentStepHintId: Int
        get() = prefs.getInt(CURRENT_STEP_HINT_ID_PREF, 0)
        set(value) {
            prefs.edit().putInt(CURRENT_STEP_HINT_ID_PREF, value).apply()
        }

    fun updateHintUsageTime() {
        prefs.edit().putLong(LAST_TIME_USAGE_HINT_PREF, System.currentTimeMillis()).apply()
    }

    fun getHintUsageTime(): Long {
        val lastUsageTime = prefs.getLong(LAST_TIME_USAGE_HINT_PREF, 0L)
        return lastUsageTime + HINT_COOL_DOWN_THRESHOLD
    }


    companion object {
        private const val CURRENT_STEP_ID_PREF = "current_step_id"
        private const val CURRENT_STEP_HINT_ID_PREF = "current_step_hint_id"
        private const val LAST_TIME_USAGE_HINT_PREF = "last_time_usage_hint"
        private val HINT_COOL_DOWN_THRESHOLD = TimeUnit.MINUTES.toMillis(15)
        const val INVALID_STEP_ID = -1
    }
}