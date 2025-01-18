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

/**
 * Classe utilitaire permettant d'afficher une popup d'erreur
 * avec un message personnalisé et un bouton "OK" pour fermer celle-ci.
 * <p>
 * Cette fenêtre est créée à partir du fichier FXML error-popup.fxml,
 * et son comportement est géré via les annotations {@code @FXML}.
 * Lors de l'appel à {@link #showError(String)}, une nouvelle fenêtre
 * JavaFX est affichée (via {@link Stage}) et empêche toute interaction
 * avec les autres fenêtres de l'application, grâce au mode modal.
 * </p>
 *
 * @author Paul et Yevhenii
 * @version 1.0
 */
public class ErrorPopup {
    /**
     * Bouton "OK" permettant de fermer la fenêtre d'erreur.
     */
    @FXML
    private Button okButton;

    /**
     * Zone de texte affichant le message d'erreur qu'on va utiliser pour
     * le customiser selon l'appel.
     */
    @FXML
    private Text errorText;

    /**
     * Affiche une fenêtre modale contenant le message d'erreur spécifié.
     * <p>
     * Cette méthode :
     * <ul>
     *   <li>Charge le fichier FXML {@code error-popup.fxml} pour construire
     *       l'interface utilisateur.</li>
     *   <li>Crée une nouvelle scène et un nouveau {@link Stage} en mode modal.</li>
     *   <li>Modifie le texte d'erreur via {@link #errorText}.</li>
     *   <li>Associe un événement au bouton "OK" pour fermer la fenêtre.</li>
     *   <li>Affiche la fenêtre et attend que l'utilisateur la ferme
     *       (méthode {@link Stage#showAndWait()}).</li>
     * </ul>
     *
     * @param message Le message d'erreur à afficher dans la fenêtre.
     *                Il sera injecté dans {@link #errorText}.
     */
    public static void showError(String message) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ErrorPopup.class.getResource("/fr/lasschko/pocketimperium/pocketimperium/view/error-popup.fxml"));
            Parent ErrorPopupRoot = fxmlLoader.load();

            ErrorPopup controller = fxmlLoader.getController();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Error");

            // Ferme la fenêtre lorsque le bouton OK est cliqué
            controller.okButton.setOnAction(event -> stage.close());
            controller.errorText.setText(message);

            // Définit le message d'erreur dans la zone de texte
            Scene scene = new Scene(ErrorPopupRoot);
            stage.setScene(scene);

            // Affiche la fenêtre et attend qu'elle se ferme
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
