package ru.geekbrains.notes.presenter

import ru.geekbrains.notes.model.CardData
import ru.geekbrains.notes.model.CardSource
import ru.geekbrains.notes.model.CardSourceRemoteImplementation
import ru.geekbrains.notes.model.CardsSourceResponse
import ru.geekbrains.notes.presenter.observation.Observer
import ru.geekbrains.notes.presenter.observation.Publisher
import ru.geekbrains.notes.view.NotesFragment

class MainPresenter (fragment: NotesFragment) : PresenterContract {
    private val notesView = fragment
    private val publisher = Publisher()
    private var cardsData : CardSource? = null
    private var data = mutableListOf<CardData>()

    override fun getDataFromSource() {
        cardsData = CardSourceRemoteImplementation().getCards(object : CardsSourceResponse {
            override fun initialized(cardSource: CardSource) {
                updateViewData()
            }
        })
    }

    override fun updatePosition(position: Int) {
        publisher.subscribe(object : Observer {
            override fun updateState(cardData: CardData) {
                cardsData?.updateCardData(position, cardData)
                updateViewData()
            }
        })
    }

    override fun deleteCard(position: Int) {
        publisher.subscribe(object : Observer {
            override fun updateState(cardData: CardData) {
                cardsData?.deleteCardData(position)
                cardsData = CardSourceRemoteImplementation().getCards(object : CardsSourceResponse {
                    override fun initialized(cardSource: CardSource) {
                        updateViewData()
                    }
                })
            }
        })
    }

    override fun addCard() {
        publisher.subscribe(object : Observer {
            override fun updateState(cardData: CardData) {
                cardsData?.addCardData(cardData)
                updateViewData()
            }
        })
    }

    override fun clear() {
        cardsData?.clearCardData()
        data = mutableListOf()
    }

    override fun notify(cardData: CardData) {
        publisher.notifyTask(cardData)
    }

    private fun convertCardsToData() {
        cardsData?.let {
            val size = it.size()
            if (size > 0) {
                data = mutableListOf()
                for (i in 0 until size) {
                    data.add(it.getCardData(i))
                }
            }
        }
    }

    private fun updateViewData() {
        convertCardsToData()
        notesView.setData(data)
        notesView.setAdapter(data)
    }
}
