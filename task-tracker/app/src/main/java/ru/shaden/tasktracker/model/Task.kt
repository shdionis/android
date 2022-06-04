package ru.shaden.tasktracker.model

interface Task {
    val id: Int
    var name: String
    var description: String?
    var workspace: Workspace?
    var status: Status?
}