package ru.geekbrains.notes.data;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ru.geekbrains.notes.R;

public class CardSourceLocalImplementation implements CardSource {
    private List<CardData> dataSource;
    private final Resources resources;

    @Override
    public int size() {
        return this.dataSource.size();
    }

    @Override
    public CardData getCardData(int position) {
        return this.dataSource.get(position);
    }

    @Override
    public void deleteCardData(int position) {
        dataSource.remove(position);
    }

    @Override
    public void updateCardData(int position, CardData newCardData) {
        dataSource.set(position, newCardData);
    }

    @Override
    public void addCardData(CardData newCardData) {
        dataSource.add(newCardData);
    }

    @Override
    public void clearCardData() {
        dataSource.clear();
    }

    public CardSourceLocalImplementation(Resources resources) {
        this.resources = resources;
    }

    @Override
    public CardSource init(CardsSourceResponse cardsSourceResponse) {
        dataSource = new ArrayList<>();
        String[] titles = resources.getStringArray(R.array.notes);
        String[] texts = resources.getStringArray(R.array.texts);

        for (int i = 0; i < titles.length; i++) {
            dataSource.add(new CardData(titles[i], texts[i], Calendar.getInstance().getTime()));
        }

        if(cardsSourceResponse != null) {
            cardsSourceResponse.initialized(this);
        }

        return this;
    }
}
