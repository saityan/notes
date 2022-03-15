package ru.geekbrains.notes.presenter

import android.util.Log
import ru.geekbrains.notes.data.CardData
import ru.geekbrains.notes.data.CardSource
import ru.geekbrains.notes.data.CardSourceRemoteImplementation
import ru.geekbrains.notes.data.CardsSourceResponse
import ru.geekbrains.notes.observation.Observer
import ru.geekbrains.notes.observation.Publisher
import ru.geekbrains.notes.view.NotesFragment

class NotesPresenter (fragment: NotesFragment) : NotesPresenterContract {
    private val notesView = fragment
    private val publisher = Publisher()
    private lateinit var cardsData : CardSource
    private var data = mutableListOf<CardData>()

    override fun getDataFromSource(){
        cardsData = CardSourceRemoteImplementation().getCards(object : CardsSourceResponse {
            override fun initialized(cardSource: CardSource) {
                updateViewData()
            }
        })
    }

    override fun updatePosition(position: Int) {
        publisher.subscribe(object : Observer {
            override fun updateState(cardData: CardData) {
                Log.d("myLogs", "updatePosition")
                cardsData.updateCardData(position, cardData)
                updateViewData()
            }
        })
    }

    override fun addCard() {
        publisher.subscribe(object : Observer {
            override fun updateState(cardData: CardData) {
                Log.d("myLogs", "addCard")
                cardsData.addCardData(cardData)
                updateViewData()
            }
        })
    }

    override fun clear() {
        cardsData.clearCardData()
        data = mutableListOf()
    }

    private fun convertCardsToData() {
        val size = cardsData.size()
        if (size > 0) {
            data = mutableListOf()
            for (i in 0 until size) {
                data.add(cardsData.getCardData(i))
            }
        }
    }

    private fun updateViewData() {
        convertCardsToData()
        notesView.setData(data)
        notesView.setAdapter(data)
    }
}
