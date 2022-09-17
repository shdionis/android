package ru.montgolfiere.searchquest.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.montgolfiere.searchquest.interact.QuestInteractorFactory

class QuestViewModelFactory(
    private val questInteractorFactory: QuestInteractorFactory
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when(modelClass) {
            QuestViewModel::class.java -> createQuestViewModel() as T
            StateModel::class.java -> createStateModel() as T
            else -> super.create(modelClass)
        }
    }

    private fun createStateModel(): StateModel = StateModel(questInteractorFactory.createInteractor())

    private fun createQuestViewModel(): QuestViewModel = QuestViewModel(questInteractorFactory.createInteractor())
}