package ru.montgolfiere.searchquest.model.repository

import android.content.Context
import android.util.Log
import androidx.annotation.WorkerThread
import com.google.gson.Gson
import ru.montgolfiere.searchquest.config.QuestConfig
import ru.montgolfiere.searchquest.model.Quest
import ru.montgolfiere.searchquest.model.QuestStep
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.io.PrintWriter
import java.nio.charset.Charset

class QuestRepository(
    private val context: Context,
    private val config: QuestConfig,
) {
    private val questData: Quest by lazy {
        try {
            val currentStep = config.currentStepId
            val questStepsStream = if (currentStep != QuestConfig.INVALID_STEP_ID) {
                if (context.fileList().find { it==FILE_NAME } != null) {
                    openFileFromStorage()
                } else {
                    openFileFromAssets()
                }
            } else {
                openFileFromAssets()
            }
            val size = questStepsStream.available()
            val buffer = ByteArray(size)
            questStepsStream.read(buffer)
            val quest = Gson().fromJson(String(buffer), Quest::class.java)
            questStepsStream.close()
            quest
        } catch (ex: Exception) {
            Log.e(TAG, "read quest data failure", ex)
            Quest(-1, emptyList())
        }
    }

    @WorkerThread
    fun getQuestStepById(id: Int): QuestStep? {
        val quest = questData
        val stepId = if (id == QuestConfig.INVALID_STEP_ID) {
            quest.startStep
        } else {
            id
        }
        return quest.questSteps.find { it.id == stepId }
    }

    @WorkerThread
    fun storeQuestModel() {
        try {
            val fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)
            val str = Gson().toJson(questData)
            fos.write(str.toByteArray(Charset.defaultCharset()))
        } catch (ex: Exception) {
            Log.e(TAG, "failed update model", ex)
        }
    }

    private fun openFileFromAssets(): InputStream {
        Log.d(TAG, "open asset File")
        return context.assets.open(ASSET_NAME)
    }

    private fun openFileFromStorage(): InputStream {
        Log.d(TAG, "open Stored File")
        return context.openFileInput(FILE_NAME)
    }

    companion object {
        private const val TAG = "QuestRepository"
        private const val FILE_NAME = "quest.json"
        private const val ASSET_NAME = "quest_steps.json"
    }
}