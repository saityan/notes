package ru.geekbrains.notes.data;

import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CardDataTranslate {
    public static class Fields {
        public final static String TITLE = "title";
        public final static String DESCRIPTION = "description";
        public final static String DATE = "date";
    }

    public static CardData documentToCardData(String id, Map<String, Object> doc) {
        CardData result = new CardData(
                (String)doc.get(Fields.TITLE),
                (String)doc.get(Fields.DESCRIPTION),
                ((Timestamp) Objects.requireNonNull(doc.get(Fields.DATE))).toDate());

        result.setId(id);

        return result;
    }

    public static Map<String, Object> cardDataToDocument(CardData cardData) {
        Map<String, Object> result = new HashMap<>();
        result.put(Fields.TITLE, cardData.getTitle());
        result.put(Fields.DESCRIPTION, cardData.getText());
        result.put(Fields.DATE, cardData.getDate());
        return result;
    }
}
