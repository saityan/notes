package ru.geekbrains.notes.presenter

import ru.geekbrains.notes.data.CardData

interface NotesPresenterContract {
    fun addCard()
    fun clear()
    fun getDataFromSource()
    fun updatePosition(position: Int)
    fun notify(cardData: CardData)
}
