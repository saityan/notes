package ru.geekbrains.notes.observation

import ru.geekbrains.notes.model.CardData

interface Observer {
    fun updateState(cardData: CardData)
}
