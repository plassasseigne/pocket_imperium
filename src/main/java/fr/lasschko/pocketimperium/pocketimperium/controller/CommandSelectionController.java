package fr.lasschko.pocketimperium.pocketimperium.controller;

import fr.lasschko.pocketimperium.pocketimperium.model.Game;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CommandSelectionController {


    private List<String> commandOrder = new ArrayList<>();

    private Game game;

    public CommandSelectionController(Game game) {
        this.game = game;
    }

    public void createCommandSelection(CountDownLatch latch) {
        Stage stage = new Stage();
        VBox layout = new VBox(10);
        Label instruction = new Label("Select the order of commands for " + game.getCurrentPlayer().getName());
        HBox commands = new HBox(10);

        Button expandButton = new Button("Expand");
        Button exploreButton = new Button("Explore");
        Button exterminateButton = new Button("Exterminate");
        Button confirmButton = new Button("Confirm");

        expandButton.setOnAction(event -> toggleCommandSelection("Expand", expandButton));
        exploreButton.setOnAction(event -> toggleCommandSelection("Explore", exploreButton));
        exterminateButton.setOnAction(event -> toggleCommandSelection("Exterminate", exterminateButton));

        confirmButton.setOnAction(event -> {
            if (commandOrder.size() == 3) {
                game.getCurrentPlayer().setCommandOrder(new ArrayList<>(commandOrder));
                commandOrder.clear();
                latch.countDown(); // Unblock the thread for the next player
                stage.close();
            } else {
                instruction.setText("Please select all three commands!");
            }
        });

        commands.getChildren().addAll(expandButton, exploreButton, exterminateButton);
        layout.getChildren().addAll(instruction, commands, confirmButton);

        Scene scene = new Scene(layout, 300, 200);
        stage.setScene(scene);
        stage.show();
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
