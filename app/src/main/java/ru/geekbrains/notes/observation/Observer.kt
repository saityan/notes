package ru.geekbrains.notes.observation

import ru.geekbrains.notes.data.CardData

interface Observer {
    fun updateState(cardData: CardData)
}