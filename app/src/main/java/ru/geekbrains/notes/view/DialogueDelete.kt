package ru.geekbrains.notes.view

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ru.geekbrains.notes.model.CardData
import ru.geekbrains.notes.presenter.MainMainPresenter

class DialogueDelete(
    private val presenter: MainMainPresenter,
    private val cardData: CardData,
    private val position: Int
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
            .setTitle("Are you sure you want to delete this note?")
            .setPositiveButton(
                "Yes"
            ) { dialogInterface, i ->
                presenter.deleteCard(position)
                presenter.notify(cardData)
                dismiss()
            }
            .setNegativeButton(
                "No"
            ) { dialogInterface, i -> dismiss() }
        isCancelable = false
        return builder.create()
    }
}
