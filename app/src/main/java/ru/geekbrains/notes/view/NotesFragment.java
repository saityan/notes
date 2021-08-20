package ru.geekbrains.notes.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.geekbrains.notes.Note;
import ru.geekbrains.notes.R;
import ru.geekbrains.notes.card.CardData;
import ru.geekbrains.notes.card.CardSource;
import ru.geekbrains.notes.card.CardSourceImplementation;

public class NotesFragment extends Fragment {

    Note currentNote;
    boolean isLandscape;
    private CardSource data;
    private NotesAdapter adapter;
    private RecyclerView recyclerView;

    public static NotesFragment newInstance() { return new NotesFragment(); }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        this.data = new CardSourceImplementation(getResources()).init();

        this.recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        this.adapter = new NotesAdapter(this.data);
        adapter.setNotesOnClickListener(new NotesOnClickListener() {
            @Override
            public void onNoteClick(View view, int position) {
                currentNote = new Note(data.getCardData(position).getTitle(),
                        data.getCardData(position).getText());
                isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
                showNote();
            }
        });
        recyclerView.setAdapter(adapter);

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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                this.data.addCardData(new CardData("New " + (this.data.size() + 1),
                        "New  description " + (this.data.size() + 1)));
                this.adapter.notifyItemInserted(this.data.size() - 1);
                this.recyclerView.smoothScrollToPosition(this.data.size() - 1);
                return true;
            case R.id.action_clear:
                data.clearCardData();
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
