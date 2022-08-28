package ru.montgolfiere.searchquest.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.montgolfiere.searchquest.config.QuestConfig
import ru.montgolfiere.searchquest.model.Quest
import ru.montgolfiere.searchquest.model.QuestStep
import ru.montgolfiere.searchquest.viewmodels.state.DataState
import ru.montgolfiere.searchquest.viewmodels.state.ErrorCauses
import ru.montgolfiere.searchquest.viewmodels.state.ErrorState
import ru.montgolfiere.searchquest.viewmodels.state.ScreenState

class QuestViewModel(
    val questConfig: QuestConfig
): ViewModel() {
    private val questStepLiveData = MutableLiveData<ScreenState>()
    private var requestQuestDataJob: Job? = null

    fun getActualQuestStep(context: Context): LiveData<ScreenState> {
        requestQuestDataJob?.cancel()
        requestQuestDataJob = viewModelScope.launch(Dispatchers.Main) {
            val job = async(Dispatchers.IO) {
                try {
                    val questStepsStream = context.assets.open("quest_steps.json")
                    val size = questStepsStream.available()
                    val buffer = ByteArray(size)
                    questStepsStream.read(buffer)
                    val quest = Gson().fromJson(String(buffer), Quest::class.java)
                    findQuestStep(quest)?.let { DataState(it) } ?: ErrorState(ErrorCauses.NOT_FOUND)
                } catch (ex: Exception) {
                    ErrorState(ErrorCauses.PARSE_ERROR)
                }
            }
            questStepLiveData.value = job.await()
        }
        return questStepLiveData
    }

    private fun findQuestStep(data: Quest): QuestStep? {
        return data.questSteps.firstOrNull()
    }
}