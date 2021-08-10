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

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.notes_container, NotesFragment.newInstance())
                .commit();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.note_container, NoteFragment.newInstance(new Note ("test", "another test")))
                    .commit();
        }
    }
}
