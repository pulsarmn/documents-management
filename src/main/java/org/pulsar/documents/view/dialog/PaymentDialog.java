package org.pulsar.documents.view.dialog;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.pulsar.documents.model.Document;


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

    private void addEmployeeField(GridPane gridPane) {
        Label employeeLabel = new Label("Сотрудник:");
        employeeField = new TextField();
        employeeField.setPromptText("Александр");

        gridPane.add(employeeLabel, 0, 4);
        gridPane.add(employeeField, 1, 4);
    }

    private void addSaveButton(GridPane gridPane) {
        Button submitButton = new Button("OK");
        gridPane.add(submitButton, 1, 5);
    }
}
