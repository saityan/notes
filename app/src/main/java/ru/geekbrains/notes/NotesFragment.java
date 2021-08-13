package ru.geekbrains.notes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class NotesFragment extends Fragment {

    Note currentNote;
    boolean isLandscape;
    public static String KEY_NOTE = "note";

    public static NotesFragment newInstance() { return new NotesFragment(); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        LinearLayout layout = (LinearLayout) view;
        String[] notes = getResources().getStringArray(R.array.notes);

        for (int i = 0; i < notes.length; i++) {
            String name = notes[i];
            TextView textView = new TextView(getContext());
            textView.setText(name);
            textView.setTextSize(30);
            textView.setGravity(Gravity.END);
            layout.addView(textView);
            int finalI = i;
            textView.setOnClickListener(view1 -> {
                currentNote = new Note((getResources().getStringArray(R.array.notes)[finalI]),
                        (getResources().getStringArray(R.array.texts)[finalI]));
                isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
                showNote();
            });
        }
        return view;
    }

    private void showNote() {
        if (isLandscape) {
            showNoteLand();
        } else {
            showNotePort();
        }
    }

    private void showNotePort() {
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.notes_container, NoteFragment.newInstance(currentNote))
                .addToBackStack("")
                .commit();
    }

    private void showNoteLand() {
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.note_container, NoteFragment.newInstance(currentNote))
                .commit();
    }
}
