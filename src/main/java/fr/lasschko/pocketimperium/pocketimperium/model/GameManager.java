package fr.lasschko.pocketimperium.pocketimperium.model;

import fr.lasschko.pocketimperium.pocketimperium.view.GameBoardView;
import fr.lasschko.pocketimperium.pocketimperium.view.HexView;
import javafx.application.Platform;

import java.util.concurrent.atomic.AtomicBoolean;

public class GameManager {
    private final PhaseManager phaseManager;
    private final Game game;
    private final AtomicBoolean reverse = new AtomicBoolean(false);
    private boolean running;
    private int round;
    private GameBoardView gameBoardView;

    public GameManager(Game game, GameBoardView gameBoardView) {
        phaseManager = new PhaseManager();
        this.game = game;
        this.gameBoardView = gameBoardView;
        running = true;
        round = 1;
    }

    public int getPhase() {
        return phaseManager.getPhase();
    }

    public void nextPhase() {
        phaseManager.nextPhase();
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void addRound() {
        setRound(getRound() + 1);
    }

    public void start() {
        new Thread(() -> {
            while (this.getRound() <= 9) {
                switch (this.getPhase()) {
                    case 0:
                        Platform.runLater(this::startPhase0);
                        break;
//                    case 1:
//                        System.out.println("Phase 1");
//                        break;
//                    case 2:
//                        System.out.println("Phase 2");
//                        break;
//                    case 3:
//                        System.out.println("Phase 3");
//                        this.addRound();
//                        break;
                    default:
                        this.addRound();
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

    public void startPhase0() {
        for (HexView hexView : gameBoardView.getHexViews()) {
            Hex hex = hexView.getHex();
            hexView.getPolygon().setOnMouseClicked(event -> {
                Player currentPlayer = game.getCurrentPlayer();
                if (hex.getSystemLevel() == SystemLevel.LEVEL_1 && !hex.getSector().isInitialDeployed()) {
                    new ExpandCommand(2).execute(gameBoardView.getRootLayout(), currentPlayer, hexView);
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
                    gameBoardView.showError("It inst planet with systel level 1 or this sector is initialy demployed");
                }
            });
        }


    }
}
