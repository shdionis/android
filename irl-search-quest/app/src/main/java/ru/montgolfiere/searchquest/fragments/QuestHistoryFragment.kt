package ru.montgolfiere.searchquest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.montgolfiere.searchquest.R
import ru.montgolfiere.searchquest.model.Quest
import ru.montgolfiere.searchquest.model.QuestStep
import ru.montgolfiere.searchquest.viewmodels.QuestViewModel
import ru.montgolfiere.searchquest.viewmodels.QuestViewModelFactory
import ru.montgolfiere.searchquest.viewmodels.StateModel
import ru.montgolfiere.searchquest.viewmodels.state.screen.ViewState
import ru.montgolfiere.searchquest.views.ToolbarView

class QuestHistoryFragment(questViewModelFactory: QuestViewModelFactory) : Fragment() {
    private val viewModel: QuestViewModel by activityViewModels { questViewModelFactory }
    private val stateModel: StateModel by activityViewModels { questViewModelFactory }
    private lateinit var toolbar: ToolbarView
    private lateinit var questHistoryView: RecyclerView
    private lateinit var questHistoryViewAdapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_history, container, false)
        initViews(root)
        initListeners()
        return root
    }

    private fun initViews(root: View) {
        toolbar = root.findViewById(R.id.quest_toolbar)
        questHistoryView = root.findViewById(R.id.quest_history_recycler_view)
        questHistoryViewAdapter = HistoryAdapter(stateModel)
        questHistoryView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        questHistoryView.adapter = questHistoryViewAdapter

    }

    private fun initListeners() {
        viewModel.questLiveData.observe(viewLifecycleOwner) {
            questHistoryViewAdapter.bind(it)
        }
        toolbar.setLeftButtonClickListener {
            requireActivity().onBackPressed()
        }
    }

    class HistoryAdapter(private val stateModel: StateModel) : RecyclerView.Adapter<HistoryViewHolder>() {
        var items = emptyList<QuestStep>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.history_item, parent, false)
            return HistoryViewHolder(view)
        }

        override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
            holder.bind(items[position]) {
                stateModel.route(ViewState(items[position].id))
            }
        }

        override fun getItemCount(): Int {
            return items.count()
        }

        fun bind(quest: Quest) {
            items = quest.questSteps
            notifyDataSetChanged()
        }
    }

    class HistoryViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val historyImage: ImageView = view.findViewById(R.id.history_item_img)
        private val title: TextView = view.findViewById(R.id.history_item_title)
        private val subtitle: TextView = view.findViewById(R.id.history_item_subtitle)
        fun bind(questStep: QuestStep, listener: View.OnClickListener) {
            val imageId =
                view.context.resources.getIdentifier(
                    "seal${questStep.id}",
                    "drawable",
                    view.context.packageName
                )
            historyImage.setImageResource(imageId)
            title.text = questStep.title
            if (questStep.isDone) {
                subtitle.text = questStep.answersList.firstOrNull()
                subtitle.visibility = View.VISIBLE
                view.setOnClickListener(listener)
            } else {
                subtitle.visibility = View.GONE
            }
        }
    }
}