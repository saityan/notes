package ru.geekbrains.notes;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class NoteFragment extends Fragment {

    public static String ARG_NOTE = "note";
    private Note note;

    public static NoteFragment newInstance(Note note) {
        NoteFragment fragment = new NoteFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_NOTE, note);
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
            this.note = getArguments().getParcelable(ARG_NOTE);
    }
}
