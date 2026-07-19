package org.pulsar.documents.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.pulsar.documents.model.Document;
import org.pulsar.documents.view.dialog.PaymentDialog;

import java.util.List;


public class MainWindow extends BorderPane {

    private final ObservableList<Document> documents = FXCollections.observableArrayList();
    private final ListView<Document> listView = new ListView<>(documents);

    public MainWindow() {
        super.setCenter(listView);
        super.setRight(createRightPanel());
    }

    private VBox createRightPanel() {
        VBox vBox = new VBox();

        vBox.getChildren().addAll(createRightPanelButtons());
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.getChildren().forEach(child -> {
            if (child instanceof Button button) {
                button.setMaxWidth(Double.MAX_VALUE);
            }
        });
        vBox.setSpacing(10);

        return vBox;
    }

    private List<Button> createRightPanelButtons() {
        Button invoiceBtn = new Button("Накладная");
        Button paymentBtn = new Button("Платёжка");
        Button requestBtn = new Button("Заявка на оплату");
        Button saveBtn = new Button("Сохранить");
        Button loadBtn = new Button("Загрузить");
        Button viewBtn = new Button("Просмотр");
        Button exitBtn = new Button("Выход");

        exitBtn.setOnAction(event -> {
            Platform.exit();
        });

        paymentBtn.setOnAction(event -> {
            PaymentDialog dialog = new PaymentDialog(documents);
            dialog.showAndWait();
        });

        return List.of(invoiceBtn, paymentBtn, requestBtn, saveBtn, loadBtn, viewBtn, exitBtn);
    }
}
