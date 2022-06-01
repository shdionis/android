package ru.shaden.tasktracker

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.shaden.tasktracker.fragments.TaskTrackerFragmentFactory
import ru.shaden.tasktracker.fragments.task.TaskDetailsFragment
import ru.shaden.tasktracker.fragments.task.TasksListFragment
import ru.shaden.tasktracker.fragments.workspace.WorkspacesListFragment
import ru.shaden.tasktracker.viewmodels.ScreenSwitchViewModel
import ru.shaden.tasktracker.viewmodels.State

class ContainerActivity : AppCompatActivity() {

    private val screenSwitchViewModel: ScreenSwitchViewModel by viewModels()
    private lateinit var fragmentFactory: TaskTrackerFragmentFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentFactory = TaskTrackerFragmentFactory()
        supportFragmentManager.fragmentFactory = fragmentFactory
        setContentView(R.layout.container_activity)
        screenSwitchViewModel.selectedFlow.observe(this) { state ->
            if (state != null) {
                switch(state)
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
                fragment.arguments = Bundle().also {
                    it.putInt(WorkspacesListFragment.ARG_PROJECT_ID, state.project.id)
                }
                fragment
            }
            is State.TasksListState -> {
                val fragment = fragmentFactory.createTasksListFragment()
                fragment.arguments = Bundle().also {
                    it.putInt(TasksListFragment.ARG_WORKSPACE_ID, state.workspace.id)
                }
                fragment
            }
            is State.TaskDetailsState -> {
                val fragment = fragmentFactory.createTasksDetailsFragment()
                fragment.arguments = Bundle().also {
                    it.putInt(TaskDetailsFragment.ARG_WORKSPACE_ID, state.task.workspace.id)
                    it.putInt(TaskDetailsFragment.ARG_TASK_ID, state.task.id)
                }
                fragment
            }
        }
        val transaction = supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container,
            fragment)
        if (isNeedAddToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }
}