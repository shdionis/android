package ru.shaden.tasktracker.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.shaden.tasktracker.model.Project
import ru.shaden.tasktracker.model.room.repositories.ProjectRepository

class ProjectsListViewModel(projectRepository: ProjectRepository) : ViewModel() {

    private val projectsFlow = projectRepository.getAllProjects()
    private val projects: MutableLiveData<List<Project>> = MutableLiveData()

    init {
        viewModelScope.launch {
            projectsFlow.collect {
                projects.value = it
            }
        }
    }

    fun getAllProjects(): LiveData<List<Project>> = projects

}