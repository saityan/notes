package ru.geekbrains.notes.data;

public interface CardSource {
    int size();
    CardData getCardData(int position);
    CardSource init(CardsSourceResponse cardsSourceResponse);

    void deleteCardData(int position);
    void updateCardData(int position, CardData newCardData);
    void addCardData(CardData newCardData);
    void clearCardData();
}
