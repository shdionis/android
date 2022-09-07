package ru.montgolfiere.searchquest.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.montgolfiere.searchquest.R
import ru.montgolfiere.searchquest.animations.CustomAnimationListener
import ru.montgolfiere.searchquest.model.QuestStep
import ru.montgolfiere.searchquest.viewmodels.QuestViewModel
import ru.montgolfiere.searchquest.viewmodels.QuestViewModelFactory
import ru.montgolfiere.searchquest.viewmodels.StateModel
import ru.montgolfiere.searchquest.viewmodels.state.DataState
import ru.montgolfiere.searchquest.viewmodels.state.ErrorState
import ru.montgolfiere.searchquest.viewmodels.state.FinishState
import ru.montgolfiere.searchquest.viewmodels.state.LoadingState
import ru.montgolfiere.searchquest.viewmodels.state.State
import ru.montgolfiere.searchquest.viewmodels.state.screen.HistoryState
import ru.montgolfiere.searchquest.viewmodels.state.screen.ViewState
import ru.montgolfiere.searchquest.views.BottomSheetView
import ru.montgolfiere.searchquest.views.ToolbarView

class QuestFragment(
    questViewModelFactory: QuestViewModelFactory,
) : Fragment() {
    private val viewModel: QuestViewModel by viewModels { questViewModelFactory }
    private val stateModel: StateModel by activityViewModels { questViewModelFactory }
    private lateinit var toolbar: ToolbarView
    private lateinit var questTextView: TextView
    private lateinit var questWrongAnswerView: TextView
    private lateinit var questAnswerView: EditText
    private lateinit var questImageView: ImageView
    private lateinit var questTryAnswerButton: Button
    private lateinit var questNextButton: Button
    private lateinit var questErrorView: FrameLayout
    private lateinit var questStubView: FrameLayout
    private lateinit var questContentView: NestedScrollView
    private lateinit var questAnswerViewGroup: LinearLayout
    private lateinit var questIsDoneViewGroup: LinearLayout
    private lateinit var questStubImage: ImageView
    private lateinit var questAnswerText: TextView
    private lateinit var hintFab: FloatingActionButton
    private lateinit var bottomSheet: BottomSheetView
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<BottomSheetView>
    private val fabAppearTimer = object: CountDownTimer(2000, 1000) {
        override fun onTick(millisUntilFinished: Long) { }

        override fun onFinish() {
            hintFab.startAnimation(AnimationUtils.loadAnimation(context, R.anim.appear_scale_animation))
            hintFab.visibility = View.VISIBLE
        }
    }

    private val wrongAnswerAnimationListener = CustomAnimationListener(
        endCallback = {
            questWrongAnswerView.visibility = View.INVISIBLE
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        initViews(root)
        initListeners()
        initScreenState()
        return root
    }

    private fun initScreenState() {
        val state = stateModel.screenState.value
        if (state is ViewState) {
            toolbar.enableBackMode()
            toolbar.setLeftButtonClickListener {
                requireActivity().onBackPressed()
            }
            viewModel.fetchStepById(state.id)
        } else {
            toolbar.setLeftButtonClickListener {
                stateModel.route(HistoryState)
            }
            viewModel.fetchActualStep()
        }
    }


    private fun initViews(root: View) {
        questTextView = root.findViewById(R.id.quest_text)
        questAnswerView = root.findViewById(R.id.quest_answer)
        questImageView = root.findViewById(R.id.quest_image)
        toolbar = root.findViewById(R.id.quest_toolbar)
        questWrongAnswerView = root.findViewById(R.id.quest_answer_wrong)
        questTryAnswerButton = root.findViewById(R.id.quest_button)
        questIsDoneViewGroup = root.findViewById(R.id.step_done_views_container)
        questAnswerViewGroup = root.findViewById(R.id.answer_views_container)
        questContentView = root.findViewById(R.id.quest_content_view)
        questStubView = root.findViewById(R.id.quest_stub_view)
        questStubImage = root.findViewById(R.id.quest_stub_image)
        questErrorView = root.findViewById(R.id.quest_error_view)
        questNextButton = root.findViewById(R.id.quest_next_button)
        hintFab = root.findViewById(R.id.quest_hint_fab)
        bottomSheet = root.findViewById(R.id.bottom_sheet)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun initListeners() {
        questTryAnswerButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                val isSuccess = viewModel.tryAnswer(questAnswerView.text.toString())
                if (!isSuccess) {
                    Log.d(TAG, "wrong answer")
                    questWrongAnswerView.visibility = View.VISIBLE
                    if (hintFab.visibility != View.VISIBLE) {
                        fabAppearTimer.start()
                    }
                    delay(1000)
                    val animation =
                        AnimationUtils.loadAnimation(
                            requireContext(),
                            R.anim.disappear_animation
                        )
                    animation.setAnimationListener(wrongAnswerAnimationListener)
                    questWrongAnswerView.startAnimation(animation)
                }
            }
        }
        questNextButton.setOnClickListener {
            viewModel.fetchNextStep()
        }
        viewModel.questStepLiveData.observe(viewLifecycleOwner) { dataState ->
            handleState(dataState)
        }
        hintFab.setOnClickListener {
            when (bottomSheetBehavior.state) {
                BottomSheetBehavior.STATE_HIDDEN -> {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                }
                BottomSheetBehavior.STATE_COLLAPSED -> {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
                BottomSheetBehavior.STATE_EXPANDED -> {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                }
            }
        }
    }

    private fun handleState(state: State) {
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

    private fun showError() {
        stub()
        questStubView.visibility = View.GONE
        questErrorView.visibility = View.VISIBLE
    }

    private fun stub() {
        Log.d(TAG, "stub")
        toolbar.visibility = View.GONE
        questErrorView.visibility = View.GONE
        questContentView.visibility = View.GONE
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
        toolbar.visibility = View.VISIBLE
        questErrorView.visibility = View.GONE
        questStubView.visibility = View.GONE
        questContentView.visibility = View.VISIBLE

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
        questContentView.smoothScrollTo(0,0)
        if (questStep.isDone) {
            questIsDoneViewGroup.visibility = View.VISIBLE
            questAnswerViewGroup.visibility = View.GONE
            questAnswerText.text = questStep.answersList.firstOrNull()
        } else {
            questIsDoneViewGroup.visibility = View.GONE
            questAnswerViewGroup.visibility = View.VISIBLE
        }
        bottomSheet.bind(questStep)
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
        const val STEP_ID_ARG = "step_id_arg"
    }
}