package ru.geekbrains.notes;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private final CardSource dataSource;
    private NotesOnClickListener listener;

    public NotesAdapter(CardSource dataSource){ this.dataSource = dataSource; }

    public void setNotesOnClickListener (NotesOnClickListener listener) { this.listener = listener; }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.title.setText(dataSource.getCardData(position).getTitle());
//        holder.text.setText(dataSource.getCardData(position).getText());
    }

    @Override
    public int getItemCount() { return dataSource.size(); }

    public class NotesViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView text;

        public NotesViewHolder(View itemView) {
            super(itemView);

            this.title = itemView.findViewById(R.id.titleView);
            this.text = itemView.findViewById(R.id.description);

            this.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onNoteClick(view, getAdapterPosition());
                }
            });
        }
    }
}
