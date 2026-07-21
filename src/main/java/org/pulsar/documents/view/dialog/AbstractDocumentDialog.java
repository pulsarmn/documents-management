package org.pulsar.documents.view.dialog;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.pulsar.documents.model.Document;
import org.pulsar.documents.util.DialogUtils;
import org.pulsar.documents.util.StyleUtils;
import org.pulsar.documents.util.ValidationUtils;

import java.math.BigDecimal;


public abstract class AbstractDocumentDialog<T extends Document> extends Stage {

    protected TextField numberField;
    protected DatePicker datePicker;
    protected TextField userField;
    protected TextField sumField;

    private int currentRow = 0;

    protected final ObservableList<Document> documents;
    private final GridPane gridPane = new GridPane(10, 15);

    public AbstractDocumentDialog(ObservableList<Document> documents, String title, double width, double height) {
        this.documents = documents;
        initModality(Modality.APPLICATION_MODAL);
        setTitle(title);

        setUpUI();
        setScene(new Scene(gridPane, width, height));
    }

    private void setUpUI() {
        gridPane.setPadding(new Insets(10));
        gridPane.setAlignment(Pos.TOP_CENTER);

        addBaseFields();
        addCustomFields();
        addSaveButton();
    }

    private void addBaseFields() {
        addNumberField();
        addDateField();
        addUserField();
        addSumField();
    }

    private void addNumberField() {
        numberField = createTextField("K1");
        addRow("Номер:", numberField);
    }

    private void addDateField() {
        datePicker = new DatePicker();
        addRow("Дата:", datePicker);
    }

    private void addUserField() {
        userField = createTextField("Александр");
        addRow("Пользователь:", userField);
    }

    private void addSumField() {
        sumField = createTextField("123");
        sumField.setOnKeyTyped(_ -> validatePositiveDecimal(sumField));
        addRow("Сумма:", sumField);
    }

    protected abstract void addCustomFields();

    private void addSaveButton() {
        Button saveButton = new Button("OK");
        gridPane.add(saveButton, 1, currentRow);

        saveButton.setOnAction(_ -> trySave());
    }

    private void trySave() {
        T document = buildDocument();
        if (document == null) {
            DialogUtils.showError(this, "Не все поля заполнены корректно!");
        } else {
            documents.add(document);
            this.close();
        }
    }

    protected abstract T buildDocument();

    protected TextField createTextField(String promptText) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        StyleUtils.applyNeutral(textField);
        return textField;
    }

    protected void addRow(String labelText, Node node) {
        gridPane.add(new Label(labelText), 0, currentRow);
        gridPane.add(node, 1, currentRow);
        currentRow++;
    }

    protected void validatePositiveDecimal(TextField textField) {
        String value = textField.getText();
        if (ValidationUtils.canBeDecimal(value) && new BigDecimal(value).compareTo(BigDecimal.ZERO) > 0) {
            StyleUtils.applyValidStyle(textField);
        } else {
            StyleUtils.applyInvalidStyle(textField);
        }
    }
}
