package org.pulsar.documents.view.dialog;

import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import org.pulsar.documents.model.Document;
import org.pulsar.documents.model.Payment;
import org.pulsar.documents.util.ValidationUtils;

import java.math.BigDecimal;
import java.time.LocalDate;


public class PaymentDialog extends AbstractDocumentDialog<Payment> {

    private TextField employeeField;

    private static final String DEFAULT_TITLE = "Создание платежа";

    public PaymentDialog(ObservableList<Document> documents) {
        super(documents, DEFAULT_TITLE, 350, 500);
    }

    @Override
    protected void addCustomFields() {
        employeeField = createTextField("Александр");
        addRow("Сотрудник:", employeeField);
    }

    @Override
    protected Payment buildDocument() {
        String number = numberField.getText();
        LocalDate date = datePicker.getValue();
        String user = userField.getText();
        String sum = sumField.getText();
        String employee = employeeField.getText();

        if (ValidationUtils.hasAnyNullOrBlank(number, date, user, sum, employee)) {
            return null;
        }

        try {
            BigDecimal decimalSum = new BigDecimal(sum);
            return new Payment(number, date, user, decimalSum, employee);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
