package ru.montgolfiere.searchquest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.montgolfiere.searchquest.R
import ru.montgolfiere.searchquest.viewmodels.QuestViewModelFactory
import ru.montgolfiere.searchquest.viewmodels.StateModel
import ru.montgolfiere.searchquest.viewmodels.state.screen.HistoryState
import ru.montgolfiere.searchquest.views.ToolbarView

class QuestFinishFragment(
    private val questViewModelFactory: QuestViewModelFactory,
    private val questFragmentFactory: QuestFragmentFactory,
) : Fragment() {
    private val stateModel: StateModel by activityViewModels { questViewModelFactory }
    private lateinit var toolbar: ToolbarView
    private val sealsArrayIds = arrayOf(
        R.id.quest_finish_seal1_img,
        R.id.quest_finish_seal2_img,
        R.id.quest_finish_seal3_img,
        R.id.quest_finish_seal4_img,
        R.id.quest_finish_seal5_img,
        R.id.quest_finish_seal6_img,
        R.id.quest_finish_seal7_img,
    )
    private val brokenSealsArrayIds = arrayOf(
        R.id.quest_finish_seal1_broken_img,
        R.id.quest_finish_seal2_broken_img,
        R.id.quest_finish_seal3_broken_img,
        R.id.quest_finish_seal4_broken_img,
        R.id.quest_finish_seal5_broken_img,
        R.id.quest_finish_seal6_broken_img,
        R.id.quest_finish_seal7_broken_img,
    )
    private lateinit var sealsList: List<SealImage>
    private lateinit var finishButton: ImageView
    private lateinit var pulseAnimation: Animation
    private lateinit var rotationAnimation: Animation

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
        val mutableSealsList = ArrayList<SealImage>(sealsArrayIds.size)
        for (i in sealsArrayIds.indices) {
            mutableSealsList += SealImage(
                root.findViewById(sealsArrayIds[i]),
                root.findViewById(brokenSealsArrayIds[i])
            )
        }
        sealsList = mutableSealsList
        pulseAnimation = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.pulse_70_120_animation
        )
        rotationAnimation = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.infinity_rotate_animation_lr
        )
        finishButton = root.findViewById(R.id.quest_finish_button)
        finishButton.setOnClickListener {
            onClickFinish()
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        closeDialog()
        startPulseFinishButtonAnimation()

    }

    private fun startPulseFinishButtonAnimation() {
        finishButton.clearAnimation()
        finishButton.startAnimation(pulseAnimation)
    }

    private fun startRotationFinishButtonAnimation() {
        finishButton.clearAnimation()
        finishButton.startAnimation(rotationAnimation)
    }

    private fun onClickFinish() {
        startRotationFinishButtonAnimation()
        lifecycleScope.launch(Dispatchers.Main) {
            sealsList.forEach {
                delay(500)
                it.showBroken()
            }
            showDialog()
        }

    }

    private fun showDialog() {
        val supportFragmentManager = requireActivity().supportFragmentManager
        val transaction = supportFragmentManager.beginTransaction();
        val prev = supportFragmentManager.findFragmentByTag("dialog");
        if (prev != null) {
            transaction.remove(prev);
        }
        transaction.addToBackStack(null);
        // Create and show the dialog.
        val newFragment: QuestFinishImageModalFragment =
            questFragmentFactory.createQuestFinishImageModalFragment()
        newFragment.onDismissListener = {
            sealsList.forEach {
                it.showUnbroken()
            }
            startPulseFinishButtonAnimation()
        }
        newFragment.show(transaction, "dialog")
    }

    private fun closeDialog() {
        val supportFragmentManager = requireActivity().supportFragmentManager
        val prev = supportFragmentManager.findFragmentByTag("dialog")
        if (prev != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.remove(prev)
            transaction.commit()
        }
    }


    private data class SealImage(
        private val sealImage: ImageView,
        private val brokenSealImage: ImageView
    ) {
        fun showBroken() {
            sealImage.visibility = View.INVISIBLE
            brokenSealImage.visibility = View.VISIBLE
        }

        fun showUnbroken() {
            sealImage.visibility = View.VISIBLE
            brokenSealImage.visibility = View.INVISIBLE
        }

    }
}