package ru.geekbrains.notes

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import ru.geekbrains.notes.model.CardData
import ru.geekbrains.notes.presenter.MainPresenter
import ru.geekbrains.notes.presenter.NotesViewContract
import ru.geekbrains.notes.presenter.observation.Publisher
import ru.geekbrains.notes.repository.CardSource

class MainPresenterTest {
    private lateinit var mainPresenter: MainPresenter

    @Mock
    private lateinit var cardSource: CardSource

    @Mock
    private lateinit var viewContract: NotesViewContract

    @Mock
    private lateinit var publisher: Publisher

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainPresenter = MainPresenter(viewContract, cardSource, publisher)
    }

    @Test
    fun getDataFromSource_Test() {
        mainPresenter.getDataFromSource()
        verify(cardSource).getCards(mainPresenter.getCardsSourceResponse())
    }

    @Test
    fun updatePosition_Test() {
        val position = 0
        val cardData = CardData()
        mainPresenter.updatePosition(position)
        mainPresenter.notify(cardData)
        verify(publisher).notifyTask(cardData)
    }
}
