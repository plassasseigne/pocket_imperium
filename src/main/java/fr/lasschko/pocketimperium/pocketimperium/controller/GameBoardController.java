package fr.lasschko.pocketimperium.pocketimperium.controller;

import fr.lasschko.pocketimperium.pocketimperium.model.*;
import fr.lasschko.pocketimperium.pocketimperium.view.HexView;
import fr.lasschko.pocketimperium.pocketimperium.view.SectorView;
import fr.lasschko.pocketimperium.pocketimperium.view.ShipView;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameBoardController implements Initializable {
    private final List<HexView> hexViews = new ArrayList<>();
    private final List<SectorView> sectorViews = new ArrayList<>();

    private Game game;
    private GameManager gameManager;

    private final List<ShipView> shipViews = new ArrayList<>();

    @FXML
    private Rectangle fadeRectangle;

    @FXML
    private Pane rootLayout;

    public GameBoardController() {}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), fadeRectangle);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0);
        fadeOut.play();
    }

    public void initData(Game game) {
        this.game = game;
        this.gameManager = new GameManager(this.game, this);

        createHexBoard();

        gameManager.start();
    }

    public List<HexView> getHexViews(){
        return hexViews;
    }
    public Pane getRootLayout() {
        return rootLayout;
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

    public void showError(String message) {
        System.err.println(message);
    }

    public void addShipView(ShipView shipView) {
        shipViews.add(shipView);
    }

    public List<ShipView> getShipViews(){
        return shipViews;
    }


}
