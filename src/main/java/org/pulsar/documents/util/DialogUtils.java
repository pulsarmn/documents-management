package org.pulsar.documents.util;

import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Window;


public abstract class DialogUtils { // Подсмотрел фишку с util классами в Spring :)

    private static final String INFO_TITLE = "Информация";
    private static final String WARNING_TITLE = "Предупреждение";
    private static final String ERROR_TITLE = "Ошибка";

    public static void showInfo(Window owner, String message) {
        showAlert(Alert.AlertType.INFORMATION, owner, INFO_TITLE, message);
    }

    public static void showWarning(Window owner, String message) {
        showAlert(Alert.AlertType.WARNING, owner, WARNING_TITLE, message);
    }

    public static void showError(Window owner, String message) {
        showAlert(Alert.AlertType.ERROR, owner, ERROR_TITLE, message);
    }

    private static void showAlert(Alert.AlertType type, Window owner, String title, String message) {
        Alert alert = new Alert(type);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
