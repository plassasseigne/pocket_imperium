package fr.lasschko.pocketimperium.pocketimperium;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view/main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style/font.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style/button.css")).toExternalForm());

        stage.setTitle("Pocket Imperium");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}