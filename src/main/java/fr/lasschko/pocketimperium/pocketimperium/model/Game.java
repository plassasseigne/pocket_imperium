package fr.lasschko.pocketimperium.pocketimperium.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Représente le jeu principal, contenant les secteurs, les hexagones, les joueurs
 * et la logique pour gérer les tours, les phases et les rounds.
 */
public class Game {

    /**
     * Liste des secteurs du jeu.
     */
    private final List<Sector> sectors;

    /**
     * Configuration des secteurs dans le jeu.
     */
    private final List<List<String>> configuration = Arrays.asList(
            List.of("CENTRAL"),
            List.of("NE"),
            List.of("E"),
            List.of("SE"),
            List.of("SW"),
            List.of("W"),
            List.of("NW")
    );

    /**
     * Liste des hexagones présents dans le jeu.
     */
    private final List<Hex> hexes = new ArrayList<>();

    /**
     * La carte du jeu.
     */
    private GameMap gameMap;

    /**
     * Liste des joueurs.
     */
    private List<Player> players = new ArrayList<>();

    /**
     * Index du joueur actuellement actif.
     */
    private int currentPlayerIndex = 0;

    /**
     * Numéro du round actuel.
     */
    private int round = 0;

    /**
     * Numéro du tour actuel.
     */
    private int turn = 0;

    /**
     * Phase actuelle du jeu.
     */
    private int phase = 0;

    /**
     * Graphe des hexagones utilisé pour calculer les déplacements.
     */
    private HexesGraph hexesGraph;

    /**
     * Initialise une nouvelle instance de jeu, en configurant la carte,
     * les secteurs et le graphe des hexagones.
     */
    public Game() {
        gameMap = new GameMap(configuration);
        gameMap.initialize();
        sectors = gameMap.getSectors();
        for (Sector sector : sectors) {
            hexes.addAll(sector.getHexes());
        }
        hexesGraph = new HexesGraph(hexes);
    }

    /**
     * Obtient la liste des secteurs.
     *
     * @return La liste des secteurs.
     */
    public List<Sector> getSectors() {
        return sectors;
    }

    /**
     * Obtient le graphe des hexagones.
     *
     * @return Le graphe des hexagones.
     */
    public HexesGraph getHexesGraph() {
        return hexesGraph;
    }

    /**
     * Obtient la liste des joueurs.
     *
     * @return La liste des joueurs.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Définit la liste des joueurs.
     *
     * @param players La nouvelle liste des joueurs.
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * Obtient le nombre de joueurs dans le jeu.
     *
     * @return Le nombre de joueurs.
     */
    public int getNumberOfPlayers() {
        return players.size();
    }

    /**
     * Obtient l'index du joueur actuellement actif.
     *
     * @return L'index du joueur actuel.
     */
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    /**
     * Définit l'index du joueur actuellement actif.
     *
     * @param currentPlayerIndex Le nouvel index du joueur actif.
     */
    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    /**
     * Change l'index du joueur actif en l'incrémentant ou le décrémentant.
     *
     * @param by La valeur à ajouter à l'index actuel.
     */
    public void changeCurrentPlayerIndex(int by) {
        setCurrentPlayerIndex(getCurrentPlayerIndex() + by);
    }

    /**
     * Obtient le joueur actuellement actif.
     *
     * @return Le joueur actif.
     */
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    /**
     * Obtient le numéro du round actuel.
     *
     * @return Le numéro du round.
     */
    public int getRound() {
        return round;
    }

    /**
     * Définit le numéro du round actuel.
     *
     * @param round Le nouveau numéro du round.
     */
    public void setRound(int round) {
        this.round = round;
    }

    /**
     * Obtient le numéro du tour actuel.
     *
     * @return Le numéro du tour.
     */
    public int getTurn() {
        return turn;
    }

    /**
     * Obtient la phase actuelle du jeu.
     *
     * @return La phase actuelle.
     */
    public int getPhase() {
        return phase;
    }

    /**
     * Définit la phase actuelle du jeu.
     *
     * @param phase La nouvelle phase du jeu.
     */
    public void setPhase(int phase) {
        this.phase = phase;
    }
}
