package ru.montgolfiere.searchquest.model

import com.google.gson.annotations.SerializedName

data class Quest(
    @SerializedName("start_step_id") val startStep: Int,
    @SerializedName("quest_steps") val questSteps: List<QuestStep>,
)
