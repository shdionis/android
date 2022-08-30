package ru.montgolfiere.searchquest.model.repository

import android.content.Context
import androidx.annotation.WorkerThread
import com.google.gson.Gson
import ru.montgolfiere.searchquest.config.QuestConfig
import ru.montgolfiere.searchquest.model.Quest
import ru.montgolfiere.searchquest.model.QuestStep

class QuestRepository(
    val context: Context,
) {
    private val questData: Quest? by lazy {
        try {
            val questStepsStream = context.assets.open("quest_steps.json")
            val size = questStepsStream.available()
            val buffer = ByteArray(size)
            questStepsStream.read(buffer)
            val quest = Gson().fromJson(String(buffer), Quest::class.java)
            quest
        } catch (ex: Exception) {
            null
        }
    }

    @WorkerThread
    fun getQuestStepById(id: Int): QuestStep {
        val quest = questData ?: throw QuestParseException()
        val stepId = if (id == QuestConfig.INVALID_STEP_ID) {
            quest.startStep
        } else {
            id
        }
        return quest.questSteps.find { it.id == stepId } ?: throw QuestStepNotFoundException()
    }

    fun getQuestCount(): Int = questData?.questSteps?.size ?: throw QuestParseException()
}