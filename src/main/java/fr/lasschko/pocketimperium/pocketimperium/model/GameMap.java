package fr.lasschko.pocketimperium.pocketimperium.model;

import java.util.*;

/**
 * Représente la carte du jeu, comprenant ses secteurs et sa configuration.
 * La carte est composée de secteurs, chacun contenant un groupe d'hexagones.
 */
public class GameMap {
    /**
     * Liste des secteurs présents sur la carte du jeu.
     */
    private final List<Sector> sectors = new ArrayList<>();

    /**
     * Plan de coordonnées utilisé pour organiser la grille hexagonale.
     */
    private final CoordinatePlane plane = new CoordinatePlane();

    /**
     * Générateur utilisé pour créer les hexagones de la carte.
     */
    private final HexGenerator hexGenerator = new HexGenerator(plane);

    /**
     * Configuration de la carte, spécifiant les détails pour chaque secteur.
     */
    private final List<List<String>> configuration;

    /**
     * Construit une carte du jeu avec la configuration spécifiée.
     *
     * @param configuration la configuration de la carte, spécifiant les détails des secteurs
     */
    public GameMap(List<List<String>> configuration) {
        this.configuration = configuration;
    }

    /**
     * Génère et initialise les secteurs en fonction de la configuration de la carte.
     * Chaque secteur se voit attribuer des hexagones, et un hexagone central est marqué comme "CENTRAL".
     */
    public void generateSideSectors() {
        int sectorID = 0;
        for (List<String> sides : configuration) {
            String sectorName = "";
            for (String side : sides) {
                sectorID += 1;
                sectorName += side;
                Sector sector = new Sector(sectorID, new ArrayList<>(), sectorName);

                // Génère les hexagones pour le secteur et les attribue
                hexGenerator.generateHexes(7, sector);
                List<Hex> hexes = hexGenerator.getHexes();
                sector.setHexes(hexes);

                // Marque le premier hexagone comme hexagone central
                Hex centralHex = sector.getHexes().getFirst();
                centralHex.setType("CENTRAL");

                // Ajoute le secteur à la carte
                sectors.add(sector);

                sectorName += " ";
            }
        }
    }

    /**
     * Renvoie la liste des secteurs de la carte du jeu.
     *
     * @return la liste des secteurs
     */
    public List<Sector> getSectors() {
        return sectors;
    }

    /**
     * Initialise la carte du jeu en générant ses secteurs.
     */
    public void initialize() {
        this.generateSideSectors();
    }
}
