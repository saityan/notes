package ru.geekbrains.notes.presenter

import ru.geekbrains.notes.model.CardData

interface MainPresenterContract {
    fun addCard()
    fun clear()
    fun deleteCard(position: Int)
    fun getDataFromSource()
    fun notify(cardData: CardData)
    fun updateCard(position: Int)
}
