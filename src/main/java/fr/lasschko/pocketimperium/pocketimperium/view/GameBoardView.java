package fr.lasschko.pocketimperium.pocketimperium.view;


import fr.lasschko.pocketimperium.pocketimperium.model.*;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

public class GameBoardView implements Initializable {
    private final List<HexView> hexViews = new ArrayList<>();
    private final List<SectorView> sectorViews = new ArrayList<>();
    private final Game game;
    @FXML
    private Rectangle fadeRectangle;

    @FXML
    private Pane rootLayout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), fadeRectangle);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0);

        fadeOut.play();

        createHexBoard();
    }
    public GameBoardView() {
        game = new Game(); // Getting the "DB" of our game
    }

    private void createHexBoard() {
        for(Sector sector: game.getSectors()) {
            addSector(sector);
        }
        //Draw hexes
        for(HexView hexView: hexViews) {
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
}
