package ru.geekbrains.notes.presenter

import ru.geekbrains.notes.model.CardData
import ru.geekbrains.notes.presenter.observation.Observer
import ru.geekbrains.notes.presenter.observation.Publisher
import ru.geekbrains.notes.repository.CardSource
import ru.geekbrains.notes.repository.CardsSourceResponse

class MainPresenter (
    private val notesViewContract: NotesViewContract,
    private val repository : CardSource,
    private val publisher : Publisher
) : MainPresenterContract {

    private var cardsData : CardSource? = null
    private var data = mutableListOf<CardData>()
    private val cardsSourceResponse = object : CardsSourceResponse {
        override fun initialized(cardSource: CardSource) {
            updateViewData()
        }
    }

    override fun getDataFromSource() {
        cardsData = repository.getCards(cardsSourceResponse)
    }

    override fun addCard() {
        publisher.subscribe(object : Observer {
            override fun updateState(cardData: CardData) {
                cardsData?.addCardData(cardData)
                updateViewData()
            }
        })
    }

    override fun deleteCard(position: Int) {
        publisher.subscribe(object : Observer {
            override fun updateState(cardData: CardData) {
                cardsData?.deleteCardData(position)
                cardsData = repository.getCards(object : CardsSourceResponse {
                    override fun initialized(cardSource: CardSource) {
                        updateViewData()
                    }
                })
            }
        })
    }

    override fun updateCard(position: Int) {
        publisher.subscribe(object : Observer {
            override fun updateState(cardData: CardData) {
                cardsData?.updateCardData(position, cardData)
                updateViewData()
            }
        })
    }

    override fun clear() {
        repository.clearCardData()
        cardsData = repository.getCards(object : CardsSourceResponse {
            override fun initialized(cardSource: CardSource) {
                updateViewData()
            }
        })
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
        notesViewContract.setData(data)
        notesViewContract.setAdapter(data)
    }

    //for tests
    fun getCardsSourceResponse() : CardsSourceResponse = this.cardsSourceResponse
}
