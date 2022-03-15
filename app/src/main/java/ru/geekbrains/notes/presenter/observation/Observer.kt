package ru.geekbrains.notes.presenter.observation

import ru.geekbrains.notes.model.CardData

interface Observer {
    fun updateState(cardData: CardData)
}
