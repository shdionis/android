package ru.montgolfiere.searchquest.fragments

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.montgolfiere.searchquest.R
import ru.montgolfiere.searchquest.model.QuestStep
import ru.montgolfiere.searchquest.viewmodels.QuestViewModel
import ru.montgolfiere.searchquest.viewmodels.QuestViewModelFactory
import ru.montgolfiere.searchquest.viewmodels.state.DataState
import ru.montgolfiere.searchquest.viewmodels.state.ErrorState
import ru.montgolfiere.searchquest.viewmodels.state.LoadingState

class QuestFragment(questViewModelFactory: QuestViewModelFactory) : Fragment() {
    private val viewModel: QuestViewModel by activityViewModels { questViewModelFactory }
    lateinit var questTitleView: TextView
    lateinit var questTextView: TextView
    lateinit var questWrongAnswerView: TextView
    lateinit var questAnswerView: EditText
    lateinit var questImageView: ImageView
    lateinit var questButton: Button
    lateinit var questErrorView: FrameLayout
    lateinit var questStubView: FrameLayout
    lateinit var questStubImage: ImageView

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
        questStubView = root.findViewById(R.id.quest_stub_view)
        questStubImage = root.findViewById(R.id.quest_stub_image)
        questErrorView = root.findViewById(R.id.quest_error_view)
        fetchData()
        questButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                val correctAnswer = viewModel.checkAnswer(questAnswerView.text.toString())
                if (correctAnswer) {
                    stub()
                    Log.d(TAG, "correct answer")
                    delay(1000)
                    viewModel.goToNextStep()
                } else {
                    Log.d(TAG, "wrong answer")
                    questWrongAnswerView.visibility = View.VISIBLE
                    delay(1000)
                    val animation =
                        AnimationUtils.loadAnimation(requireContext(), R.anim.disapear_animation)
                    animation.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationStart(animation: Animation?) {}

                        override fun onAnimationEnd(animation: Animation?) {
                            questWrongAnswerView.visibility = View.INVISIBLE
                        }

                        override fun onAnimationRepeat(animation: Animation?) {}

                    })
                    questWrongAnswerView.startAnimation(animation)

                }
            }
        }
        return root
    }

    private fun fetchData() {
        viewModel.getActualQuestStep().observe(viewLifecycleOwner) { state ->
            when (state) {
                is DataState -> {
                    val questStep = state.data
                    Log.d(TAG, "Data state: $questStep")
                    bind(questStep)
                }
                is LoadingState -> {
                    Log.d(TAG, "LoadingState stub")
                    stub()
                }
                is ErrorState -> {
                    Log.d(TAG, "ErrorState cause: ${state.cause}")
                    showError()
                }
                else -> {}
            }
        }
    }

    private fun showError() {
        stub()
        questStubView.visibility = View.GONE
        questErrorView.visibility = View.VISIBLE
    }

    private fun stub() {
        Log.d(TAG, "stub")
        questTextView.visibility = View.GONE
        questAnswerView.visibility = View.GONE
        questImageView.visibility = View.GONE
        questTitleView.visibility = View.GONE
        questButton.visibility = View.GONE
        questErrorView.visibility = View.GONE
        questStubView.visibility = View.VISIBLE
        questStubImage.startAnimation(
            AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.infinity_rotate_animation
            )
        )
    }

    private fun bind(questStep: QuestStep) {
        Log.d(TAG, "bind $questStep")
        questTextView.visibility = View.VISIBLE
        questAnswerView.visibility = View.VISIBLE
        questImageView.visibility = View.VISIBLE
        questTitleView.visibility = View.VISIBLE
        questButton.visibility = View.VISIBLE
        questErrorView.visibility = View.GONE
        questStubView.visibility = View.GONE

        questTitleView.text = questStep.title
        questTextView.text = Html.fromHtml(requireContext().getString(R.string.quest_text_template, questStep.text))
        questWrongAnswerView.text = questStep.wrongMessage
        val questStepImage = getQuestStepImage(questStep)
        if (questStepImage == null) {
            questImageView.visibility = View.GONE
        } else {
            questImageView.visibility = View.VISIBLE
            questImageView.setImageResource(questStepImage)
        }
        questAnswerView.text.clear()
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