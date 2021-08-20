package ru.geekbrains.notes.view;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import ru.geekbrains.notes.Note;
import ru.geekbrains.notes.R;

public class NoteFragment extends Fragment {

    public static String KEY_NOTE = "note";
    private Note note;

    public static NoteFragment newInstance(Note note) {
        NoteFragment fragment = new NoteFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_NOTE, note);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        TextView titleView = view.findViewById(R.id.title_view);
        TextView textView = view.findViewById(R.id.text_view);
        titleView.setText(this.note.getTitle());
        textView.setText(this.note.getText());
        titleView.setTypeface(null, Typeface.BOLD);
        textView.setTypeface(null, Typeface.ITALIC);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null)
            this.note = getArguments().getParcelable(KEY_NOTE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(KEY_NOTE, this.note);
        super.onSaveInstanceState(outState);
    }
}
