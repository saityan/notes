package ru.geekbrains.notes.card;

public interface CardSource {
    int size();
    CardData getCardData(int position);
}
