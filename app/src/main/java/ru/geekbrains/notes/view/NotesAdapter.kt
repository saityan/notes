package ru.geekbrains.notes.view

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.notes.R
import ru.geekbrains.notes.data.CardSource
import ru.geekbrains.notes.view.NotesAdapter.NotesViewHolder

class NotesAdapter(private val fragment: Fragment) :
    RecyclerView.Adapter<NotesViewHolder>() {
    private var dataSource: CardSource? = null
    private var listener: NotesOnClickListener? = null
    var menuContextClickPosition = 0

    fun setNotesOnClickListener(listener: NotesOnClickListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_card_view, parent, false)
        return NotesViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.title.text = dataSource!!.getCardData(position)!!.title
        holder.text.text = dataSource!!.getCardData(position)!!.text
    }

    override fun getItemCount(): Int {
        return dataSource!!.size()
    }

    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var title: TextView
        internal var text: TextView

        init {
            title = itemView.findViewById(R.id.note_title)
            text = itemView.findViewById(R.id.description)
            fragment.registerForContextMenu(title)
            title.setOnClickListener { view -> listener!!.onNoteClick(view, adapterPosition) }
            title.setOnLongClickListener {
                menuContextClickPosition = adapterPosition
                title.showContextMenu(0f, 0f)
                true
            }
        }
    }

    fun setDataSource(dataSource: CardSource?) {
        this.dataSource = dataSource
        notifyDataSetChanged()
    }
}