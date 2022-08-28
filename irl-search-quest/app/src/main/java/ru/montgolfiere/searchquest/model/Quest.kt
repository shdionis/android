package ru.montgolfiere.searchquest.model

import com.google.gson.annotations.SerializedName

data class Quest(
    @SerializedName("quest_steps") val questSteps: ArrayList<QuestStep>
)
