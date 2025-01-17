package fr.lasschko.pocketimperium.pocketimperium;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Classe principale du jeu Pocket Imperium.
 * Elle hérite de {@link javafx.application.Application} et définit le point d'entrée
 * de l'interface graphique de l'application.
 *
 * <p>
 * Cette classe charge la scène principale à partir du FXML {@code main-menu.fxml},
 * applique les feuilles de style, puis affiche la fenêtre (Stage) aux dimensions
 * spécifiées.
 * </p>
 *
 * @author Paul et Yevhenii
 * @version 1.0
 */
public class HelloApplication extends Application {
    /**
     * Point d'entrée principal pour la partie JavaFX de Pocket Imperium.
     * Cette méthode est appelée au lancement de l'application et configure
     * la scène principale, les fichiers css et le titre de la fenêtre.
     *
     * @param stage La fenêtre (Stage) principale de l'application
     * @throws IOException Si le fichier FXML n'a pas pu être chargé
     */
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

    /**
     * Point d'entrée principal de Pocket Imperium.
     * Cette méthode appelle {@link #launch(String...)} pour démarrer
     * le cycle de vie JavaFX.
     *
     * @param args Arguments de la ligne de commande
     */
    public static void main(String[] args) {
        launch();
    }
}