package fr.lasschko.pocketimperium.pocketimperium.controller;

import fr.lasschko.pocketimperium.pocketimperium.model.*;
import fr.lasschko.pocketimperium.pocketimperium.view.HexView;
import fr.lasschko.pocketimperium.pocketimperium.view.SectorView;
import fr.lasschko.pocketimperium.pocketimperium.view.ShipView;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
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

    @FXML
    private Rectangle playerSColor1;
    @FXML
    private Rectangle playerSColor2;
    @FXML
    private Rectangle playerSColor3;

    @FXML
    private Text playerSName1;
    @FXML
    private Text playerSName2;
    @FXML
    private Text playerSName3;

    @FXML
    private Text playerSShip1;
    @FXML
    private Text playerSShip2;
    @FXML
    private Text playerSShip3;

    @FXML
    private Text playerSScore1;
    @FXML
    private Text playerSScore2;
    @FXML
    private Text playerSScore3;

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

        updateUiContent();
        createHexBoard();
        gameManager.start();
    }

    public void updateUiContent() {
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        Player player3 = game.getPlayers().get(2);

        playerSName1.setText(player1.getName());
        playerSName2.setText(player2.getName());
        playerSName3.setText(player3.getName());

        playerSColor1.setFill(player1.getColor());
        playerSColor2.setFill(player2.getColor());
        playerSColor3.setFill(player3.getColor());

        playerSShip1.setText(String.valueOf(player1.getShips().size()));
        playerSShip2.setText(String.valueOf(player2.getShips().size()));
        playerSShip3.setText(String.valueOf(player3.getShips().size()));

        playerSScore1.setText(String.valueOf(player1.getScore()));
        playerSScore2.setText(String.valueOf(player2.getScore()));
        playerSScore3.setText(String.valueOf(player3.getScore()));
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

        updateUiContent();
    }

    public List<ShipView> getShipViews(){
        return shipViews;
    }
}
