package ru.montgolfiere.searchquest.model

import com.google.gson.annotations.SerializedName
 class QuestStep {
     @SerializedName("id")
     val id: Int = 0
     @SerializedName("quest_title")
     val title: String = ""
     @SerializedName("quest_subtitle")
     val subtitle: String = ""
     @SerializedName("quest_text")
     val text: String = ""
     @SerializedName("quest_image")
     val imageName: String = ""
     @SerializedName("quest_answers")
     val answersList: List<String> = emptyList()
     @SerializedName("quest_wrong_message")
     val wrongMessage: String = ""
     @SerializedName("quest_hints")
     val hints: List<String> = emptyList()
     @SerializedName("next_step_id")
     val nextStepId: Int? = null
     @SerializedName("last_hint_usage_time")
     var lastHintUsageTime: Long = 0L
     @SerializedName("last_hint_usage_item")
     var lastHintUsageItem: Int = -1
     @SerializedName("quest_is_done")
     var isDone: Boolean = false
 }