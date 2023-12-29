package ru.montgolfier.comments.app.view

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.montgolfier.comments.app.R
import ru.montgolfier.comments.app.di.StupidDi
import ru.montgolfier.comments.app.viewmodel.RoutingViewModel

class ContainerActivity : FragmentActivity() {
    private val viewModel: RoutingViewModel by viewModels { StupidDi.viewModelFactory }
    private val mainFragmentFactory: MainFragmentFactory by lazy {
        StupidDi.fragmentFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = mainFragmentFactory
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            viewModel.initState?.let {
                routeFragment(it, true)
            }
        }
        initViewModelListeners()
    }

    private fun initViewModelListeners() {
        lifecycleScope.launch {
            viewModel.stateFlow.collect {
                routeFragment(it)
            }
        }
    }

    private fun routeFragment(state: RoutingViewModel.State, isInit: Boolean = false) {
        val fragment = when (state) {
            RoutingViewModel.State.CommentsState -> mainFragmentFactory.createCommentsFragment()
            RoutingViewModel.State.GoodsState -> mainFragmentFactory.createGoodsFragment()
        }
        this.findViewById<ViewGroup>(android.R.id.content).postDelayed({
            val transaction = supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, fragment)
            if (!isInit) {
                transaction.addToBackStack(null)
            }
            transaction.commit()
        }, 5000)
    }
}
