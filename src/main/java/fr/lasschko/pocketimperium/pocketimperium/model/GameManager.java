package fr.lasschko.pocketimperium.pocketimperium.model;

import fr.lasschko.pocketimperium.pocketimperium.controller.CommandSelectionController;
import fr.lasschko.pocketimperium.pocketimperium.controller.GameBoardController;
import fr.lasschko.pocketimperium.pocketimperium.view.HexView;
import fr.lasschko.pocketimperium.pocketimperium.view.ShipView;
import javafx.application.Platform;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameManager {
    private final PhaseManager phaseManager;
    private final Game game;
    private final AtomicBoolean reverse = new AtomicBoolean(false);
    private final AtomicBoolean allPlayersReady = new AtomicBoolean(false);
    private final Map<Player, List<String>> playerCommands;
    private final GameBoardController gameBoardController;
    private final CommandSelectionController commandSelectionController;
    private ShipView selectedShipView;
    private int round;

    public GameManager(Game game, GameBoardController gameBoardController) {
        this.phaseManager = new PhaseManager(game.getPhase(), game.getTurn());
        this.game = game;
        this.gameBoardController = gameBoardController;
        this.commandSelectionController = new CommandSelectionController(game);

        this.round = game.getRound();
        this.playerCommands = new LinkedHashMap<>();
    }

    public int getPhase() {
        return phaseManager.getPhase();
    }

    public void nextPhase() {
        phaseManager.nextPhase();
        game.setPhase(phaseManager.getPhase());
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
        game.setRound(this.round);
    }

    public void addRound() {
        setRound(getRound() + 1);
    }

    public void start() {
        new Thread(() -> {
            while (this.getRound() < 9) {
                switch (this.getPhase()) {
                    case 0:
                        Platform.runLater(this::startPhase0);

                        break;
                    case 1:
                        startPhase1();
                        break;
                    default:
                        this.addRound();
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

    public void startPhase0() {
        for (HexView hexView : gameBoardController.getHexViews()) {
            Hex hex = hexView.getHex();
            hexView.getPolygon().setOnMouseClicked(event -> {
                Player currentPlayer = game.getCurrentPlayer();
                if (hex.getSystemLevel() == SystemLevel.LEVEL_1 && !hex.getSector().isInitialDeployed()) {
                    new ExpandCommand(2).execute(gameBoardController, currentPlayer, hexView);
                    //Check for reverse move
                    if (!reverse.get()) {
                        game.changeCurrentPlayerIndex(1);
                        if (game.getCurrentPlayerIndex() == 3) {
                            reverse.set(true);
                            game.changeCurrentPlayerIndex(-1);
                        }
                    } else {
                        game.changeCurrentPlayerIndex(-1);
                        if (game.getCurrentPlayerIndex() == -1) {
                            game.changeCurrentPlayerIndex(1);
                            this.nextPhase();
                        }
                    }

                } else {
                    gameBoardController.showError("It inst planet with systel level 1 or this sector is initialy demployed");
                }
            });
        }


    }

    public void startPhase1() {
        for (int i = 0; i < game.getNumberOfPlayers(); i++) {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.runLater(() -> commandSelectionController.createCommandSelection(latch));
            try {
                latch.await(); // Block until the player finishes selection
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            playerCommands.put(game.getCurrentPlayer(), game.getCurrentPlayer().getCommandOrder());
            game.changeCurrentPlayerIndex(1);
        }
        allPlayersReady.set(true);
        nextPhase();
    }



    private void moveShip() {
        selectShipView();
        for (HexView hexView : gameBoardController.getHexViews()) {
            hexView.getPolygon().setOnMouseClicked(event -> {
                new ExploreCommand(game).execute(selectedShipView, hexView);
            });
        }
    }

    private void selectShipView() {
        for (ShipView shipView : gameBoardController.getShipViews()) {
            shipView.getBody().setOnMouseClicked(event -> {
                if (selectedShipView == null) {
                    selectedShipView = shipView;
                    selectedShipView.select();
                } else {
                    selectedShipView.deselect();
                    selectedShipView = null;
                }
            });
        }
    }
}
