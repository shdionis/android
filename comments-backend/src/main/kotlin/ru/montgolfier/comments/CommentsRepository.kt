package ru.montgolfier.comments

import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.PrintWriter
import java.nio.charset.Charset

class CommentsRepository {
    val commentsDir: File by lazy {
        PropertiesManager.getProperty(DIR_PROPERTY)?.let { File(it) } ?: throw FileNotFoundException()
    }

    fun getCommentsByPage(page: String): String {
        println("search page $page ...")
        val pageFile = commentsDir.listFiles()?.find {
            val name = it.name
            name.endsWith("_$page.json")
        } ?: throw FileNotFoundException()
        val reader = FileReader(pageFile, Charset.forName("UTF-8"))
        val content = reader.readText()
        reader.close()
        return content
    }

    fun writePageComments(pageBody: String, page: Int) {
        val file = File(commentsDir, "comments_$page.json")
        val writer = PrintWriter(file, Charset.forName("UTF-8"))
        writer.println(pageBody)
        writer.close()
    }

    companion object {
        private const val DIR_PROPERTY = "comments.files.directory"
    }
}