package ru.geekbrains.notes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NotesFragment extends Fragment {

    Note currentNote;
    boolean isLandscape;

    public static NotesFragment newInstance() { return new NotesFragment(); }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        CardSource data = new CardSourceImplementation(getResources()).init();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        NotesAdapter notesAdapter = new NotesAdapter(data);
        notesAdapter.setNotesOnClickListener(new NotesOnClickListener() {
            @Override
            public void onNoteClick(View view, int position) {
                currentNote = new Note(data.getCardData(position).getTitle(),
                        data.getCardData(position).getText());
                isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
                showNote();
            }
        });
        recyclerView.setAdapter(notesAdapter);

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
