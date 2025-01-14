package fr.lasschko.pocketimperium.pocketimperium.model;

public class PhaseManager {
    private int phase;
    private int turn;

    public PhaseManager(int phase, int turn) {
        this.phase = phase;
        this.turn = turn;
    }


    public int getPhase() {
        return phase;
    }

    public int getTour() {
        return turn;
    }

    public int nextPhase() {
        if (phase == 3) {
            phase = 1;
        } else {
            phase++;
        }
        return phase;
    }

    public int nextTurn() {
        if (turn == 2) {
            turn = 1;
        } else {
            turn++;
        }
        return turn;
    }

}
