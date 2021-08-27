package ru.geekbrains.notes.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import ru.geekbrains.notes.MainActivity;
import ru.geekbrains.notes.Note;
import ru.geekbrains.notes.R;
import ru.geekbrains.notes.data.CardData;
import ru.geekbrains.notes.data.CardSource;
import ru.geekbrains.notes.data.CardSourceRemoteImplementation;
import ru.geekbrains.notes.data.CardsSourceResponse;
import ru.geekbrains.notes.observation.Observer;
import ru.geekbrains.notes.observation.Publisher;

public class NotesFragment extends Fragment {

    Note currentNote;
    boolean isLandscape;
    private CardSource data;
    private NotesAdapter adapter;
    private RecyclerView recyclerView;
    private Navigation navigation;
    private Publisher publisher;

    public static NotesFragment newInstance() { return new NotesFragment(); }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        this.recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        this.adapter = new NotesAdapter(this);

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

        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setChangeDuration(1000);
        defaultItemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(defaultItemAnimator);

        this.data = new CardSourceRemoteImplementation().init(new CardsSourceResponse() {
            @Override
            public void initialized(CardSource cardSource) {
                adapter.notifyDataSetChanged();
            }
        });
        adapter.setDataSource(data);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        this.navigation = activity.getNavigation();
        this.publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        this.navigation = null;
        this.publisher = null;
        super.onDetach();
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
                this.navigation.addFragment(CardUpdateFragment.newInstance(), true);
                this.publisher.subscribe(new Observer() {
                    @Override
                    public void updateState(CardData cardData) {
                        data.addCardData(cardData);
                        adapter.notifyItemInserted(data.size() - 1);
                    }
                });
                return true;
            case R.id.action_clear:
                data.clearCardData();
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        requireActivity().getMenuInflater().inflate(R.menu.card_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = adapter.getMenuContextClickPosition();
        switch (item.getItemId()) {
            case R.id.action_update_from_context:
                navigation.addFragment(CardUpdateFragment.newInstance(data.getCardData(position)), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateState(CardData cardData) {
                        data.updateCardData(position, cardData);
                        adapter.notifyItemChanged(position);
                    }
                });
                return true;
            case R.id.action_delete_from_context:
                DialogueDelete dialogueDelete = new DialogueDelete();
                dialogueDelete.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(),
                        "deletion check");
                if (dialogueDelete.isYes()) {
                    Toast.makeText(getContext(), "deleted", Toast.LENGTH_SHORT).show();
                    //data.deleteCardData(position);
                    //adapter.notifyItemRemoved(position);
                }
                return true;
        }
        return super.onContextItemSelected(item);
    }
}
