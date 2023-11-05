package ru.montgolfier.comments

import java.util.*

object ServerApplication {
    @JvmStatic
    fun main(args: Array<String>) {
        val server = CommentsServer(CommentsRepository())
        println("Startup server...")
        server.start()
        println("Success! write \"exit\" for shutdown")
        val scanner = Scanner(System.`in`)
        while (true) {
            if(scanner.nextLine().contentEquals("exit", true)) {
                break
            }
        }
        println("Bye-bye!")
    }
}