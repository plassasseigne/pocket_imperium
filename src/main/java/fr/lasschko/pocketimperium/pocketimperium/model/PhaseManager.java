package fr.lasschko.pocketimperium.pocketimperium.model;

public class PhaseManager {
    private int phase;
    private int tour = 1;

    public PhaseManager() {
        this.phase = 0;
    }

    public int getPhase(){
        return phase;
    }

    public int getTour(){
        return tour;
    }

    public int nextPhase(){
        if(phase == 3){
            phase = 1;
        }else {
            phase++;
        }
        return phase;
    }

    public int nextTour(){
        if(tour == 3){
            tour = 1;
        }else {
            tour++;
        }
        return tour;
    }

}
