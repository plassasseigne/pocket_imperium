package fr.lasschko.pocketimperium.pocketimperium.model;

import java.util.*;

/**
 * Représente un graphe des hexagones sur la carte, où chaque hexagone est connecté à ses voisins.
 * Permet de calculer les relations entre hexagones et les déplacements possibles sur la carte.
 */
public class HexesGraph {
    /**
     * Le graphe représentant les connexions entre les hexagones.
     * Chaque hexagone est associé à une liste de ses voisins.
     */
    private final Map<Hex, List<Hex>> hexesGraph = new HashMap<>();

    /**
     * Les directions pour identifier les voisins d'un hexagone dans la grille hexagonale.
     */
    private final double[][] directions = {
            {0, -55},        // HAUT
            {48, -27.5},     // HAUT-DROITE
            {48, 27.5},      // BAS-DROITE
            {0, 55},         // BAS
            {-48, 27.5},     // BAS-GAUCHE
            {-48, -27.5}     // HAUT-GAUCHE
    };

    /**
     * Construit le graphe des hexagones à partir d'une liste d'hexagones.
     *
     * @param hexes la liste des hexagones à connecter
     */
    public HexesGraph(List<Hex> hexes) {
        linkHexes(hexes);
    }

    /**
     * Relie chaque hexagone de la liste à ses voisins et met à jour le graphe.
     *
     * @param hexes la liste des hexagones
     */
    private void linkHexes(List<Hex> hexes) {
        for (Hex hex : hexes) {
            List<Hex> neighbors = findNeighbors(hex, hexes);
            hexesGraph.put(hex, neighbors);
        }
    }

    /**
     * Trouve les voisins d'un hexagone donné à partir de la liste d'hexagones.
     *
     * @param hex       l'hexagone dont on cherche les voisins
     * @param allHexes  la liste complète des hexagones
     * @return une liste des voisins de l'hexagone
     */
    private List<Hex> findNeighbors(Hex hex, List<Hex> allHexes) {
        List<Hex> neighbors = new ArrayList<>();
        for (Hex candidate : allHexes) {
            if (isNeighbor(hex, candidate)) {
                neighbors.add(candidate);
            }
        }
        return neighbors;
    }

    /**
     * Vérifie si deux hexagones sont voisins en fonction de leurs coordonnées.
     *
     * @param h1 le premier hexagone
     * @param h2 le second hexagone
     * @return true si les deux hexagones sont voisins, false sinon
     */
    public boolean isNeighbor(Hex h1, Hex h2) {
        for (double[] direction : directions) {
            double x = h1.getX() + direction[0];
            double y = h1.getY() + direction[1];
            if (x == h2.getX() && y == h2.getY()) {

                if (h1.getSystemLevel() == SystemLevel.LEVEL_3 && h2.getSystemLevel() == SystemLevel.LEVEL_3) {
                    return false;
                }
                if (h1.getSystemLevel() == SystemLevel.LEVEL_3) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Calcule le nombre de déplacements nécessaires pour aller d'un hexagone de départ à un hexagone cible.
     *
     * @param start  l'hexagone de départ
     * @param target l'hexagone cible
     * @return le nombre de déplacements nécessaires, ou -1 si le déplacement est impossible
     */
    public int getMoveCount(Hex start, Hex target) {

        Queue<Hex> queue = new LinkedList<>();
        Set<Hex> visited = new HashSet<>();
        queue.add(start);
        visited.add(start);

        int moves = 0;
        if (start.equals(target)) {
            return 1;
        }
        while (!queue.isEmpty() && moves < 3) {
            int size = queue.size();
            moves++;

            for (int i = 0; i < size; i++) {
                Hex current = queue.poll();

                for (Hex neighbour : hexesGraph.getOrDefault(current, Collections.emptyList())) {
                    if (neighbour.equals(target)) {
                        return moves;
                    }

                    if (!visited.contains(neighbour)) {
                        queue.add(neighbour);
                        visited.add(neighbour);
                    }
                }
            }
        }
        return -1;
    }

    /**
     * Renvoie le graphe des hexagones.
     *
     * @return une map contenant les hexagones et leurs voisins
     */
    public Map<Hex, List<Hex>> getHexesGraph() {
        return hexesGraph;
    }
}
