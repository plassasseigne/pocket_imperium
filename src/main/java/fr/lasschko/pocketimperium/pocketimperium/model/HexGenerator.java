package fr.lasschko.pocketimperium.pocketimperium.model;

import java.util.*;

public class HexGenerator {
    private final CoordinatePlane coordinatePlane;
    private int hexId = 10;
    private List<Hex> hexes;

    public HexGenerator(CoordinatePlane coordinatePlane) {
        this.coordinatePlane = coordinatePlane;
    }

    public List<Hex> getHexes() {
        return hexes;
    }

    public void generateHexes(int numberOfHexes, Sector sector) {
        this.hexes = new ArrayList<>();

        double[][] transformedHexesCoor = coordinatePlane.getTransformedHexesCoordinates("CENTRAL");
        for (String side : sector.getSideAsList()) {
            transformedHexesCoor = coordinatePlane.getTransformedHexesCoordinates(side, transformedHexesCoor);
        }

        int coorId = 0;
        double[] hexCoor = transformedHexesCoor[coorId];

        if (sector.IsTriPrime()) {
            for (int i = hexId; i < hexId + numberOfHexes; i++) {
                hexCoor = transformedHexesCoor[coorId];
                hexes.add(new Hex(i, hexCoor[0], hexCoor[1], SystemLevel.LEVEL_3, sector));
                coorId++;
            }
        } else {
            HashMap<Integer, SystemLevel> systemPlanet = generateSystemPlanet(numberOfHexes);

            for (int i = hexId; i < hexId + numberOfHexes; i++) {
                hexCoor = transformedHexesCoor[coorId];
                hexes.add(new Hex(i, hexCoor[0], hexCoor[1], systemPlanet.getOrDefault(i, SystemLevel.LEVEL_0), sector));
                coorId++;
            }
        }
        hexId = modifyHexId(sector.getId());
    }

    private List<Integer> generateIdForSysLevel(int numberOfHexes) {
        List<Integer> idOfSystemLevel = new ArrayList<>();

        for (int i = hexId; i < hexId + numberOfHexes; i++) {
            idOfSystemLevel.add(i);
        }

        Collections.shuffle(idOfSystemLevel);
        idOfSystemLevel = idOfSystemLevel.subList(0, 3);

        return idOfSystemLevel;
    }

    private HashMap<Integer, SystemLevel> generateSystemPlanet(int numberOfHexes) {
        List<Integer> idOfSystemLevel = generateIdForSysLevel(numberOfHexes);

        List<SystemLevel> levels = new ArrayList<>();
        levels.add(SystemLevel.LEVEL_1);
        levels.add(SystemLevel.LEVEL_1);
        levels.add(SystemLevel.LEVEL_2);

        Collections.shuffle(levels);

        HashMap<Integer, SystemLevel> systemLevelsPlanet = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            systemLevelsPlanet.put(idOfSystemLevel.get(i), levels.get(i));
        }

        return systemLevelsPlanet;
    }

    private int modifyHexId(int sectorId) {
        return 10 * (sectorId + 1);
    }
}
