package ru.shaden.tasktracker.model

interface Project {
    val id: Int
    var name: String
    var description: String?
    var workspacesList: List<Workspace>?
}