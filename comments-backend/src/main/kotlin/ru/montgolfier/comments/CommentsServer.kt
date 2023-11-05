package ru.montgolfier.comments

import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress
import java.util.concurrent.Executors

class CommentsServer(
    private val repository: CommentsRepository,
) {
    private val httpServer: HttpServer by lazy { initServer() }

    private fun initServer(): HttpServer {
        val server = HttpServer.create(InetSocketAddress(8080), 10)
        server.createContext("/comments", CommentsHandler(repository))
        server.executor = Executors.newFixedThreadPool(10)
        return server
    }

    fun start() {
        httpServer.start()
    }

    fun stop() {
        httpServer.stop(300)
    }

    companion object {
        internal  const val CONTENT_TYPE_HEADER = "Content-Type"
        internal  const val CONTENT_TYPE_JSON = "application/json"

        internal  const val NOT_ALLOW_CODE = 405
        internal  const val NOT_FOUND_CODE = 404
        internal  const val INTERNAL_ERROR_CODE = 500
        internal  const val OK_CODE = 200

        internal const val NOT_ALLOW_MESSAGE = "Not Allowing this method for comments!"
        internal const val INTERNAL_ERROR_MESSAGE = "Oops"
        internal const val NOT_FOUND_ERROR_MESSAGE = "Not found! Sorry..."

        internal const val GET_METHOD = "GET"
        internal const val PAGE_PARAM = "page"
    }
}