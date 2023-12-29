package ru.montgolfier.comments.app.repository

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.montgolfier.comments.app.data.Comment

interface CommetsService {
    @GET("comments")
    suspend fun getCommentsByPage(@Query("page") page:Int): Response<List<Comment>>
}