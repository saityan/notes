package ru.geekbrains.notes.view

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.view.ContextMenu.ContextMenuInfo
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.notes.MainActivity
import ru.geekbrains.notes.R
import ru.geekbrains.notes.model.CardData
import ru.geekbrains.notes.model.Note
import ru.geekbrains.notes.presenter.MainPresenter
import ru.geekbrains.notes.presenter.NotesViewContract
import ru.geekbrains.notes.view.CardUpdateFragment.Companion.newInstance
import ru.geekbrains.notes.view.NoteFragment.Companion.newInstance

class NotesFragment : Fragment(), NotesViewContract {
    private val presenter: MainPresenter = MainPresenter(this)
    var currentNote: Note = Note()
    var isLandscape = false
    private var data: List<CardData> = listOf()
    private lateinit var adapter: NotesAdapter
    private lateinit var recyclerView: RecyclerView
    private var navigation: Navigation? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.fragment_notes, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager

        adapter = NotesAdapter(this)
        adapter.setNotesOnClickListener(object : NotesOnClickListener {
            override fun onNoteClick(view: View, position: Int) {
                currentNote = Note(
                    data[position].title,
                    data[position].text
                )
                isLandscape =
                    resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
                showNote()
            }
        })
        recyclerView.adapter = adapter
        adapter.setDataSource(data)
        presenter.getDataFromSource()

        val defaultItemAnimator = DefaultItemAnimator()
        defaultItemAnimator.changeDuration = 1000
        defaultItemAnimator.removeDuration = 1000
        recyclerView.itemAnimator = defaultItemAnimator
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val activity = context as MainActivity
        navigation = activity.navigation
    }

    private fun showNote() {
        if (isLandscape) {
            showNoteLand()
        } else {
            showNotePort()
        }
    }

    private fun showNotePort() {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.notes_container, newInstance(currentNote))
            .addToBackStack("")
            .commit()
    }

    private fun showNoteLand() {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.note_container, newInstance(currentNote))
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> {
                navigation?.addFragment(newInstance(presenter), true)
                presenter.addCard()
                return true
            }
            R.id.action_clear -> {
                presenter.clear()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        requireActivity().menuInflater.inflate(R.menu.card_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = adapter.menuContextClickPosition
        when (item.itemId) {
            R.id.action_update_from_context -> {
                navigation?.addFragment(newInstance(data[position], presenter), true)
                presenter.updatePosition(position)
                return true
            }
            R.id.action_delete_from_context -> {
                val dialogueDelete = DialogueDelete(presenter, data[position], position)
                dialogueDelete.show(
                    requireActivity().supportFragmentManager,
                    "deletion check"
                )
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun showNotes(notes: List<CardData>) {

    }

    override fun setData(data: List<CardData>) {
        this.data = data
    }

    override fun setAdapter(data: List<CardData>) {
        this.adapter.setDataSource(data)
        this.adapter.notifyDataSetChanged()
    }

    companion object {
        fun newInstance(): NotesFragment {
            return NotesFragment()
        }
    }
}
