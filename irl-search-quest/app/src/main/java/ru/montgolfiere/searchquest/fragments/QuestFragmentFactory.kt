package ru.montgolfiere.searchquest.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import ru.montgolfiere.searchquest.viewmodels.QuestViewModelFactory

class QuestFragmentFactory(
    val questViewModelFactory: QuestViewModelFactory,
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            QuestFragment::class.java.name -> createQuestFragment()
            else -> super.instantiate(classLoader, className)
        }
    }

    fun createQuestFragment(): Fragment = QuestFragment(questViewModelFactory)
}