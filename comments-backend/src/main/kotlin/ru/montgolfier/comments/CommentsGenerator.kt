package ru.montgolfier.comments

import com.google.gson.GsonBuilder
import java.io.File
import java.io.FileReader
import kotlin.random.Random

object CommentsGenerator {
    private val repo = CommentsRepository()
    private val names: List<String> by lazy { readFile("names") }
    private val comments: List<String> by lazy { readFile("comments") }
    private val avatars: List<String> by lazy { readFile("avatars") }


    private fun readFile(fileName: String): List<String> {
        val reader = FileReader(File(repo.commentsDir, fileName))
        return reader.readLines()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val gson = GsonBuilder().setPrettyPrinting().create()
        for (i in 0..50) {
            val page = generatePage()
            val pageBody = gson.toJson(page)
            repo.writePageComments(pageBody, i)
        }
    }

    private fun generatePage(): List<Comment> = ArrayList<Comment>().apply {
        val random = Random(System.currentTimeMillis())
        var commentId = 0
        repeat(50) {
            this.add(
                Comment(
                    id = commentId++,
                    author = names[random.nextInt(names.size)],
                    message = comments[random.nextInt(comments.size)],
                    rating = random.nextInt(1, 6),
                    avatar = avatars[random.nextInt(avatars.size)]
                )
            )
        }
    }

    data class Comment(val id: Int, val author: String, val message: String, val rating: Int, val avatar: String)
}