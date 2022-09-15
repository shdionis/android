package ru.montgolfiere.searchquest.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import ru.montgolfiere.searchquest.viewmodels.QuestViewModelFactory

class QuestFragmentFactory(
    private val questViewModelFactory: QuestViewModelFactory,
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            QuestFragment::class.java.name -> createQuestFragment()
            QuestHistoryFragment::class.java.name -> createQuestHistoryFragment()
            QuestFinishFragment::class.java.name -> createFinishFragment()
            QuestFinishImageModalFragment::class.java.name -> createQuestFinishImageModalFragment()
            else -> super.instantiate(classLoader, className)
        }
    }

    fun createQuestFinishImageModalFragment(): QuestFinishImageModalFragment =
        QuestFinishImageModalFragment()

    fun createQuestHistoryFragment(): Fragment = QuestHistoryFragment(questViewModelFactory)

    fun createQuestFragment(): Fragment = QuestFragment(questViewModelFactory)

    fun createFinishFragment(): Fragment = QuestFinishFragment(questViewModelFactory, this)
}