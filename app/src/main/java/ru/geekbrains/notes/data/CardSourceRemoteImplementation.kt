package ru.geekbrains.notes.data

import com.google.firebase.firestore.*
import java.util.*

class CardSourceRemoteImplementation : CardSource {
    private val store = FirebaseFirestore.getInstance()
    private val collectionReference = store.collection(CARDS_COLLECTION)
    private var cardsData: MutableList<CardData> = ArrayList()
    override fun size(): Int {
        return cardsData.size
    }

    override fun getCardData(position: Int): CardData {
        return cardsData[position]
    }

    override fun init(cardsSourceResponse: CardsSourceResponse?): CardSource {
        collectionReference.orderBy(CardDataTranslate.Fields.DATE, Query.Direction.DESCENDING).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    cardsData = ArrayList()
                    for (docFields in Objects.requireNonNull(task.result)!!) {
                        val cardData = CardDataTranslate.documentToCardData(
                            docFields.id,
                            docFields.data
                        )
                        cardsData.add(cardData)
                    }
                    cardsSourceResponse!!.initialized(this@CardSourceRemoteImplementation)
                }
            }
        return this
    }

    override fun deleteCardData(position: Int) {
        collectionReference.document(cardsData[position].id!!).delete()
    }

    override fun updateCardData(position: Int, newCardData: CardData?) {
        collectionReference.document(cardsData[position].id!!).update(
            CardDataTranslate
                .cardDataToDocument(newCardData)
        )
    }

    override fun addCardData(newCardData: CardData?) {
        collectionReference.add(CardDataTranslate.cardDataToDocument(newCardData))
    }

    override fun clearCardData() {
        for (cardData in cardsData) {
            collectionReference.document(cardData.id!!).delete()
        }
    }

    companion object {
        private const val CARDS_COLLECTION = "cards"
    }
}