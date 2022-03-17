package ru.geekbrains.notes.presenter.observation

import ru.geekbrains.notes.model.CardData

object Publisher {
    private val observers = ArrayList<Observer>()
    fun subscribe(observer: Observer) {
        observers.add(observer)
    }

    private fun unsubscribe(observer: Observer) {
        observers.remove(observer)
    }

    fun notifyTask(cardData: CardData) {
        for (observer in observers) {
            observer.updateState(cardData)
            unsubscribe(observer)
        }
    }
}
