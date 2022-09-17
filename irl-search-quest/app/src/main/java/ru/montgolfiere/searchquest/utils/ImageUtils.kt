package ru.montgolfiere.searchquest.utils

import android.content.Context

object ImageUtils {
    fun getBrokenSealDrawableId(context: Context, id: Int): Int {
        return getDrawableIdByName(context, "seal${id}_broken")
    }

    fun getSealDrawableId(context: Context, id: Int): Int {
        return getDrawableIdByName(context, "seal${id}")
    }

    fun getDrawableIdByName(context: Context, resName: String): Int {
        return context.resources.getIdentifier(
            resName,
            "drawable",
            context.packageName
        )
    }
}