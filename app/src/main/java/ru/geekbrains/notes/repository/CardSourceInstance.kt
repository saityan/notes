package ru.geekbrains.notes.repository

import ru.geekbrains.notes.model.CardData

object CardSourceInstance : CardSource {
    private val cardSource = CardSourceRemoteImplementation()

    override fun size(): Int {
        return cardSource.size()
    }

    override fun getCardData(position: Int): CardData {
        return cardSource.getCardData(position)
    }

    override fun getCards(cardsSourceResponse: CardsSourceResponse): CardSource? {
        return cardSource.getCards(cardsSourceResponse)
    }

    override fun deleteCardData(position: Int) {
        cardSource.deleteCardData(position)
    }

    override fun updateCardData(position: Int, newCardData: CardData) {
        cardSource.updateCardData(position, newCardData)
    }

    override fun addCardData(newCardData: CardData) {
        cardSource.addCardData(newCardData)
    }

    override fun clearCardData() {
        cardSource.clearCardData()
    }
}
