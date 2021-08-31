package ru.geekbrains.notes

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import java.util.*

class Note : Parcelable {
    var title: String?
    var text: String?
    val creationDate: String?
    var dueDate: String?

    constructor(title: String?, text: String?) {
        this.title = title
        this.text = text
        creationDate = Calendar.getInstance().time.toString()
        dueDate = ""
    }

    protected constructor(`in`: Parcel) {
        title = `in`.readString()
        text = `in`.readString()
        creationDate = `in`.readString()
        dueDate = `in`.readString()
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeString(title)
        parcel.writeString(text)
        parcel.writeString(creationDate)
        parcel.writeString(dueDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Creator<Note> {
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }
}