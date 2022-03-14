package ru.geekbrains.notes.presenter

import ru.geekbrains.notes.data.CardSource

interface NotesPresenterContract {
    fun giveCardsToView() : CardSource
}
