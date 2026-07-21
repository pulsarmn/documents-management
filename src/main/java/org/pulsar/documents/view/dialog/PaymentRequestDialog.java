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
import org.pulsar.documents.model.PaymentRequest;
import org.pulsar.documents.util.DialogUtils;
import org.pulsar.documents.util.StyleUtils;
import org.pulsar.documents.util.ValidationUtils;

import java.math.BigDecimal;
import java.time.LocalDate;


public class PaymentRequestDialog extends Stage {

    private final ObservableList<Document> documents;

    private TextField numberField;
    private DatePicker datePicker;
    private TextField userField;
    private TextField sumField;
    private TextField counterpartyField;
    private ComboBox<Currency> currencyComboBox;
    private TextField currencyRateField;
    private TextField commissionField;

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

        addNumberField(gridPane);
        addDatePicker(gridPane);
        addUserField(gridPane);
        addSumField(gridPane);
        addCounterpartyField(gridPane);
        addCurrencyField(gridPane);
        addCurrencyRateField(gridPane);
        addCommissionField(gridPane);
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

    private void addDatePicker(GridPane gridPane) {
        Label dateLabel = new Label("Дата:");
        datePicker = new DatePicker();

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
        sumField.setPromptText("0");
        sumField.setOnKeyTyped(_ -> validateSum());

        gridPane.add(sumLabel, 0, 3);
        gridPane.add(sumField, 1, 3);
    }

    private void validateSum() {
        validatePositiveDecimal(sumField);
    }

    private void addCounterpartyField(GridPane gridPane) {
        Label counterpartyLabel = new Label("Контрагент:");
        counterpartyField = new TextField();
        StyleUtils.applyNeutral(counterpartyField);
        counterpartyField.setPromptText("Контрагент");

        gridPane.add(counterpartyLabel, 0, 4);
        gridPane.add(counterpartyField, 1, 4);
    }

    private void addCurrencyField(GridPane gridPane) {
        Label currencyLabel = new Label("Валюта:");
        currencyComboBox = new ComboBox<>();
        currencyComboBox.setItems(getCurrencies());

        if (Currency.values().length != 0) {
            currencyComboBox.setValue(Currency.RUB);
        }

        gridPane.add(currencyLabel, 0, 5);
        gridPane.add(currencyComboBox, 1, 5);
    }

    private ObservableList<Currency> getCurrencies() {
        return FXCollections.observableArrayList(Currency.values());
    }

    private void addCurrencyRateField(GridPane gridPane) {
        Label currencyRateLabel = new Label("Курс валюты:");
        currencyRateField = new TextField();
        StyleUtils.applyNeutral(currencyRateField);
        currencyRateField.setPromptText("0");
        currencyRateField.setOnKeyTyped(_ -> validateCurrencyRate());

        gridPane.add(currencyRateLabel, 0, 6);
        gridPane.add(currencyRateField, 1, 6);
    }

    private void validateCurrencyRate() {
        validatePositiveDecimal(currencyRateField);
    }

    private void addCommissionField(GridPane gridPane) {
        Label commissionLabel = new Label("Комиссия:");
        commissionField = new TextField();
        StyleUtils.applyNeutral(commissionField);
        commissionField.setPromptText("10");
        commissionField.setOnKeyTyped(_ -> validateCommission());

        gridPane.add(commissionLabel, 0, 7);
        gridPane.add(commissionField, 1, 7);
    }

    private void validateCommission() {
        validatePositiveDecimal(commissionField);
    }

    private void addSaveButton(GridPane gridPane) {
        Button saveButton = new Button("OK");
        gridPane.add(saveButton, 1, 8);

        saveButton.setOnAction(_ -> trySavePaymentRequest());
    }

    private void trySavePaymentRequest() {
        PaymentRequest paymentRequest = buildPaymentRequest();
        if (paymentRequest == null) {
            DialogUtils.showError(this, "Не все поля заполнены корректно!");
        } else {
            documents.add(paymentRequest);
            this.close();
        }
    }

    private PaymentRequest buildPaymentRequest() {
        String number = numberField.getText();
        LocalDate date = datePicker.getValue();
        String user = userField.getText();
        String sum = sumField.getText();
        String counterparty = counterpartyField.getText();
        Currency currency = currencyComboBox.getValue();
        String currencyRate = currencyRateField.getText();
        String commission = commissionField.getText();

        if (ValidationUtils.hasAnyNullOrBlank(number, date, user, sum, counterparty, currency, currencyRate, commission)) {
            return null;
        }

        try {
            BigDecimal decimalSum = new BigDecimal(sum);
            BigDecimal decimalCurrencyRate = new BigDecimal(currencyRate);
            BigDecimal decimalCommission = new BigDecimal(commission);

            if (decimalSum.compareTo(BigDecimal.ZERO) <= 0 ||
                    decimalCurrencyRate.compareTo(BigDecimal.ZERO) <= 0 ||
                    decimalCommission.compareTo(BigDecimal.ZERO) <= 0) {
                return null;
            }
            return new PaymentRequest(number, date, user, decimalSum, counterparty, currency, decimalCurrencyRate, decimalCommission);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void validatePositiveDecimal(TextField textField) {
        String value = textField.getText();
        if (ValidationUtils.canBeDecimal(value) && new BigDecimal(value).compareTo(BigDecimal.ZERO) > 0) {
            StyleUtils.applyValidStyle(textField);
        } else {
            StyleUtils.applyInvalidStyle(textField);
        }
    }
}
