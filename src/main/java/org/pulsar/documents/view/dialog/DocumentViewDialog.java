package org.pulsar.documents.view.dialog;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.pulsar.documents.model.Document;
import org.pulsar.documents.model.Invoice;
import org.pulsar.documents.model.Payment;
import org.pulsar.documents.model.PaymentRequest;


public class DocumentViewDialog extends Stage {

    public DocumentViewDialog(Document document) {
        setTitle("Просмотр документа: " + document.getNumber());
        initModality(Modality.APPLICATION_MODAL);

        VBox root = new VBox(15);
        root.setPadding(new Insets(15));

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        int row = 0;

        addRow(grid, row++, "Тип документа:", getDocumentTypeName(document));
        addRow(grid, row++, "Номер:", document.getNumber());
        addRow(grid, row++, "Дата:", document.getDate() != null ? document.getDate().toString() : "");
        addRow(grid, row++, "Пользователь:", document.getUser());
        addRow(grid, row++, "Сумма:", String.valueOf(document.getSum()));

        switch (document) {
            case Payment payment -> addRow(grid, row++, "Сотрудник:", payment.getEmployee());
            case Invoice invoice -> {
                addRow(grid, row++, "Валюта:", invoice.getCurrency().getTitle());
                addRow(grid, row++, "Курс валюты:", invoice.getCurrencyRate().toString());
                addRow(grid, row++, "Товар:", invoice.getProduct());
                addRow(grid, row++, "Количество:", String.valueOf(invoice.getCount()));
            }
            case PaymentRequest request -> {
                addRow(grid, row++, "Контрагент:", request.getCounterparty());
                addRow(grid, row++, "Валюта:", request.getCurrency().getTitle());
                addRow(grid, row++, "Курс валюты:", request.getCurrencyRate().toString());
                addRow(grid, row++, "Комиссия:", request.getCommission().toString());
            }
            case null, default -> {}
        }

        Button closeBtn = new Button("Закрыть");
        closeBtn.setOnAction(_ -> close());

        root.getChildren().addAll(grid, closeBtn);
        root.setAlignment(Pos.CENTER);

        setScene(new Scene(root, 350, 300));
    }

    private void addRow(GridPane grid, int row, String labelText, String valueText) {
        Label label = new Label(labelText);
        label.setStyle("-fx-font-weight: bold;");
        Label value = new Label(valueText != null ? valueText : "-");

        grid.add(label, 0, row);
        grid.add(value, 1, row);
    }

    private String getDocumentTypeName(Document document) {
        return switch (document) {
            case Invoice _ -> "Накладная";
            case Payment _ -> "Платёжка";
            case PaymentRequest _ -> "Заявка на оплату";
            case null, default -> "Неизвестный документ";
        };
    }
}
