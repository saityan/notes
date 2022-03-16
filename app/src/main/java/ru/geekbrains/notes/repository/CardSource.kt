package ru.geekbrains.notes.repository

import ru.geekbrains.notes.model.CardData

interface CardSource {
    fun size(): Int
    fun getCardData(position: Int): CardData
    fun getCards(cardsSourceResponse: CardsSourceResponse): CardSource?
    fun deleteCardData(position: Int)
    fun updateCardData(position: Int, newCardData: CardData)
    fun addCardData(newCardData: CardData)
    fun clearCardData()
}
