package ru.geekbrains.notes.presenter

import ru.geekbrains.notes.model.CardData

interface PresenterContract {
    fun addCard()
    fun clear()
    fun getDataFromSource()
    fun updatePosition(position: Int)
    fun notify(cardData: CardData)
}
