package fr.lasschko.pocketimperium.pocketimperium.controller;

import fr.lasschko.pocketimperium.pocketimperium.model.Game;
import fr.lasschko.pocketimperium.pocketimperium.model.Player;
import fr.lasschko.pocketimperium.pocketimperium.utils.ErrorPopup;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Contrôleur pour le menu principal de Pocket Imperium. Cette classe gère l'affichage
 * et les transitions entre le menu principal et l'écran de configuration des joueurs,
 * ainsi que le lancement de la partie.
 *
 * <p>
 * Elle implémente l'interface {@link javafx.fxml.Initializable}, ce qui lui permet
 * d'initialiser les différents composants JavaFX après le chargement du fichier FXML.
 * </p>
 *
 * @author Paul et Yevhenii
 * @version 1.0
 */
public class MainMenuController implements Initializable {
    /**
     * VBOX représentant le menu principal dans l'interface graphique.
     */
    @FXML
    private VBox mainMenu;

    /**
     * VBOX représentant l'écran de configuration des joueurs.
     */
    @FXML
    private VBox playerSetupMenu;

    /**
     * SVG du vaisseau de chaque joueur.
     */
    @FXML
    private SVGPath svgShip1;
    @FXML
    private SVGPath svgShip2;
    @FXML
    private SVGPath svgShip3;

    /**
     * Sélecteur de couleur de chaque joueur.
     */
    @FXML
    private ColorPicker colorPicker1;
    @FXML
    private ColorPicker colorPicker2;
    @FXML
    private ColorPicker colorPicker3;

    /**
     * Champs de texte pour récupérer le nom de chaque joueur.
     */
    @FXML
    private TextField playerName1;
    @FXML
    private TextField playerName2;
    @FXML
    private TextField playerName3;

    /**
     * Initialise le contrôleur après le chargement du fichier FXML.
     * Ici, on associe un événement au changement de couleur dans chaque
     * {@link ColorPicker}, afin d'actualiser la couleur du SVG correspondant.
     *
     * @param url L'emplacement utilisé pour résoudre les chemins relatifs
     * @param resourceBundle Les ressources de localisation pour cette classe
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<ColorPicker> colorPickerList = List.of(colorPicker1, colorPicker2, colorPicker3);
        List<SVGPath> svgList = List.of(svgShip1, svgShip2, svgShip3);

        for (int i = 0; i < 3; i++) {
            int j = i;
            colorPickerList.get(i).setOnAction(event -> {
                Color selectedColor = colorPickerList.get(j).getValue();

                svgList.get(j).setFill(selectedColor);
            });
        }
    }

    /**
     * Gère l'action du bouton "Exit". Ferme l'application lorsque le bouton est cliqué.
     *
     * @param event L'événement généré par l'interface utilisateur
     */
    @FXML
    private void handleExitButton(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Gère l'action du bouton "Play". Lance les animations pour faire la transition
     * entre le menu principal et l'écran de configuration des joueurs.
     */
    @FXML
    private void handlePlayButton() {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(600), mainMenu);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> mainMenu.setVisible(false));

        playerSetupMenu.setVisible(true);
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(500), playerSetupMenu);
        slideIn.setFromY(600);
        slideIn.setToY(0);
        playerSetupMenu.setOpacity(1.0);

        fadeOut.play();
        slideIn.play();
    }

    /**
     * Gère l'action du bouton "Cancel". Lance les animations pour faire la transition
     * inversée entre l'écran de configuration des joueurs et le menu principal.
     */
    @FXML
    private void handleCancelButton() {
        TranslateTransition slideOut = new TranslateTransition(Duration.millis(600), playerSetupMenu);
        slideOut.setToY(600);
        slideOut.setOnFinished(event -> playerSetupMenu.setVisible(false));

        mainMenu.setVisible(true);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), mainMenu);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        slideOut.play();
        fadeIn.play();
    }

    /**
     * Gère l'action du bouton "Start". Vérifie que les noms de joueurs ne sont pas
     * vides, puis charge la vue du plateau de jeu et initialise le contrôleur associé
     * avec la configuration du {@link Game}.
     *
     * @param event L'événement généré par l'interface utilisateur
     */
    @FXML
    private void startGame(ActionEvent event) {
        Game game = getGame();

        if (!playerName1.getText().isEmpty() && !playerName2.getText().isEmpty() && !playerName3.getText().isEmpty()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fr/lasschko/pocketimperium/pocketimperium/view/game-board.fxml"));
                Parent gameBoardRoot = fxmlLoader.load();

                GameBoardController gameBoardController = fxmlLoader.getController();

                gameBoardController.initData(game);

                Scene gameBoardScene = new Scene(gameBoardRoot, 1280, 720);

                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                stage.setScene(gameBoardScene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            ErrorPopup.showError("Please enter a name for every players.");
        }
    }

    /**
     * Crée et retourne une nouvelle instance de {@link Game} à partir des noms et
     * couleurs saisis par l'utilisateur. Les joueurs sont encapsulés dans un
     * {@link List} pour être ensuite utilisés dans le jeu.
     *
     * @return Un objet {@link Game} initialisé avec les informations des joueurs
     */
    private Game getGame() {
        String name1 = playerName1.getText();
        Color color1 = colorPicker1.getValue();

        String name2 = playerName2.getText();
        Color color2 = colorPicker2.getValue();

        String name3 = playerName3.getText();
        Color color3 = colorPicker3.getValue();

        Player player1 = new Player(name1, color1);
        Player player2 = new Player(name2, color2);
        Player player3 = new Player(name3, color3);

        List<Player> players = List.of(player1, player2, player3);

        Game game = new Game();

        game.setPlayers(players);
        return game;
    }
}
