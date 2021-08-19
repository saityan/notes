package ru.geekbrains.notes;

public interface CardSource {
    int size();
    CardData getCardData(int position);
}
