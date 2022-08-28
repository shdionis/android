package ru.montgolfiere.searchquest.model

import com.google.gson.annotations.SerializedName

data class QuestStep(
    @SerializedName("id") val id: Int,
    @SerializedName("quest_title") val title: String,
    @SerializedName("quest_text") val text: String,
    @SerializedName("quest_image") val imageName: String,
    @SerializedName("quest_answer") val answer: String,
    @SerializedName("quest_wrong_message") val wrongMessage: String,
    @SerializedName("quest_hints") val hints: ArrayList<String>,
)