package ru.geekbrains.notes.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class CardData (
    var id: String,
    var title: String,
    var text: String,
    var date: Date
) : Parcelable {
    constructor() : this ("","","", Date())
}
