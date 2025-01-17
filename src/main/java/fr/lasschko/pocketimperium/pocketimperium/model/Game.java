package fr.lasschko.pocketimperium.pocketimperium.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {
    private final List<Sector> sectors;
    private final List<List<String>> configuration = Arrays.asList(
            List.of("CENTRAL"),
            List.of("NE"),
            List.of("E"),
            List.of("SE"),
            List.of("SW"),
            List.of("W"),
            List.of("NW")
    );
    private final List<Hex> hexes = new ArrayList<>();
    private GameMap gameMap;
    private List<Player> players = new ArrayList<>();
    private int currentPlayerIndex = 0;
    private int round = 0;
    private int turn = 0;
    private int phase = 0;
    private HexesGraph hexesGraph;

    public Game() {
        gameMap = new GameMap(configuration);
        gameMap.initialize();
        sectors = gameMap.getSectors();
        for (Sector sector : sectors) {
            hexes.addAll(sector.getHexes());
        }
        hexesGraph = new HexesGraph(hexes);
    }

    public List<Sector> getSectors() {
        return sectors;
    }

    public List<Hex> getHexes() {
        return hexes;
    }

    public HexesGraph getHexesGraph() {
        return hexesGraph;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getNumberOfPlayers(){
        return players.size();
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public void changeCurrentPlayerIndex(int by) {
        setCurrentPlayerIndex(getCurrentPlayerIndex() + by);
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }
}
