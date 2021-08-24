package ru.geekbrains.notes.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

import ru.geekbrains.notes.MainActivity;
import ru.geekbrains.notes.R;
import ru.geekbrains.notes.data.CardData;
import ru.geekbrains.notes.observation.Publisher;

public class CardUpdateFragment extends Fragment {

    private static final String ARG_CARD_DATA = "Param_CardData";
    private CardData cardData;
    private Publisher publisher;
    private TextInputEditText title;
    private TextInputEditText description;
    private DatePicker datePicker;

    public static CardUpdateFragment newInstance(CardData cardData) {
        CardUpdateFragment fragment = new CardUpdateFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CARD_DATA, cardData);
        fragment.setArguments(args);
        return fragment;
    }

    public static CardUpdateFragment newInstance() {
        CardUpdateFragment fragment = new CardUpdateFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            this.cardData = getArguments().getParcelable(ARG_CARD_DATA);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.publisher = ((MainActivity) context).getPublisher();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.publisher = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_card, container, false);

        initView(view);

        if (this.cardData != null)
            populateView();
        return view;
    }

    private void initView(View view) {
        this.title = view.findViewById(R.id.inputTitle);
        this.description = view.findViewById(R.id.inputDescription);
        this.datePicker = view.findViewById(R.id.inputDate);
    }

    private void populateView() {
            title.setText(cardData.getTitle());
            description.setText(cardData.getText());
            initDatePicker(cardData.getDate());
    }

    private void initDatePicker(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null);
    }

    private CardData collectCardData(){
        String title = this.title.getText().toString();
        String description = this.description.getText().toString();
        Date date = getDateFromDatePicker();
        if(cardData != null) {
            cardData.setTitle(title);
            cardData.setText(description);
            cardData.setDate(date);
            return cardData;
        }
        return new CardData(title, description, date);
    }

    private Date getDateFromDatePicker() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, this.datePicker.getYear());
        cal.set(Calendar.MONTH, this.datePicker.getMonth());
        cal.set(Calendar.DAY_OF_MONTH, this.datePicker.getDayOfMonth());
        return cal.getTime();
    }

    @Override
    public void onStop() {
        super.onStop();
        cardData = collectCardData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        publisher.notifyTask(cardData);
    }
}
