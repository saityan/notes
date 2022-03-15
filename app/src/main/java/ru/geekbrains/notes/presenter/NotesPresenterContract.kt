package ru.geekbrains.notes.presenter

interface NotesPresenterContract {
    fun addCard()
    fun clear()
    fun getDataFromSource()
    fun updatePosition(position: Int)
}
