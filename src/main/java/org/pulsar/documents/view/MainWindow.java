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
import org.pulsar.documents.view.dialog.InvoiceDialog;
import org.pulsar.documents.view.dialog.PaymentDialog;
import org.pulsar.documents.view.dialog.PaymentRequestDialog;

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
        Button invoiceBtn = createInvoiceButton();
        Button paymentBtn = createPaymentButton();
        Button requestBtn = createPaymentRequestButton();
        Button saveBtn = createSaveButton();
        Button loadBtn = createLoadButton();
        Button viewBtn = createViewButton();
        Button exitBtn = createExitButton();

        return List.of(invoiceBtn, paymentBtn, requestBtn, saveBtn, loadBtn, viewBtn, exitBtn);
    }

    private Button createInvoiceButton() {
        Button invoiceBtn = new Button("Накладная");
        invoiceBtn.setOnAction(event -> {
            InvoiceDialog invoiceDialog = new InvoiceDialog(documents);
            invoiceDialog.showAndWait();
        });
        return invoiceBtn;
    }

    private Button createPaymentButton() {
        Button paymentBtn = new Button("Платёжка");
        paymentBtn.setOnAction(event -> {
            PaymentDialog paymentDialog = new PaymentDialog(documents);
            paymentDialog.showAndWait();
        });
        return paymentBtn;
    }

    private Button createPaymentRequestButton() {
        Button requestBtn = new Button("Заявка на оплату");
        requestBtn.setOnAction(event -> {
            PaymentRequestDialog paymentRequestDialog = new PaymentRequestDialog(documents);
            paymentRequestDialog.showAndWait();
        });
        return requestBtn;
    }

    private Button createSaveButton() {
        Button saveBtn = new Button("Сохранить");
        saveBtn.setOnAction(event -> {

        });
        return saveBtn;
    }

    private Button createLoadButton() {
        Button loadBtn = new Button("Загрузить");
        loadBtn.setOnAction(event -> {

        });
        return loadBtn;
    }

    private Button createViewButton() {
        Button viewBtn = new Button("Просмотр");
        viewBtn.setOnAction(event -> {

        });
        return viewBtn;
    }

    private Button createExitButton() {
        Button exitBtn = new Button("Выход");
        exitBtn.setOnAction(_ -> Platform.exit());
        return exitBtn;
    }
}
