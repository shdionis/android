package ru.montgolfiere.searchquest

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.montgolfiere.searchquest.fragments.QuestFragment
import ru.montgolfiere.searchquest.fragments.QuestFragmentFactory
import ru.montgolfiere.searchquest.viewmodels.StateModel
import ru.montgolfiere.searchquest.viewmodels.state.screen.FinishScreenState
import ru.montgolfiere.searchquest.viewmodels.state.screen.HistoryState
import ru.montgolfiere.searchquest.viewmodels.state.screen.MainState
import ru.montgolfiere.searchquest.viewmodels.state.screen.ViewState
import javax.inject.Inject

class ContainerActivity : AppCompatActivity() {
    @Inject
    lateinit var questFragmentFactory: QuestFragmentFactory
    private val screenStateViewModel: StateModel by viewModels {
        QuestApplication.component.getQuestViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        QuestApplication.component.inject(this)
        val fragmentFactory = questFragmentFactory
        supportFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_activity)
        initObserver()
    }

    private fun initObserver() {
        screenStateViewModel.screenState.observe(this) { state ->
            var isNeedToBackStack = false
            val fragment = when (state) {
                is MainState -> {
                    Log.d(TAG, "MainState")
                    questFragmentFactory.createQuestFragment()
                }
                is ViewState -> {
                    Log.d(TAG, "ViewState")
                    isNeedToBackStack = true
                    questFragmentFactory.createQuestFragment().apply {
                        arguments = Bundle().apply {
                            putInt(QuestFragment.STEP_ID_ARG, id)
                        }
                    }
                }
                is HistoryState -> {
                    Log.d(TAG, "HistoryState")
                    isNeedToBackStack = true
                    questFragmentFactory.createQuestHistoryFragment()
                }
                is FinishScreenState -> {
                    Log.d(TAG, "FinishScreenState")
                    questFragmentFactory.createFinishFragment()
                }
            }
            val transaction = supportFragmentManager.beginTransaction()
            if (isNeedToBackStack) {
                transaction.add(
                    R.id.fragment_content,
                    fragment
                )
                transaction.addToBackStack(null)
            } else {
                transaction.replace(
                    R.id.fragment_content,
                    fragment
                )
            }
            transaction.commit()
        }
    }

    companion object {
        private const val TAG = "ContainerActivity"
    }
}