package fr.lasschko.pocketimperium.pocketimperium.controller;

import fr.lasschko.pocketimperium.pocketimperium.model.Game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Contrôleur pour l'interface de sélectionner des commandes pour chaque
 * joueur. Elle permet aux joueurs de définir l'ordre voulu pour l'exécution
 * de chaque commande.
 *
 * @author Paul et Yevhenii
 * @version 1.0
 */
public class CommandSelectionController {
    /**
     * Boutons représentant le choix de l'ordre des commandes.
     */
    @FXML
    private Button expandButton;
    @FXML
    private Button exploreButton;
    @FXML
    private Button exterminateButton;

    /**
     * Bouton de confirmation pour valider l'ordre sélectionné.
     */
    @FXML
    private Button confirmButton;

    @FXML
    private Text actualPlayer;
    @FXML
    private Text instruction;

    private List<String> commandOrder = new ArrayList<>();
    private Game game;

    /**
     * Récupère l'instance de la partie actuelle pour avoir toutes les
     * informations nécessaires.
     *
     * @param game
     */
    public CommandSelectionController(Game game) {
        this.game = game;
    }

    public void createCommandSelection(CountDownLatch latch) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fr/lasschko/pocketimperium/pocketimperium/view/command-selection.fxml"));
            CommandSelectionController controller = new CommandSelectionController(game);
            fxmlLoader.setController(controller);
            Parent CommandSelectionRoot = fxmlLoader.load();
            Stage stage = new Stage();

            controller.initUi(latch);

            Scene scene = new Scene(CommandSelectionRoot, 450, 250);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initUi(CountDownLatch latch) {
        actualPlayer.setText(game.getCurrentPlayer().getName());

        expandButton.setOnAction(event -> toggleCommandSelection("Expand", expandButton));
        exploreButton.setOnAction(event -> toggleCommandSelection("Explore", exploreButton));
        exterminateButton.setOnAction(event -> toggleCommandSelection("Exterminate", exterminateButton));

        confirmButton.setOnAction(event -> {
            if (commandOrder.size() == 3) {
                game.getCurrentPlayer().setCommandOrder(new ArrayList<>(commandOrder));
                commandOrder.clear();
                latch.countDown();
                confirmButton.getScene().getWindow().hide();
            } else {
                instruction.setText("Please select all three commands!");
            }
        });
    }

    private void toggleCommandSelection(String command, Button button) {
        if (commandOrder.contains(command)) {
            commandOrder.remove(command);
            button.setStyle("");
        } else {
            if (commandOrder.size() < 3) {
                commandOrder.add(command);
                button.setStyle("-fx-background-color: lightgreen;");
            }
        }
    }
}
