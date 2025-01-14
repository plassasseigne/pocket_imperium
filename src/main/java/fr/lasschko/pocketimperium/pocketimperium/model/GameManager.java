package fr.lasschko.pocketimperium.pocketimperium.model;

public class GameManager {
    private final PhaseManager phaseManager;
    private boolean running;
    private int round;

    public GameManager() {
        phaseManager = new PhaseManager();
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
}
