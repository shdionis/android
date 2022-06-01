package ru.shaden.tasktracker.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ru.shaden.tasktracker.viewmodels.ScreenSwitchViewModel
import ru.shaden.tasktracker.viewmodels.State

open class BaseFragment : Fragment() {
    private val screenSwitcher: ScreenSwitchViewModel by activityViewModels()

    protected fun switch(state: State) {
        screenSwitcher.switch(state)
    }
}