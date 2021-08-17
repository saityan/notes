package ru.geekbrains.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private String[] titlesList;
    private NotesOnClickListener listener;

    public NotesAdapter(String[] titlesList){ this.titlesList = titlesList; }

    public void setNotesOnClickListener (NotesOnClickListener listener) { this.listener = listener; }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.getTextView().setText(titlesList[position]);
    }

    @Override
    public int getItemCount() { return titlesList.length; }

    public class NotesViewHolder extends RecyclerView.ViewHolder {

        private TextView titleView;
        private TextView textView;
        private ImageView imageView;

        public NotesViewHolder(View itemView) {
            super(itemView);
            this.titleView = itemView.findViewById(R.id.titleView);
            this.textView = itemView.findViewById(R.id.text_view);
            titleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onNoteClick(view, getAdapterPosition());
                }
            });
        }

        public TextView getTitleView() {
            return titleView;
        }

        public void setTitleView(TextView titleView) {
            this.titleView = titleView;
        }

        public TextView getTextView() {
            return textView;
        }

        public void setTextView(TextView textView) {
            this.textView = textView;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }
    }
}
