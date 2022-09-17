package ru.montgolfiere.searchquest.views

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.montgolfiere.searchquest.R
import ru.montgolfiere.searchquest.animations.CustomAnimationListener
import ru.montgolfiere.searchquest.config.QuestConfig
import ru.montgolfiere.searchquest.model.QuestStep
import ru.montgolfiere.searchquest.viewmodels.QuestViewModel
import java.util.concurrent.TimeUnit

class BottomSheetView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    private val hintRecyclerView: RecyclerView
    private val hintRecyclerViewAdapter: HintViewAdapter
    lateinit var viewModel: QuestViewModel

    init {
        inflate(context, R.layout.bottom_sheet, this)
        hintRecyclerView = findViewById(R.id.quest_hint_list_view)
        hintRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        hintRecyclerViewAdapter = HintViewAdapter(this)
        hintRecyclerView.adapter = hintRecyclerViewAdapter
    }

    fun bind(step: QuestStep) {
        if (step.hints.isEmpty()) {
            visibility = GONE
        } else {
            visibility = VISIBLE
            hintRecyclerViewAdapter.bind(step)
        }
    }

    private fun storeQuestModel() {
        viewModel.storeQuestModel()
    }

    class HintViewAdapter(val bottomSheetView: BottomSheetView) :
        RecyclerView.Adapter<HintViewHolder>() {
        var item: QuestStep? = null
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HintViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.hint_item, parent, false)
            return HintViewHolder(view, this)
        }

        override fun onBindViewHolder(holder: HintViewHolder, position: Int) {
            holder.bind(item, position)
        }

        override fun getItemCount(): Int {
            val currentItem = item ?: return 0
            if (currentItem.lastHintUsageItem + 2 < currentItem.hints.size) {
                return currentItem.lastHintUsageItem + 2
            }
            return currentItem.hints.size
        }

        fun storeQuestStep() {
            bottomSheetView.storeQuestModel()
        }

        fun bind(step: QuestStep) {
            item = step
            notifyDataSetChanged()
        }

    }

    class HintViewHolder(view: View, val hintViewAdapter: HintViewAdapter) :
        RecyclerView.ViewHolder(view) {
        private val hintMessage: TextView = view.findViewById(R.id.hint_text)
        private val timerLable: TextView = view.findViewById(R.id.hint_timer_label)
        private val openHint: TextView = view.findViewById(R.id.open_hint_view)
        private val timerImg: ImageView = view.findViewById(R.id.hint_timer_img)
        private val overlay: FrameLayout = view.findViewById(R.id.hint_overlay)
        private var timer: CountDownTimer? = null

        fun bind(questStep: QuestStep?, position: Int) {
            initState()
            questStep ?: return
            overlay.setOnClickListener {
                questStep.lastHintUsageTime = System.currentTimeMillis()
                questStep.lastHintUsageItem = position
                hintViewAdapter.storeQuestStep()
                val disappearAnim = AnimationUtils.loadAnimation(
                    it.context,
                    R.anim.disappear_animation
                )
                disappearAnim.setAnimationListener(
                    CustomAnimationListener(
                        endCallback = {
                            overlay.visibility = GONE
                            hintViewAdapter.notifyDataSetChanged()
                        }
                    )
                )
                it.startAnimation(disappearAnim)
            }

            if (position <= questStep.lastHintUsageItem) {
                hintMessage.text = questStep.hints[position]
                hintMessage.visibility = VISIBLE
                overlay.visibility = GONE
                overlay.isEnabled = false
                return
            } else {
                hintMessage.visibility = GONE
                overlay.visibility = VISIBLE
            }

            val timeToShowHint =
                questStep.lastHintUsageTime + QuestConfig.HINT_COOL_DOWN_THRESHOLD * position - System.currentTimeMillis()
            Log.d("HintViewHolder", "timeToShowHint = $timeToShowHint")
            timer?.cancel()
            timer = object : CountDownTimer(timeToShowHint, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                    val timeText = "${seconds / 60}:${seconds % 60}"
                    timerLable.text = timeText
                }

                override fun onFinish() {
                    timerLable.visibility = GONE
                    timerImg.visibility = GONE
                    overlay.isEnabled = true
                    openHint.visibility = VISIBLE
                }

            }
            if (timeToShowHint < 0) {
                timer?.onFinish()
            } else {
                timer?.start()
            }
        }

        private fun initState() {
            hintMessage.visibility = GONE
            timerLable.visibility = VISIBLE
            openHint.visibility = GONE
            timerImg.visibility = VISIBLE
            overlay.visibility = VISIBLE
            overlay.isEnabled = false
        }

    }
}
