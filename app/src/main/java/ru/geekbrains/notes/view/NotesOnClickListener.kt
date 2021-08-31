package ru.geekbrains.notes.view

import android.view.View

interface NotesOnClickListener {
    fun onNoteClick(view: View, position: Int)
}