package ru.shaden.tasktracker.model

interface Workspace {
    val id: Int
    var name: String
    var description: String?
    var project: Project?
    var tasks: List<Task>?
}
