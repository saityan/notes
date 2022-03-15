package ru.geekbrains.notes.view

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.geekbrains.notes.model.Note
import ru.geekbrains.notes.R

class NoteFragment : Fragment() {
    private var note = Note()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return initView(inflater, container)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null)
            requireArguments().getParcelable<Note>(KEY_NOTE)?.let { note = it }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(KEY_NOTE, note)
        super.onSaveInstanceState(outState)
    }

    private fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_note, container, false)
        val titleView = view.findViewById<TextView>(R.id.title_view)
        val textView = view.findViewById<TextView>(R.id.text_view)
        titleView.text = note.title
        textView.text = note.text
        titleView.setTypeface(null, Typeface.BOLD)
        textView.setTypeface(null, Typeface.ITALIC)
        return view
    }

    companion object {
        var KEY_NOTE = "note"
        fun newInstance(note: Note): NoteFragment {
            val fragment = NoteFragment()
            val bundle = Bundle()
            bundle.putParcelable(KEY_NOTE, note)
            fragment.arguments = bundle
            return fragment
        }
    }
}
