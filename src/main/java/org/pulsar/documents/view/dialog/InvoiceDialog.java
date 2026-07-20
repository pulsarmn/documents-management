package org.pulsar.documents.view.dialog;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.pulsar.documents.model.Currency;
import org.pulsar.documents.model.Document;
import org.pulsar.documents.model.Invoice;
import org.pulsar.documents.util.DialogUtils;
import org.pulsar.documents.util.ValidationUtils;

import java.math.BigDecimal;
import java.time.LocalDate;


public class InvoiceDialog extends Stage {

    private final ObservableList<Document> documents;

    private TextField numberField;
    private DatePicker datePicker;
    private TextField userField;
    private TextField sumField;
    private ComboBox<Currency> currencyField;
    private TextField currencyRateField;
    private TextField productField;
    private TextField countField;

    public InvoiceDialog(ObservableList<Document> documents) {
        this.documents = documents;
        initModality(Modality.APPLICATION_MODAL);
        setTitle("Создание накладной");

        GridPane gridPane = createDialogContent();
        setScene(new Scene(gridPane, 400, 600));
    }

    private GridPane createDialogContent() {
        GridPane gridPane = new GridPane(10, 15);
        gridPane.setPadding(new Insets(10));
        gridPane.setAlignment(Pos.TOP_CENTER);

        addNumberField(gridPane);
        addDatePicker(gridPane);
        addUserField(gridPane);
        addSumField(gridPane);
        addCurrencyField(gridPane);
        addCurrencyRateField(gridPane);
        addProductField(gridPane);
        addCountField(gridPane);
        addSaveButton(gridPane);

        return gridPane;
    }

    private void addNumberField(GridPane gridPane) {
        Label numberLabel = new Label("Номер:");
        numberField = new TextField();
        numberField.setPromptText("123");

        gridPane.add(numberLabel, 0, 0);
        gridPane.add(numberField, 1, 0);
    }

    private void addDatePicker(GridPane gridPane) {
        Label dateLabel = new Label("Дата:");
        datePicker = new DatePicker();

        gridPane.add(dateLabel, 0, 1);
        gridPane.add(datePicker, 1, 1);
    }

    private void addUserField(GridPane gridPane) {
        Label userLabel = new Label("Пользователь:");
        userField = new TextField();
        userField.setPromptText("Александр");

        gridPane.add(userLabel, 0, 2);
        gridPane.add(userField, 1, 2);
    }

    private void addSumField(GridPane gridPane) {
        Label sumLabel = new Label("Сумма:");
        sumField = new TextField();
        sumField.setPromptText("0");

        gridPane.add(sumLabel, 0, 3);
        gridPane.add(sumField, 1, 3);
    }

    private void addCurrencyField(GridPane gridPane) {
        Label currencyLabel = new Label("Валюта:");
        currencyField = new ComboBox<>();
        currencyField.setItems(getCurrencies());

        if (Currency.values().length != 0) {
            currencyField.setValue(Currency.RUB);
        }

        gridPane.add(currencyLabel, 0, 4);
        gridPane.add(currencyField, 1, 4);
    }

    private ObservableList<Currency> getCurrencies() {
        return FXCollections.observableArrayList(Currency.values());
    }

    private void addCurrencyRateField(GridPane gridPane) {
        Label currencyRateLabel = new Label("Курс валюты:");
        currencyRateField = new TextField();
        currencyRateField.setPromptText("0");

        gridPane.add(currencyRateLabel, 0, 5);
        gridPane.add(currencyRateField, 1, 5);
    }

    private void addProductField(GridPane gridPane) {
        Label productLabel = new Label("Товар:");
        productField = new TextField();
        productField.setPromptText("Товар");

        gridPane.add(productLabel, 0, 6);
        gridPane.add(productField, 1, 6);
    }

    private void addCountField(GridPane gridPane) {
        Label countLabel = new Label("Количество:");
        countField = new TextField();
        countField.setPromptText("0");

        gridPane.add(countLabel, 0, 7);
        gridPane.add(countField, 1, 7);
    }

    private void addSaveButton(GridPane gridPane) {
        Button saveButton = new Button("OK");
        gridPane.add(saveButton, 1, 8);

        saveButton.setOnAction(_ -> trySaveInvoice());
    }

    private void trySaveInvoice() {
        Invoice invoice = buildInvoice();
        if (invoice == null) {
            DialogUtils.showError(this, "Не все поля заполнены корректно!");
        } else {
            documents.add(invoice);
            this.close();
        }
    }

    private Invoice buildInvoice() {
        String number = numberField.getText();
        LocalDate date = datePicker.getValue();
        String user = userField.getText();
        String sum = sumField.getText();
        Currency currency = currencyField.getValue();
        String currencyRate = currencyRateField.getText();
        String product = productField.getText();
        String count = countField.getText();

        if (ValidationUtils.hasAnyNullOrBlank(number, date, user, sum, currency, currencyRate, product, count)) {
            return null;
        }

        try {
            BigDecimal decimalSum = new BigDecimal(sum);
            BigDecimal decimalCurrencyRate = new BigDecimal(currencyRate);
            int numCount = Integer.parseInt(count);

            if (decimalSum.compareTo(BigDecimal.ZERO) <= 0 ||
                    decimalCurrencyRate.compareTo(BigDecimal.ZERO) <= 0 ||
                    numCount <= 0) {
                return null;
            }

            return new Invoice(number, date, user, decimalSum, currency, decimalCurrencyRate, product, numCount);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
