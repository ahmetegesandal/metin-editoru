package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        checkAndShowRichTextFXStatus();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/main.css")).toExternalForm());

        HelloController controller = fxmlLoader.getController();
        controller.setStage(stage);

        double desiredWidth = 1366;
        double desiredHeight = 768;
        stage.setWidth(desiredWidth);
        stage.setHeight(desiredHeight);
        stage.setTitle("Notepad--");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/icon.jpg"))));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void checkAndShowRichTextFXStatus() {
        boolean isRichTextFXInstalled = checkRichTextFX();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (isRichTextFXInstalled) {
            alert.setTitle("RichTextFX Durumu");
            alert.setHeaderText("RichTextFX Bulundu!");
            alert.setContentText("RichTextFX kütüphanesi düzgün bir şekilde yüklendi ve kullanıma hazır.");
        } else {
            alert.setTitle("RichTextFX Durumu");
            alert.setHeaderText("RichTextFX Eksik!");
            alert.setContentText("RichTextFX kütüphanesi yüklü değil. Lütfen bağımlılıkları kontrol edin.");
        }
        alert.showAndWait();
    }

    private boolean checkRichTextFX() {
        try {
            Class.forName("org.fxmisc.richtext.CodeArea");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
