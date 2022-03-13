package ru.geekbrains.notes.data

import com.google.firebase.Timestamp
import java.util.*

object CardDataTranslate {
    fun documentToCardData(id: String, doc: Map<String, Any?>): CardData {
        return CardData(
            id,
            doc[Fields.TITLE] as String,
            doc[Fields.TEXT] as String,
            (Objects.requireNonNull(doc[Fields.DATE]) as Timestamp).toDate()
        )
    }

    fun cardDataToDocument(cardData: CardData?): Map<String, Any> {
        val result: MutableMap<String, Any> = HashMap()
        if (cardData != null) {
            result[Fields.TITLE] = cardData.title
            result[Fields.TEXT] = cardData.text
            result[Fields.DATE] = cardData.date
        }
        return result
    }

    object Fields {
        const val TITLE = "title"
        const val TEXT = "description"
        const val DATE = "date"
    }
}
