package ru.geekbrains.notes.data

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import java.util.*

class CardData : Parcelable {
    var id: String? = null
    var title: String?
    var text: String?
    var date: Date? = null

    constructor(title: String?, text: String?, date: Date?) {
        this.title = title
        this.text = text
        this.date = date
    }

    protected constructor(`in`: Parcel) {
        title = `in`.readString()
        text = `in`.readString()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(title)
        dest.writeString(text)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Creator<CardData> {
        override fun createFromParcel(parcel: Parcel): CardData {
            return CardData(parcel)
        }

        override fun newArray(size: Int): Array<CardData?> {
            return arrayOfNulls(size)
        }
    }
}