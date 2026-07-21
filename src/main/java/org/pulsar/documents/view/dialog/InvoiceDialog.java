package org.pulsar.documents.view.dialog;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.pulsar.documents.model.Currency;
import org.pulsar.documents.model.Document;
import org.pulsar.documents.model.Invoice;
import org.pulsar.documents.util.StyleUtils;
import org.pulsar.documents.util.ValidationUtils;

import java.math.BigDecimal;
import java.time.LocalDate;


public class InvoiceDialog extends AbstractDocumentDialog<Invoice> {

    private ComboBox<Currency> currencyComboBox;
    private TextField currencyRateField;
    private TextField productField;
    private TextField countField;

    private static final String DEFAULT_TITLE = "Создание накладной";

    public InvoiceDialog(ObservableList<Document> documents) {
        super(documents, DEFAULT_TITLE, 400, 600);
    }

    @Override
    protected void addCustomFields() {
        addCurrencyField();
        addCurrencyRateField();
        addProductField();
        addCountField();
    }

    private void addCurrencyField() {
        currencyComboBox = new ComboBox<>(getCurrencies());
        if (Currency.values().length != 0) {
            currencyComboBox.setValue(Currency.RUB);
        }
        addRow("Валюта:", currencyComboBox);
    }

    private ObservableList<Currency> getCurrencies() {
        return FXCollections.observableArrayList(Currency.values());
    }

    private void addCurrencyRateField() {
        currencyRateField = createTextField("0");
        currencyRateField.setOnKeyTyped(_ -> validatePositiveDecimal(currencyRateField));
        addRow("Курс валюты:", currencyRateField);
    }

    private void addProductField() {
        productField = createTextField("Товар");
        addRow("Товар:", productField);
    }

    private void addCountField() {
        countField = createTextField("0");
        countField.setOnKeyTyped(_ -> validateCount());
        addRow("Количество:", countField);
    }

    private void validateCount() {
        String value = countField.getText();
        if (ValidationUtils.canBeInteger(value) && Integer.parseInt(value) > 0) {
            StyleUtils.applyValidStyle(countField);
        } else {
            StyleUtils.applyInvalidStyle(countField);
        }
    }

    @Override
    protected Invoice buildDocument() {
        String number = numberField.getText();
        LocalDate date = datePicker.getValue();
        String user = userField.getText();
        String sum = sumField.getText();
        Currency currency = currencyComboBox.getValue();
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
