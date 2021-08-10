package ru.geekbrains.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    LinkedList<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initNotes();

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.notes_container, NotesFragment.newInstance())
                .commit();
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.note_container, NoteFragment.newInstance(notes.getFirst()))
                    .commit();
        }
    }

    void initNotes() {
        notes = new LinkedList<>();
        notes.add(new Note("grocery store", "buy some cheese and vegetables"));
        notes.add(new Note("pick up Mike", "take Mike home after school"));
        notes.add(new Note("dentist", "appointment is at 19-00 on Friday"));
    }
}
