package org.pulsar.documents;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.pulsar.documents.view.MainWindow;


public class DocumentsManagementApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        MainWindow mainWindow = new MainWindow();
        Scene scene = new Scene(mainWindow, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Тест");
        stage.show();
    }
}
