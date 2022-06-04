package ru.shaden.tasktracker.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.shaden.tasktracker.model.Task
import ru.shaden.tasktracker.model.room.repositories.TaskRepository
import ru.shaden.tasktracker.model.room.repositories.WorkspaceRepository

class TaskDetailsViewModel(
    private val taskRepository: TaskRepository,
    private val workspaceRepository: WorkspaceRepository,
) : ViewModel() {

    private val taskLiveData = MutableLiveData<Task>()
    private var selectSingleTaskJob: Job? = null

    fun getTaskWithStatusByIdWithWorkspaceById(taskId: Int, workspaceId: Int): LiveData<Task> {
        selectSingleTaskJob?.cancel()
        selectSingleTaskJob = viewModelScope.launch {
            taskRepository.getTaskWithStatusWithWorkspace(taskId, workspaceId).collect {
                taskLiveData.value = it
            }

        }
        return taskLiveData
    }


}
