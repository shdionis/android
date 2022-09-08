package ru.montgolfiere.searchquest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ru.montgolfiere.searchquest.R
import ru.montgolfiere.searchquest.viewmodels.QuestViewModelFactory
import ru.montgolfiere.searchquest.viewmodels.StateModel
import ru.montgolfiere.searchquest.viewmodels.state.screen.HistoryState
import ru.montgolfiere.searchquest.views.ToolbarView

class QuestFinishFragment(
    private val questViewModelFactory: QuestViewModelFactory,
) : Fragment() {
    private val stateModel: StateModel by activityViewModels { questViewModelFactory }
    private lateinit var toolbar: ToolbarView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_finish, container, false)
        toolbar = root.findViewById(R.id.quest_toolbar)
        toolbar.setLeftButtonClickListener {
            stateModel.route(HistoryState)
        }
        return root
    }
}