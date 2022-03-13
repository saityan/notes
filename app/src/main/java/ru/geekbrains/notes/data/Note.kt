package ru.geekbrains.notes.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Note(
    var title: String?,
    var text: String?,
    val creationDate: String?,
    var dueDate: String?
) : Parcelable {

    constructor() : this("", "", Calendar.getInstance().time.toString(), "")

    constructor(title: String?, text: String?) :
            this(title, text, Calendar.getInstance().time.toString(), "")
}
