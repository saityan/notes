package ru.geekbrains.notes.data

import com.google.firebase.Timestamp
import java.util.*

object CardDataTranslate {
    fun documentToCardData(id: String?, doc: Map<String?, Any?>): CardData {
        val result = CardData(
            doc[Fields.TITLE] as String?,
            doc[Fields.DESCRIPTION] as String?,
            (Objects.requireNonNull(doc[Fields.DATE]) as Timestamp).toDate()
        )
        result.id = id
        return result
    }

    fun cardDataToDocument(cardData: CardData?): Map<String, Any> {
        val result: MutableMap<String, Any> = HashMap()
        if (cardData != null) {
            result[Fields.TITLE] = cardData.title
        }
        if (cardData != null) {
            result[Fields.DESCRIPTION] = cardData.text
        }
        if (cardData != null) {
            result[Fields.DATE] = cardData.date
        }
        return result
    }

    object Fields {
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val DATE = "date"
    }
}