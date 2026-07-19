package org.pulsar.documents.view.dialog;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.pulsar.documents.model.Document;
import org.pulsar.documents.model.Payment;
import org.pulsar.documents.util.DialogUtils;
import org.pulsar.documents.util.StyleUtils;

import java.math.BigDecimal;
import java.time.LocalDate;


public class PaymentDialog extends Stage {

    private final ObservableList<Document> documents;

    private TextField numberField;
    private DatePicker datePicker;
    private TextField userField;
    private TextField sumField;
    private TextField employeeField;

    public PaymentDialog(ObservableList<Document> documents) {
        this.documents = documents;
        initModality(Modality.APPLICATION_MODAL);
        setTitle("Создание платёжки");

        GridPane gridPane = getDialogContent();
        Scene scene = new Scene(gridPane, 350, 500);
        setScene(scene);
    }

    private GridPane getDialogContent() {
        GridPane gridPane = new GridPane(10, 15);
        gridPane.setPadding(new Insets(10));
        gridPane.setAlignment(Pos.TOP_CENTER);

        addNumberField(gridPane);
        addDateField(gridPane);
        addUserField(gridPane);
        addSumField(gridPane);
        addEmployeeField(gridPane);
        addSaveButton(gridPane);

        return gridPane;
    }

    private void addNumberField(GridPane gridPane) {
        Label numberLabel = new Label("Номер:");
        numberField = new TextField();
        StyleUtils.applyNeutral(numberField);
        numberField.setPromptText("123");

        gridPane.add(numberLabel, 0, 0);
        gridPane.add(numberField, 1, 0);
    }

    private void addDateField(GridPane gridPane) {
        Label dateLabel = new Label("Дата:");
        datePicker = new DatePicker();
        datePicker.setPromptText("7/19/2026");

        gridPane.add(dateLabel, 0, 1);
        gridPane.add(datePicker, 1, 1);
    }

    private void addUserField(GridPane gridPane) {
        Label userLabel = new Label("Пользователь:");
        userField = new TextField();
        StyleUtils.applyNeutral(userField);
        userField.setPromptText("Александр");

        gridPane.add(userLabel, 0, 2);
        gridPane.add(userField, 1, 2);
    }

    private void addSumField(GridPane gridPane) {
        Label sumLabel = new Label("Сумма:");
        sumField = new TextField();
        StyleUtils.applyNeutral(sumField);
        sumField.setOnKeyTyped(event -> validateSum());
        sumField.setPromptText("0");

        gridPane.add(sumLabel, 0, 3);
        gridPane.add(sumField, 1, 3);
    }

    private void validateSum() {
        try {
            new BigDecimal(sumField.getText());
            StyleUtils.applyValidStyle(sumField);
        } catch (NumberFormatException e) {
            StyleUtils.applyInvalidStyle(sumField);
        }
    }

    private void addEmployeeField(GridPane gridPane) {
        Label employeeLabel = new Label("Сотрудник:");
        employeeField = new TextField();
        StyleUtils.applyNeutral(employeeField);
        employeeField.setPromptText("Александр");

        gridPane.add(employeeLabel, 0, 4);
        gridPane.add(employeeField, 1, 4);
    }

    private void addSaveButton(GridPane gridPane) {
        Button okButton = new Button("OK");
        gridPane.add(okButton, 1, 5);

        okButton.setOnAction(_ -> trySavePayment());
    }

    private void trySavePayment() {
        Payment payment = buildPayment();
        if (payment == null) {
            DialogUtils.showError(this, "Не все поля заполнены корректно!");
        } else {
            documents.add(payment);
            this.close();
        }
    }

    private Payment buildPayment() {
        String number = numberField.getText();
        LocalDate date = datePicker.getValue();
        String user = userField.getText();
        String sum = sumField.getText();
        String employee = employeeField.getText();

        if (number.isBlank() || date == null || user.isBlank() || sum.isBlank() || employee.isBlank()) {
            return null;
        }

        BigDecimal decimalSum;
        try {
            decimalSum = new BigDecimal(sum);
        } catch (NumberFormatException e) {
            return null;
        }
        return new Payment(number, date, user, decimalSum, employee);
    }
}
