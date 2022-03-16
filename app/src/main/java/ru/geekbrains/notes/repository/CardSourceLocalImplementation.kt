package ru.geekbrains.notes.repository

import android.content.res.Resources
import ru.geekbrains.notes.R
import ru.geekbrains.notes.model.CardData
import java.util.*

class CardSourceLocalImplementation(private val resources: Resources) :
    CardSource {
    private var dataSource: MutableList<CardData?>? = null
    override fun size(): Int {
        return dataSource!!.size
    }

    override fun getCardData(position: Int): CardData {
        return dataSource!![position]!!
    }

    override fun deleteCardData(position: Int) {
        dataSource!!.removeAt(position)
    }

    override fun updateCardData(position: Int, newCardData: CardData) {
        dataSource!![position] = newCardData
    }

    override fun addCardData(newCardData: CardData) {
        dataSource!!.add(newCardData)
    }

    override fun clearCardData() {
        dataSource!!.clear()
    }

    override fun getCards(cardsSourceResponse: CardsSourceResponse): CardSource {
        dataSource = ArrayList()
        val titles = resources.getStringArray(R.array.notes)
        val texts = resources.getStringArray(R.array.texts)
        for (i in titles.indices) {
            (dataSource as ArrayList<CardData?>).add(
                CardData(
                i.toString(), titles[i], texts[i], Calendar.getInstance().time)
            )
        }
        cardsSourceResponse.initialized(this)
        return this
    }
}
