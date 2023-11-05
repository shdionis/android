package ru.montgolfier.comments

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import java.io.BufferedOutputStream
import java.io.BufferedWriter
import java.io.ByteArrayInputStream
import java.io.FileNotFoundException
import java.io.PrintWriter
import java.net.URI
import java.nio.charset.Charset


class CommentsHandler(private val repository: CommentsRepository) : HttpHandler {
    override fun handle(exchange: HttpExchange?) {
        val requestMethod = exchange?.requestMethod ?: return
        when (requestMethod) {
            CommentsServer.GET_METHOD -> doGetComments(exchange)
            else -> doError(exchange, CommentsServer.NOT_ALLOW_CODE, CommentsServer.NOT_ALLOW_MESSAGE)
        }
    }

    private fun doGetComments(exchange: HttpExchange) {
        val mapParams = collectParams(exchange.requestURI)
        try {
            val page = mapParams[CommentsServer.PAGE_PARAM]?.let {
                repository.getCommentsByPage(it)
            }
            page?.let { doSuccess(exchange, page) } ?: doError(exchange,
                CommentsServer.NOT_FOUND_CODE,
                CommentsServer.NOT_FOUND_ERROR_MESSAGE
            )
        } catch (ex: FileNotFoundException) {
            doError(exchange, CommentsServer.NOT_FOUND_CODE, CommentsServer.NOT_FOUND_ERROR_MESSAGE, ex)
        } catch (ex: Exception) {
            doError(exchange, CommentsServer.INTERNAL_ERROR_CODE, CommentsServer.INTERNAL_ERROR_MESSAGE, ex)
        }
    }

    private fun collectParams(requestURI: URI?): Map<String, String> =
        requestURI?.rawQuery?.let { rawQuery ->
            val params = HashMap<String, String>()
            rawQuery.split("&").forEach { keyValue ->
                val paramAndValue = keyValue.split("=")
                if (paramAndValue.size == 2) {
                    params[paramAndValue[0]] = paramAndValue[1]
                }
            }
            params
        } ?: emptyMap()

    private fun doError(exchange: HttpExchange, code: Int, message: String, ex: Exception? = null) {
        try {
            println("$code $message")
            ex?.printStackTrace()
            exchange.sendResponseHeaders(CommentsServer.INTERNAL_ERROR_CODE, message.length.toLong())
            exchange.responseBody.write(message.toByteArray())
        } catch (exception: Exception) {
            println("doError Exception!")
            exception.printStackTrace()
        }
    }

    private fun doSuccess(exchange: HttpExchange, data: String) {
        try {
            val byteData = data.toByteArray(Charset.forName("UTF-8"))

            exchange.responseHeaders.add(CommentsServer.CONTENT_TYPE_HEADER, CommentsServer.CONTENT_TYPE_JSON)
            exchange.sendResponseHeaders(CommentsServer.OK_CODE, 0)
            val bis = ByteArrayInputStream(byteData)
            val buffer = ByteArray(1024)
            var count = 0
            while (bis.read(buffer).also { count = it } != -1) {
                exchange.responseBody.write(buffer, 0, count)
            }
        } catch (exception: Exception) {
            println("doSuccess Exception!")
            exception.printStackTrace()
        } finally {
            exchange.responseBody.close()
        }
    }

}