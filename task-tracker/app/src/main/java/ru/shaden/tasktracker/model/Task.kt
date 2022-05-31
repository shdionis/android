package ru.shaden.tasktracker.model

interface Task {
    val id: Int
    val name: String
    val description: String
    val workspace: Workspace
    val status: Status
}