package ru.montgolfiere.searchquest.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.montgolfiere.searchquest.interact.QuestInteractor

class QuestViewModelFactory(
    private val questInteractor: QuestInteractor
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when(modelClass) {
            QuestViewModel::class.java -> createQuestViewModel() as T
            else -> super.create(modelClass)
        }
    }

    private fun createQuestViewModel(): QuestViewModel = QuestViewModel(questInteractor)
}