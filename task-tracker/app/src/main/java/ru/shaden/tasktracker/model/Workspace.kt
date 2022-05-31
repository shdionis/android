package ru.shaden.tasktracker.model

interface Workspace {
    val id: Int
    val name: String
    val description: String
    val project: Project
}
