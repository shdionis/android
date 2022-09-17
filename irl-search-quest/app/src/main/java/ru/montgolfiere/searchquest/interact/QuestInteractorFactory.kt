package ru.montgolfiere.searchquest.interact

import ru.montgolfiere.searchquest.config.QuestConfig
import ru.montgolfiere.searchquest.model.repository.QuestRepository

class QuestInteractorFactory(
    private val repository: QuestRepository,
    private val config: QuestConfig
) {
    fun createInteractor(): QuestInteractor = QuestInteractor(repository, config)
}