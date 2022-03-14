package ru.geekbrains.notes.presenter

import ru.geekbrains.notes.data.CardSource
import ru.geekbrains.notes.data.CardSourceRemoteImplementation
import ru.geekbrains.notes.data.CardsSourceResponse
import ru.geekbrains.notes.view.NotesFragment

class NotesPresenter (fragment: NotesFragment) : NotesPresenterContract {
    private val notesView = fragment

    override fun giveCardsToView(): CardSource {
        val data = CardSourceRemoteImplementation().getCards(object : CardsSourceResponse {
            override fun initialized(cardSource: CardSource) {  }
        })
        return data
    }
}
