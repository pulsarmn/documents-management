package org.pulsar.documents.view.dialog;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.pulsar.documents.model.Currency;
import org.pulsar.documents.model.Document;
import org.pulsar.documents.model.PaymentRequest;
import org.pulsar.documents.util.ValidationUtils;

import java.math.BigDecimal;
import java.time.LocalDate;


public class PaymentRequestDialog extends AbstractDocumentDialog<PaymentRequest> {

    private TextField counterpartyField;
    private ComboBox<Currency> currencyComboBox;
    private TextField currencyRateField;
    private TextField commissionField;

    private static final String DEFAULT_TITLE = "Создание заявки на оплату";

    public PaymentRequestDialog(ObservableList<Document> documents) {
        super(documents, DEFAULT_TITLE, 400, 600);
    }

    @Override
    protected void addCustomFields() {
        addCounterpartyField();
        addCurrencyField();
        addCurrencyRateField();
        addCommissionField();
    }

    private void addCounterpartyField() {
        counterpartyField = createTextField("Контрагент");
        addRow("Контрагент:", counterpartyField);
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

    private void addCommissionField() {
        commissionField = createTextField("0");
        commissionField.setOnKeyTyped(_ -> validatePositiveDecimal(commissionField));
        addRow("Комиссия:", commissionField);
    }

    @Override
    protected PaymentRequest buildDocument() {
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
}
