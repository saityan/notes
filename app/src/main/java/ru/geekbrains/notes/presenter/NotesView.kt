package ru.geekbrains.notes.presenter

import ru.geekbrains.notes.data.CardData

interface NotesView {
    fun showNotes(notes: List<CardData>)
}
