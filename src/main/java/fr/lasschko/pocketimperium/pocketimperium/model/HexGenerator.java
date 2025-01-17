package fr.lasschko.pocketimperium.pocketimperium.model;

import java.util.*;

/**
 * Représente un générateur d'hexagones pour un secteur de la carte.
 * Ce générateur crée des hexagones avec des coordonnées spécifiques, et les associe à un niveau de système donné.
 */
public class HexGenerator {
    /**
     * Le plan de coordonnées utilisé pour transformer les coordonnées des hexagones.
     */
    private final CoordinatePlane coordinatePlane;

    /**
     * L'ID du premier hexagone à générer.
     */
    private int hexId = 10;

    /**
     * La liste des hexagones générés.
     */
    private List<Hex> hexes;

    /**
     * Construit un générateur d'hexagones pour un plan de coordonnées donné.
     *
     * @param coordinatePlane le plan de coordonnées utilisé pour générer les hexagones
     */
    public HexGenerator(CoordinatePlane coordinatePlane) {
        this.coordinatePlane = coordinatePlane;
    }

    /**
     * Renvoie la liste des hexagones générés.
     *
     * @return la liste des hexagones
     */
    public List<Hex> getHexes() {
        return hexes;
    }

    /**
     * Génère un nombre spécifié d'hexagones pour un secteur donné.
     *
     * @param numberOfHexes le nombre d'hexagones à générer
     * @param sector le secteur auquel les hexagones seront associés
     */
    public void generateHexes(int numberOfHexes, Sector sector) {
        this.hexes = new ArrayList<>();

        // Transformation des coordonnées des hexagones de base
        double[][] transformedHexesCoor = coordinatePlane.getTransformedHexesCoordinates("CENTRAL");
        for (String side : sector.getSideAsList()) {
            transformedHexesCoor = coordinatePlane.getTransformedHexesCoordinates(side, transformedHexesCoor);
        }

        int coorId = 0;
        double[] hexCoor = transformedHexesCoor[coorId];

        // Génération des hexagones en fonction du type de secteur
        if (sector.IsTriPrime()) {
            for (int i = hexId; i < hexId + numberOfHexes; i++) {
                hexCoor = transformedHexesCoor[coorId];
                hexes.add(new Hex(i, hexCoor[0], hexCoor[1], SystemLevel.LEVEL_3, sector));
                coorId++;
            }
        } else {
            // Génération des niveaux de système pour les hexagones
            HashMap<Integer, SystemLevel> systemPlanet = generateSystemPlanet(numberOfHexes);

            for (int i = hexId; i < hexId + numberOfHexes; i++) {
                hexCoor = transformedHexesCoor[coorId];
                hexes.add(new Hex(i, hexCoor[0], hexCoor[1], systemPlanet.getOrDefault(i, SystemLevel.LEVEL_0), sector));
                coorId++;
            }
        }

        // Mise à jour de l'ID pour le prochain secteur
        hexId = modifyHexId(sector.getId());
    }

    /**
     * Génère une liste d'IDs pour les hexagones ayant un niveau de système spécifique.
     *
     * @param numberOfHexes le nombre d'hexagones pour lesquels générer des IDs
     * @return une liste d'IDs pour les hexagones sélectionnés
     */
    private List<Integer> generateIdForSysLevel(int numberOfHexes) {
        List<Integer> idOfSystemLevel = new ArrayList<>();

        for (int i = hexId; i < hexId + numberOfHexes; i++) {
            idOfSystemLevel.add(i);
        }

        // Mélange et sélectionne trois IDs pour les systèmes
        Collections.shuffle(idOfSystemLevel);
        idOfSystemLevel = idOfSystemLevel.subList(0, 3);

        return idOfSystemLevel;
    }

    /**
     * Génère un mapping entre les IDs des hexagones et leur niveau de système.
     * Les hexagones peuvent avoir les niveaux de système LEVEL_1, LEVEL_2 ou LEVEL_3.
     *
     * @param numberOfHexes le nombre d'hexagones pour lesquels générer un niveau de système
     * @return un HashMap associant les IDs des hexagones à leurs niveaux de système
     */
    private HashMap<Integer, SystemLevel> generateSystemPlanet(int numberOfHexes) {
        List<Integer> idOfSystemLevel = generateIdForSysLevel(numberOfHexes);

        List<SystemLevel> levels = new ArrayList<>();
        levels.add(SystemLevel.LEVEL_1);
        levels.add(SystemLevel.LEVEL_1);
        levels.add(SystemLevel.LEVEL_2);

        // Mélange les niveaux et les assigne aux hexagones
        Collections.shuffle(levels);

        HashMap<Integer, SystemLevel> systemLevelsPlanet = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            systemLevelsPlanet.put(idOfSystemLevel.get(i), levels.get(i));
        }

        return systemLevelsPlanet;
    }

    /**
     * Modifie l'ID des hexagones pour le prochain secteur.
     *
     * @param sectorId l'ID du secteur actuel
     * @return le nouvel ID pour les hexagones du prochain secteur
     */
    private int modifyHexId(int sectorId) {
        return 10 * (sectorId + 1);
    }
}
