package ru.montgolfiere.searchquest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ru.montgolfiere.searchquest.R
import ru.montgolfiere.searchquest.model.QuestStep
import ru.montgolfiere.searchquest.viewmodels.QuestViewModel
import ru.montgolfiere.searchquest.viewmodels.QuestViewModelFactory
import ru.montgolfiere.searchquest.viewmodels.state.DataState

class QuestFragment(questViewModelFactory: QuestViewModelFactory) : Fragment() {
    private val viewModel: QuestViewModel by activityViewModels { questViewModelFactory }
    lateinit var questTitleView: TextView
    lateinit var questTextView: TextView
    lateinit var questWrongAnswerView: TextView
    lateinit var questAnswerView: EditText
    lateinit var questImageView: ImageView
    lateinit var questButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        questTextView = root.findViewById(R.id.question_text)
        questAnswerView = root.findViewById(R.id.question_answer)
        questImageView = root.findViewById(R.id.question_image)
        questTitleView = root.findViewById(R.id.question_title)
        questWrongAnswerView = root.findViewById(R.id.question_answer_wrong)
        questButton = root.findViewById(R.id.question_button)
        fetchData()
        return root
    }

    private fun fetchData() {
        viewModel.getActualQuestStep(requireContext()).observe(viewLifecycleOwner) { state ->
            when (state) {
                is DataState -> {
                    val questStep = state.data
                    questTitleView.text = questStep.title
                    questTextView.text = questStep.text
                    questWrongAnswerView.text = questStep.wrongMessage
                    val questStepImage = getQuestStepImage(questStep)
                    if (questStepImage == null) {
                        questImageView.visibility = View.GONE
                    } else {
                        questImageView.visibility = View.VISIBLE
                        questImageView.setImageResource(questStepImage)
                    }
                }
                else -> {}
            }
        }
    }

    private fun getQuestStepImage(questStep: QuestStep): Int? {
        return context?.resources?.getIdentifier(
            questStep.imageName,
            "drawable",
            requireContext().packageName
        )
    }

    companion object {
        private const val TAG = "QuestFragment"
    }
}