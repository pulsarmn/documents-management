package org.pulsar.documents.view.dialog;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.pulsar.documents.model.Document;


public class PaymentRequestDialog extends Stage {

    private final ObservableList<Document> documents;

    public PaymentRequestDialog(ObservableList<Document> documents) {
        this.documents = documents;
        initModality(Modality.APPLICATION_MODAL);
        setTitle("Создание заявки на оплату");

        GridPane gridPane = createDialogContent();
        setScene(new Scene(gridPane, 400, 600));
    }

    private GridPane createDialogContent() {
        GridPane gridPane = new GridPane(10, 15);
        gridPane.setPadding(new Insets(10));
        gridPane.setAlignment(Pos.TOP_CENTER);
        return gridPane;
    }
}
