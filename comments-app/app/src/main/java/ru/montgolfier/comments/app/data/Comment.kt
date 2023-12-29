package ru.montgolfier.comments.app.data

data class Comment(
    val id: Int,
    val author: String,
    val message: String,
    val rating: Int,
    val avatar: String
)