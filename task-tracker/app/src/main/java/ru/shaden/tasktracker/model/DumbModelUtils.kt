package ru.shaden.tasktracker.model

import androidx.collection.ArrayMap

object DumbModelUtils {
    private val statusList: List<Status> = createDumbStatusList()
    val projects: Map<Int, Project> by lazy { createDumbProjectList() }
    val workspaces: Map<Int, Map<Int, Workspace>> by lazy { createDumbWorkspaceList(projects.values) }
    val tasks: Map<Int, Map<Int, Task>> by lazy { createDumbTaskList(workspaces) }

    private fun createDumbProjectList(): Map<Int, Project> {
        val map = ArrayMap<Int, Project>()
        for (i in 0..100) {
            map[i] = DumbProject(i, "Project #$i", "Description for Project №$i")
        }
        return map
    }

    private fun createDumbWorkspaceList(projects: Collection<Project>): Map<Int, Map<Int, Workspace>> {
        val map = ArrayMap<Int, Map<Int, Workspace>>()
        for (project in projects) {
            val workspacesMap = ArrayMap<Int, Workspace>()
            for (i in 0..3) {
                workspacesMap[i] = DumbWorkspace(
                    i,
                    "Workspace p${project.id}-$i",
                    "Description for Workspace№$i",
                    project
                )
            }
            map[project.id] = workspacesMap
        }
        return map
    }

    private fun createDumbTaskList(workspaces: Map<Int, Map<Int, Workspace>>): Map<Int, Map<Int, Task>> {
        val tasksMap = ArrayMap<Int, Map<Int, Task>>()
        for (workspaceMap in workspaces.values) {
            for (workspace in workspaceMap.values) {
                val taskMap = ArrayMap<Int, Task>()
                for (i in 0..100) {
                    taskMap[workspace.id] =
                        DumbTask(
                            i,
                            "Task$i",
                            "Description for Task№$i in Workspace ${workspace.id} in project ${workspace.project.id}",
                            workspace,
                            statusList.random()
                        )
                }
                tasksMap[workspace.id] = taskMap
            }
        }
        return tasksMap
    }

    private fun createDumbStatusList(): List<Status> {
        val list = ArrayList<Status>()
        list.add(DumbStatus(0, "Open"))
        list.add(DumbStatus(1, "Need Info"))
        list.add(DumbStatus(2, "In progress"))
        list.add(DumbStatus(3, "Review"))
        list.add(DumbStatus(4, "Ready for test"))
        list.add(DumbStatus(5, "Testing"))
        list.add(DumbStatus(6, "Done"))
        list.add(DumbStatus(7, "Closed"))
        return list
    }

    private data class DumbStatus(
        override val id: Int,
        override val name: String,
    ) : Status

    private data class DumbTask(
        override val id: Int,
        override val name: String,
        override val description: String,
        override val workspace: Workspace,
        override val status: Status,
    ) : Task

    private data class DumbWorkspace(
        override val id: Int,
        override val name: String,
        override val description: String,
        override val project: Project,
    ) : Workspace

    private data class DumbProject(
        override val id: Int,
        override val name: String,
        override val description: String,
    ) : Project
}