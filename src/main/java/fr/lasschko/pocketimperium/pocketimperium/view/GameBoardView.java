package fr.lasschko.pocketimperium.pocketimperium.view;


import fr.lasschko.pocketimperium.pocketimperium.model.*;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameBoardView implements Initializable {
    private final List<HexView> hexViews = new ArrayList<>();
    private final List<SectorView> sectorViews = new ArrayList<>();
    private final Game game;
    private final GameManager gameManager;
    private final AtomicBoolean reverse = new AtomicBoolean(false);
    @FXML
    private Rectangle fadeRectangle;

    @FXML
    private Pane rootLayout;


    public GameBoardView() {
        game = new Game(); // Getting the "DB" of our game
        gameManager = new GameManager();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), fadeRectangle);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0);

        fadeOut.play();

        createHexBoard();
        start();

    }

    private void start() {
        new Thread(() -> {
            while (gameManager.isRunning()) {
                switch (gameManager.getPhase()) {
                    case 0:
                        Platform.runLater(this::startPhase0);
                        break;
                    default:
                        gameManager.setRunning(false);
                        System.out.println("Phase 1");
                        System.out.println(game.getCurrentPlayerIndex());
                        break;
                }

                // Simulate a delay to avoid tight looping
                try {
                    Thread.sleep(100); // Adjust delay as needed
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    private void createHexBoard() {
        for (Sector sector : game.getSectors()) {
            addSector(sector);
        }
        //Draw hexes
        for (HexView hexView : hexViews) {
            rootLayout.getChildren().add(hexView.getPane());
        }
    }

    private void addSector(Sector sector) {
        SectorView sectorView = new SectorView(sector);
        sectorViews.add(sectorView);
        hexViews.addAll(sectorView.addHexes());
    }

    private void showError(String message) {
        System.err.println(message);
    }

    public void startPhase0() {

        for (HexView hexView : hexViews) {
            Hex hex = hexView.getHex();
            hexView.getPolygon().setOnMouseClicked(event -> {
                Player currentPlayer = game.getCurrentPlayer();
                if (hex.getSystemLevel() == SystemLevel.LEVEL_1 && !hex.getSector().isInitialDeployed()) {
                    new ExpandCommand(2).execute(rootLayout, currentPlayer, hexView);
                    //Check for reverse move
                    if(!reverse.get()){
                        game.changeCurrentPlayerIndex(1);
                        if(game.getCurrentPlayerIndex() == 3){
                            reverse.set(true);
                            game.changeCurrentPlayerIndex(-1);
                        }
                    }else{
                        game.changeCurrentPlayerIndex(-1);
                        if(game.getCurrentPlayerIndex() == -1){
                            game.changeCurrentPlayerIndex(1);
                            gameManager.nextPhase();
                        }
                    }

                } else {
                    showError("It inst planet with systel level 1 or this sector is initialy demployed");
                }
            });
        }


    }


}
