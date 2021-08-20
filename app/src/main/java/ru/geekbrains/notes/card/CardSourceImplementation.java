package ru.geekbrains.notes.card;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.notes.R;

public class CardSourceImplementation implements CardSource {
    private List<CardData> dataSource;
    private Resources resources;

    @Override
    public int size() {
        return this.dataSource.size();
    }

    @Override
    public CardData getCardData(int position) {
        return this.dataSource.get(position);
    }

    public CardSourceImplementation(Resources resources) {
        this.resources = resources;
    }

    public CardSourceImplementation init() {
        dataSource = new ArrayList<>();
        String[] titles = resources.getStringArray(R.array.notes);
        String[] texts = resources.getStringArray(R.array.texts);
        for (int i = 0; i < titles.length; i++) {
            dataSource.add(new CardData(titles[i], texts[i]));
        }

        return this;
    }
}
