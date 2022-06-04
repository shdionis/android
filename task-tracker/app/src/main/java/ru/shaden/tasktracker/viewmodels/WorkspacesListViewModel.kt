package ru.shaden.tasktracker.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.shaden.tasktracker.model.Workspace
import ru.shaden.tasktracker.model.room.repositories.WorkspaceRepository

class WorkspacesListViewModel(
    private val workspaceRepository: WorkspaceRepository
) : ViewModel() {
    private val workspaces: MutableLiveData<List<Workspace>> = MutableLiveData()
    private var workspaceListJob: Job? = null

    fun getWorkspacesByProject(projectId: Int): LiveData<List<Workspace>> {
        workspaceListJob?.cancel()
        workspaceListJob = viewModelScope.launch {
            workspaceRepository.getWorkspacesByProjectId(projectId).collect {
                workspaces.value = it
            }
        }
        return workspaces
    }
}