package ru.geekbrains.notes;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Calendar;

public class Note implements Parcelable {
    private String title;
    private String text;
    private final String creationDate;
    private String dueDate;

    public Note(String title, String text) {
        this.title = title;
        this.text = text;
        this.creationDate = Calendar.getInstance().getTime().toString();
        this.dueDate = "";
    }

    protected Note(Parcel in) {
        title = in.readString();
        text = in.readString();
        creationDate = in.readString();
        dueDate = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(text);
        parcel.writeString(creationDate);
        parcel.writeString(dueDate);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreationDateDate() {
        return creationDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
