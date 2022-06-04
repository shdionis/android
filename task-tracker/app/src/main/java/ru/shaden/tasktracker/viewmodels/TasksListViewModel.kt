package ru.shaden.tasktracker.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.shaden.tasktracker.model.Workspace
import ru.shaden.tasktracker.model.room.repositories.TaskRepository
import ru.shaden.tasktracker.model.room.repositories.WorkspaceRepository

class TasksListViewModel(
    private val taskRepository: TaskRepository,
    private val workspaceRepository: WorkspaceRepository,
) : ViewModel() {
    private val tasksLiveData = MutableLiveData<Workspace>()
    private var taskByWorkspaceIdJob: Job? = null
    fun getTaskByWorkspaceId(workspaceId: Int): LiveData<Workspace> {
        taskByWorkspaceIdJob?.cancel()
        taskByWorkspaceIdJob = viewModelScope.launch {
            workspaceRepository.getWorkspaceWithTasksWithStatus(workspaceId).collect {
                tasksLiveData.value = it
            }
        }
        return tasksLiveData
    }
}