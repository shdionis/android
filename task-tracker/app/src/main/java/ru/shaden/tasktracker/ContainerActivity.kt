package ru.shaden.tasktracker

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.shaden.tasktracker.di.DumbDI
import ru.shaden.tasktracker.fragments.TaskTrackerFragmentFactory
import ru.shaden.tasktracker.fragments.task.TaskDetailsFragment
import ru.shaden.tasktracker.fragments.task.TasksListFragment
import ru.shaden.tasktracker.fragments.workspace.WorkspacesListFragment
import ru.shaden.tasktracker.viewmodels.ScreenSwitchViewModel
import ru.shaden.tasktracker.viewmodels.State

class ContainerActivity : AppCompatActivity() {
    private val screenSwitchViewModel: ScreenSwitchViewModel by viewModels {
        DumbDI.viewModelProviderFactory
    }

    private lateinit var fragmentFactory: TaskTrackerFragmentFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentFactory = DumbDI.taskTrackerFragmentFactory
        supportFragmentManager.fragmentFactory = fragmentFactory
        setContentView(R.layout.container_activity)
        lifecycleScope.launch {
            screenSwitchViewModel.selectedFlow.collect { state ->
                if (state != null) {
                    switch(state)
                }
            }
        }
    }

    private fun switch(state: State) {
        var isNeedAddToBackStack = true
        val fragment: Fragment = when (state) {
            is State.ProjectListState -> {
                isNeedAddToBackStack = false
                fragmentFactory.createProjectsListFragment()

            }
            is State.WorkspacesListState -> {
                val fragment = fragmentFactory.createWorkspacesListFragment()
                fragment.arguments = Bundle().also { bundle ->
                    val projectId = state.project.id ?: return@also
                    bundle.putInt(WorkspacesListFragment.ARG_PROJECT_ID, projectId)

                }
                fragment
            }
            is State.TasksListState -> {
                val fragment = fragmentFactory.createTasksListFragment()
                fragment.arguments = Bundle().also { bundle ->
                    val workspaceId = state.workspace.id ?: return@also
                    bundle.putInt(TasksListFragment.ARG_WORKSPACE_ID, workspaceId)
                }
                fragment
            }
            is State.TaskDetailsState -> {
                val fragment = fragmentFactory.createTasksDetailsFragment()
                fragment.arguments = Bundle().also { bundle ->
                    val taskId = state.task.id ?: return@also
                    val workspaceId = state.task.workspace?.id ?: return@also
                    bundle.putInt(TaskDetailsFragment.ARG_WORKSPACE_ID, workspaceId)
                    bundle.putInt(TaskDetailsFragment.ARG_TASK_ID, taskId)
                }
                fragment
            }
        }
        val transaction = supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container,
            fragment
        )
        if (isNeedAddToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }
}