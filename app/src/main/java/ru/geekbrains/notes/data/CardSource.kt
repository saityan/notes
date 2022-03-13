package ru.geekbrains.notes.data

interface CardSource {
    fun size(): Int
    fun getCardData(position: Int): CardData
    fun init(cardsSourceResponse: CardsSourceResponse?): CardSource?
    fun deleteCardData(position: Int)
    fun updateCardData(position: Int, newCardData: CardData)
    fun addCardData(newCardData: CardData)
    fun clearCardData()
}
