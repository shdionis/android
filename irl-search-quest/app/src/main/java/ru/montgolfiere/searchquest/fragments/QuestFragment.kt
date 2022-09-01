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
import ru.montgolfiere.searchquest.viewmodels.state.FinishState
import ru.montgolfiere.searchquest.viewmodels.state.LoadingState
import ru.montgolfiere.searchquest.views.ToolbarView

class QuestFragment(questViewModelFactory: QuestViewModelFactory) : Fragment() {
    private val viewModel: QuestViewModel by activityViewModels { questViewModelFactory }
    private lateinit var toolbar: ToolbarView
    private lateinit var questTextView: TextView
    private lateinit var questWrongAnswerView: TextView
    private lateinit var questAnswerView: EditText
    private lateinit var questImageView: ImageView
    private lateinit var questButton: Button
    private lateinit var questErrorView: FrameLayout
    private lateinit var questStubView: FrameLayout
    private lateinit var questStubImage: ImageView
    private val wrongAnswerAnimationListener = object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation?) {}

        override fun onAnimationEnd(animation: Animation?) {
            questWrongAnswerView.visibility = View.INVISIBLE
        }

        override fun onAnimationRepeat(animation: Animation?) {}

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        initViews(root)
        initListeners()
        return root
    }

    private fun initViews(root: View) {
        questTextView = root.findViewById(R.id.question_text)
        questAnswerView = root.findViewById(R.id.question_answer)
        questImageView = root.findViewById(R.id.question_image)
        toolbar = root.findViewById(R.id.quest_toolbar)
        questWrongAnswerView = root.findViewById(R.id.question_answer_wrong)
        questButton = root.findViewById(R.id.question_button)
        questStubView = root.findViewById(R.id.quest_stub_view)
        questStubImage = root.findViewById(R.id.quest_stub_image)
        questErrorView = root.findViewById(R.id.quest_error_view)
    }

    private fun initListeners() {
        questButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                val isSuccess = viewModel.tryAnswer(questAnswerView.text.toString())
                if (!isSuccess) {
                    Log.d(TAG, "wrong answer")
                    questWrongAnswerView.visibility = View.VISIBLE
                    delay(1000)
                    val animation =
                        AnimationUtils.loadAnimation(requireContext(), R.anim.disapear_animation)
                    animation.setAnimationListener(wrongAnswerAnimationListener)
                    questWrongAnswerView.startAnimation(animation)
                }
            }
        }

        viewModel.getActualQuestStep().observe(viewLifecycleOwner) { state ->
            when (state) {
                is DataState -> {
                    val questStep = state.data
                    Log.d(TAG, "Data state: $questStep")
                    bind(questStep)
                }
                is LoadingState -> {
                    Log.d(TAG, "LoadingState")
                    stub()
                }
                is ErrorState -> {
                    Log.d(TAG, "ErrorState")
                    showError()
                }
                is FinishState -> {}
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
        toolbar.visibility = View.GONE
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
        toolbar.visibility = View.VISIBLE
        questButton.visibility = View.VISIBLE
        questErrorView.visibility = View.GONE
        questStubView.visibility = View.GONE

        toolbar.setTitle(questStep.title)
        toolbar.setSubTitle(questStep.subtitle)
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