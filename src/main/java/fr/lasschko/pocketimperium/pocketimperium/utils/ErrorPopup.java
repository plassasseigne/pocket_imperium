package fr.lasschko.pocketimperium.pocketimperium.utils;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ErrorPopup {
    @FXML
    private Button okButton;

    @FXML
    private Text errorText;

    public static void showError(String message) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ErrorPopup.class.getResource("/fr/lasschko/pocketimperium/pocketimperium/view/error-popup.fxml"));
            Parent ErrorPopupRoot = fxmlLoader.load();

            ErrorPopup controller = fxmlLoader.getController();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Error");

            controller.okButton.setOnAction(event -> stage.close());
            controller.errorText.setText(message);

            Scene scene = new Scene(ErrorPopupRoot);
            stage.setScene(scene);

            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
