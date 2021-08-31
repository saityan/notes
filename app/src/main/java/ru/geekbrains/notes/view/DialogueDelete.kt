package ru.geekbrains.notes.view

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ru.geekbrains.notes.data.CardSource

class DialogueDelete internal constructor(
    private val data: CardSource,
    private val position: Int,
    private val adapter: NotesAdapter
) :
    DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
            .setTitle("Are you sure you want to delete this note?")
            .setPositiveButton(
                "Yes"
            ) { dialogInterface, i ->
                data.deleteCardData(position)
                adapter.notifyItemRemoved(position)
                dismiss()
            }
            .setNegativeButton(
                "No"
            ) { dialogInterface, i -> dismiss() }
        isCancelable = false
        return builder.create()
    }
}