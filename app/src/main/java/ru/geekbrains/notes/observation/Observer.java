package ru.geekbrains.notes.observation;

import ru.geekbrains.notes.data.CardData;

public interface Observer {
    void updateState(CardData cardData);
}
