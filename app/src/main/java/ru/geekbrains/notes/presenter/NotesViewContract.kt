package ru.geekbrains.notes.presenter

import ru.geekbrains.notes.model.CardData

interface NotesViewContract {
    fun setAdapter(data: List<CardData>)
    fun setData(data: List<CardData>)
}
