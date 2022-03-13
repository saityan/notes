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
import ru.geekbrains.notes.data.Note
import ru.geekbrains.notes.R
import ru.geekbrains.notes.data.CardData
import ru.geekbrains.notes.data.CardSource
import ru.geekbrains.notes.data.CardSourceRemoteImplementation
import ru.geekbrains.notes.data.CardsSourceResponse
import ru.geekbrains.notes.observation.Observer
import ru.geekbrains.notes.observation.Publisher
import ru.geekbrains.notes.view.CardUpdateFragment.Companion.newInstance
import ru.geekbrains.notes.view.NoteFragment.Companion.newInstance
import java.util.*

class NotesFragment : Fragment() {
    var currentNote: Note? = null
    var isLandscape = false
    private var data: CardSource? = null
    private var adapter: NotesAdapter? = null
    private var recyclerView: RecyclerView? = null
    private var navigation: Navigation? = null
    private var publisher: Publisher? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.fragment_notes, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView!!.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView!!.setLayoutManager(linearLayoutManager)
        adapter = NotesAdapter(this)
        adapter!!.setNotesOnClickListener(object : NotesOnClickListener {
            override fun onNoteClick(view: View, position: Int) {
                currentNote = Note(
                    data!!.getCardData(position)!!.title,
                    data!!.getCardData(position)!!.text
                )
                isLandscape =
                    resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
                showNote()
            }
        })
        recyclerView!!.setAdapter(adapter)
        val defaultItemAnimator = DefaultItemAnimator()
        defaultItemAnimator.changeDuration = 1000
        defaultItemAnimator.removeDuration = 1000
        recyclerView!!.setItemAnimator(defaultItemAnimator)
        data = CardSourceRemoteImplementation().init(object : CardsSourceResponse {
            override fun initialized(cardSource: CardSource) {
                adapter!!.notifyDataSetChanged()
            }
        })
        adapter!!.setDataSource(data)
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val activity = context as MainActivity
        navigation = activity.navigation
        publisher = activity.publisher
    }

    override fun onDetach() {
        navigation = null
        publisher = null
        super.onDetach()
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
                navigation!!.addFragment(CardUpdateFragment.newInstance(), true)
                publisher!!.subscribe(object : Observer {
                    override fun updateState(cardData: CardData) {
                        data!!.addCardData(cardData)
                        adapter!!.notifyItemInserted(data!!.size() - 1)
                    }
                })
                return true
            }
            R.id.action_clear -> {
                data!!.clearCardData()
                adapter!!.notifyDataSetChanged()
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
        val position = adapter!!.menuContextClickPosition
        when (item.itemId) {
            R.id.action_update_from_context -> {
                navigation!!.addFragment(data!!.getCardData(position)?.let { newInstance(it) }, true)
                publisher!!.subscribe(object : Observer {
                    override fun updateState(cardData: CardData) {
                        data!!.updateCardData(position, cardData)
                        adapter!!.notifyItemChanged(position)
                    }
                })
                return true
            }
            R.id.action_delete_from_context -> {
                val dialogueDelete = DialogueDelete()
                DialogueDelete.data = data!!
                DialogueDelete.position = position
                DialogueDelete.adapter = adapter!!
                dialogueDelete.show(
                    Objects.requireNonNull(activity)!!.supportFragmentManager,
                    "deletion check"
                )
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    companion object {
        fun newInstance(): NotesFragment {
            return NotesFragment()
        }
    }
}
