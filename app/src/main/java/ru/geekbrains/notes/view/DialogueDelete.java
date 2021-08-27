package ru.geekbrains.notes.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import ru.geekbrains.notes.data.CardSource;

public class DialogueDelete extends DialogFragment {

    private final CardSource data;
    private final int position;
    private final NotesAdapter adapter;

    DialogueDelete(CardSource data, int position, NotesAdapter adapter) {
        this.data = data;
        this.position = position;
        this.adapter = adapter;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity())
                .setTitle("Are you sure you want to delete this note?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        data.deleteCardData(position);
                        adapter.notifyItemRemoved(position);
                        dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                });
        setCancelable(false);
        return builder.create();
    }
}
