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
    private GameMap gameMap;
    private final List<Hex> hexes = new ArrayList<>();

    public Game() {
        gameMap = new GameMap(configuration);
        gameMap.initialize();
        sectors =  gameMap.getSectors();
        for(Sector sector : sectors) {
            hexes.addAll(sector.getHexes());
        }
    }

    public List<Sector> getSectors() {
        return sectors;
    }

    public List<Hex> getHexes() {
        return hexes;
    }

}
