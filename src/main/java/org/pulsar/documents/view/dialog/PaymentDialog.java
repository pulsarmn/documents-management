package org.pulsar.documents.view.dialog;

import javafx.collections.ObservableList;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.pulsar.documents.model.Document;


public class PaymentDialog extends Stage {

    private final ObservableList<Document> documents;

    public PaymentDialog(ObservableList<Document> documents) {
        this.documents = documents;
        initModality(Modality.APPLICATION_MODAL);
        setTitle("Создание платёжки");
    }
}
