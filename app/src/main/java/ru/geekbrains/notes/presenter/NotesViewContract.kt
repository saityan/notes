package ru.geekbrains.notes.presenter

import ru.geekbrains.notes.model.CardData

interface NotesViewContract {
    fun showNotes(notes: List<CardData>)
    fun setData(data: List<CardData>)
    fun setAdapter(data: List<CardData>)
}
