package ru.geekbrains.notes;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    LinkedList<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    void initNotes() {
        notes = new LinkedList<>();
        notes.add(new Note("grocery store", "buy some cheese and vegetables"));
        notes.add(new Note("pick up Mike", "take Mike home after school"));
        notes.add(new Note("dentist", "appointment is at 19-00 on Friday"));
    }
}
