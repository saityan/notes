package ru.geekbrains.notes

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import ru.geekbrains.notes.presenter.MainPresenter
import ru.geekbrains.notes.presenter.NotesViewContract
import ru.geekbrains.notes.repository.CardSource
import ru.geekbrains.notes.repository.CardsSourceResponse

class MainPresenterTest {
    private lateinit var mainPresenter: MainPresenter

    @Mock
    private lateinit var cardSource: CardSource

    @Mock
    private lateinit var viewContract: NotesViewContract

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainPresenter = MainPresenter(viewContract, cardSource)
    }

    @Test
    fun getDataFromSource_Test() {
        mainPresenter.getDataFromSource()
        verify(cardSource).getCards(mainPresenter.getCardsSourceResponse())
    }
}
