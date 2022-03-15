package ru.geekbrains.notes.observation

import ru.geekbrains.notes.model.CardData

class Publisher {
    private val observers: MutableList<Observer>
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

    init {
        observers = ArrayList()
    }
}
