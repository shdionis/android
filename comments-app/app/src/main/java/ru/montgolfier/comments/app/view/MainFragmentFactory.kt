package ru.montgolfier.comments.app.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider

class MainFragmentFactory(
    private val viewModelFactory: ViewModelProvider.Factory
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
        when (className) {
            FragmentGoods::class.java.name -> createGoodsFragment()
            FragmentComments::class.java.name -> createCommentsFragment()
            else -> super.instantiate(classLoader, className)
        }

    fun createGoodsFragment(): Fragment = FragmentGoods()
    fun createCommentsFragment(): Fragment = FragmentComments(viewModelFactory)
}