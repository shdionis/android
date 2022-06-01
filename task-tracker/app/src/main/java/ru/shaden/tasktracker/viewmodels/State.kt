package ru.shaden.tasktracker.viewmodels

import ru.shaden.tasktracker.model.Project
import ru.shaden.tasktracker.model.Task
import ru.shaden.tasktracker.model.Workspace

sealed class State {
    object ProjectListState : State()
    class WorkspacesListState(val project: Project) : State()
    class TasksListState(val workspace: Workspace) : State()
    class TaskDetailsState(val task: Task) : State()

}
