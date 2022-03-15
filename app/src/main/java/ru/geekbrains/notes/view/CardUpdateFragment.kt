package ru.geekbrains.notes.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import ru.geekbrains.notes.R
import ru.geekbrains.notes.data.CardData
import ru.geekbrains.notes.presenter.MainPresenter
import java.util.*

class CardUpdateFragment(private val presenter: MainPresenter) : Fragment() {
    private var cardData = CardData()
    private lateinit var title: TextInputEditText
    private lateinit var description: TextInputEditText
    private lateinit var datePicker: DatePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            requireArguments().getParcelable<CardData>(ARG_CARD_DATA)?.let { cardData = it }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update_card, container, false)
        initView(view)
        populateView()
        return view
    }

    private fun initView(view: View) {
        title = view.findViewById(R.id.inputTitle)
        description = view.findViewById(R.id.inputDescription)
        datePicker = view.findViewById(R.id.inputDate)
    }

    private fun populateView() {
        title.setText(cardData.title)
        description.setText(cardData.text)
        initDatePicker(cardData.date)
    }

    private fun initDatePicker(date: Date) {
        val calendar = Calendar.getInstance()
        calendar.time = date
        datePicker.init(
            calendar[Calendar.YEAR],
            calendar[Calendar.MONTH],
            calendar[Calendar.DAY_OF_MONTH],
            null
        )
    }

    private fun collectCardData(): CardData {
        val title = title.text.toString()
        val description = description.text.toString()
        val date = dateFromDatePicker
        cardData.title = title
        cardData.text = description
        cardData.date = date
        return cardData
    }

    private val dateFromDatePicker: Date
        get() {
            val cal = Calendar.getInstance()
            cal[Calendar.YEAR] = datePicker.year
            cal[Calendar.MONTH] = datePicker.month
            cal[Calendar.DAY_OF_MONTH] = datePicker.dayOfMonth
            return cal.time
        }

    override fun onStop() {
        super.onStop()
        cardData = collectCardData()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.notify(cardData)
    }

    companion object {
        private const val ARG_CARD_DATA = "Param_CardData"
        fun newInstance(cardData: CardData, presenter: MainPresenter): CardUpdateFragment {
            val fragment = CardUpdateFragment(presenter)
            val args = Bundle()
            args.putParcelable(ARG_CARD_DATA, cardData)
            fragment.arguments = args
            return fragment
        }

        fun newInstance(presenter: MainPresenter): CardUpdateFragment {
            return CardUpdateFragment(presenter)
        }
    }
}
