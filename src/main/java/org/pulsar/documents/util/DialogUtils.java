package org.pulsar.documents.util;

import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Window;


public abstract class DialogUtils {

    public static void showError(Window owner, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(owner);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
