package ru.montgolfier.comments.app.repository

import android.content.res.Resources.NotFoundException
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.montgolfier.comments.app.data.Comment

class RemoteCommentsRepository(
    private val url: String
) {
    private val retrofit: Retrofit by lazy { initRetrofit() }

    private val commentService: CommetsService by lazy {
        retrofit.create(CommetsService::class.java)
    }

    private fun initRetrofit(): Retrofit {
        val gson = GsonBuilder().create()
        return Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

    suspend fun getComments(page: Int): List<Comment> {
        return withContext(Dispatchers.IO) {
            val response = commentService.getCommentsByPage(page)
            if (response.isSuccessful) {
                response.body() ?: throw NotFoundException()
            } else if (response.code() == 404) {
                throw NotFoundException()
            } else {
                throw Exception()
            }

        }
    }
}