package ru.geekbrains.notes.observe;

import ru.geekbrains.notes.card.CardData;

public interface Observer {
    void updateState(CardData cardData);
}
