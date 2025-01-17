package fr.lasschko.pocketimperium.pocketimperium.model;

/**
 * Gère les phases et les tours du jeu.
 * Permet de suivre la progression dans les différentes phases du jeu et de gérer le passage d'un tour à l'autre.
 */
public class PhaseManager {
    /**
     * La phase actuelle du jeu. Elle peut prendre les valeurs 1, 2 ou 3.
     */
    private int phase;

    /**
     * Le tour actuel du jeu. Il peut être soit 1 soit 2.
     */
    private int turn;

    /**
     * Construit un gestionnaire de phase avec la phase et le tour de départ.
     *
     * @param phase la phase de départ du jeu (1, 2 ou 3)
     * @param turn le tour de départ (1 ou 2)
     */
    public PhaseManager(int phase, int turn) {
        this.phase = phase;
        this.turn = turn;
    }

    /**
     * Renvoie la phase actuelle du jeu.
     *
     * @return la phase actuelle
     */
    public int getPhase() {
        return phase;
    }

    /**
     * Renvoie le tour actuel du jeu.
     *
     * @return le tour actuel
     */
    public int getTurn() {
        return turn;
    }

    /**
     * Passe à la phase suivante. Si la phase actuelle est la dernière (phase 3), elle recommence à la phase 1.
     *
     * @return la nouvelle phase après la transition
     */
    public int nextPhase() {
        if (phase == 3) {
            phase = 1;
        } else {
            phase++;
        }
        return phase;
    }

    /**
     * Passe au tour suivant. Si le tour actuel est le dernier (tour 2), il recommence à 1 et la phase suivante est déclenchée.
     *
     * @return le nouveau tour après la transition
     */
    public int nextTurn() {
        if (turn == 2) {
            turn = 1;
            nextPhase(); // Passe à la phase suivante à chaque nouveau tour
        } else {
            turn++;
        }
        return turn;
    }
}
