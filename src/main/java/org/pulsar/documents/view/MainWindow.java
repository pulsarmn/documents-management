package org.pulsar.documents.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.pulsar.documents.service.DocumentStorageService;
import org.pulsar.documents.model.Document;
import org.pulsar.documents.util.DialogUtils;
import org.pulsar.documents.view.dialog.InvoiceDialog;
import org.pulsar.documents.view.dialog.PaymentDialog;
import org.pulsar.documents.view.dialog.PaymentRequestDialog;

import java.io.File;
import java.util.List;


public class MainWindow extends BorderPane {

    private final ObservableList<Document> documents = FXCollections.observableArrayList();
    private final ListView<Document> listView = new ListView<>(documents);
    private final DocumentStorageService storageService = new DocumentStorageService();

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
        invoiceBtn.setOnAction(_ -> {
            InvoiceDialog invoiceDialog = new InvoiceDialog(documents);
            invoiceDialog.showAndWait();
        });
        return invoiceBtn;
    }

    private Button createPaymentButton() {
        Button paymentBtn = new Button("Платёжка");
        paymentBtn.setOnAction(_ -> {
            PaymentDialog paymentDialog = new PaymentDialog(documents);
            paymentDialog.showAndWait();
        });
        return paymentBtn;
    }

    private Button createPaymentRequestButton() {
        Button requestBtn = new Button("Заявка на оплату");
        requestBtn.setOnAction(_ -> {
            PaymentRequestDialog paymentRequestDialog = new PaymentRequestDialog(documents);
            paymentRequestDialog.showAndWait();
        });
        return requestBtn;
    }

    private Button createSaveButton() {
        Button saveBtn = new Button("Сохранить");
        saveBtn.setOnAction(_ -> saveDocuments());
        return saveBtn;
    }

    private void saveDocuments() {
        Window owner = getOwnerWindow();

        if (documents.isEmpty()) {
            DialogUtils.showWarning(owner, "Список документов пуст. Нечего сохранять!");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить документы");

        FileChooser.ExtensionFilter jsonFilter = new FileChooser.ExtensionFilter("JSON файлы (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(jsonFilter);

        File file = fileChooser.showSaveDialog(owner);
        if (file != null) {
            try {
                storageService.saveToFile(file, documents);
                DialogUtils.showInfo(owner, "Документы успешно сохранены в файл: " + file.getName());
            } catch (Exception e) {
                DialogUtils.showError(owner, "Ошибка при сохранении файла: " + e.getMessage());
            }
        }

    }

    private Button createLoadButton() {
        Button loadBtn = new Button("Загрузить");
        loadBtn.setOnAction(_ -> loadDocuments());
        return loadBtn;
    }

    private void loadDocuments() {
        Window owner = getOwnerWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выбрать файл");

        FileChooser.ExtensionFilter jsonFilter = new FileChooser.ExtensionFilter("JSON файлы (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(jsonFilter);

        File file = fileChooser.showOpenDialog(owner);
        if (file != null) {
            try {
                List<Document> loadedDocuments = storageService.loadFromFile(file);
                documents.clear();
                documents.addAll(loadedDocuments);
                DialogUtils.showInfo(owner, "Успешно загружено документов: " + loadedDocuments.size());
            } catch (Exception e) {
                DialogUtils.showError(owner, "Ошибка при загрузке файла: " + e.getMessage());
            }
        }
    }

    private Button createViewButton() {
        Button viewBtn = new Button("Просмотр");
        viewBtn.setOnAction(_ -> {

        });
        return viewBtn;
    }

    private Button createExitButton() {
        Button exitBtn = new Button("Выход");
        exitBtn.setOnAction(_ -> Platform.exit());
        return exitBtn;
    }

    private Window getOwnerWindow() {
        return getScene() == null ? null : getScene().getWindow();
    }
}
