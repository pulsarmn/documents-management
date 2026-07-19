package org.pulsar.documents.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import org.pulsar.documents.model.Document;


public class MainWindow extends BorderPane {

    private final ObservableList<Document> documents = FXCollections.observableArrayList();
    private final ListView<Document> listView = new ListView<>(documents);

    public MainWindow() {
        super.setCenter(listView);
    }
}
