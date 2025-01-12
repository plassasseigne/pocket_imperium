package fr.lasschko.pocketimperium.pocketimperium.model;


import java.util.*;

public class HexesGraph {
    private final Map<Hex, List<Hex>> hexesGraph = new HashMap<>();

    private final double[][] directions = {
            {0, -55}, // TOP
            {48, -27.5}, // TOP-RIGHT
            {48, 27.5}, // BOTTOM-RIGHT
            {0, 55}, // BOTTOM
            {-48, 27.5}, // BOTTOM-LEFT
            {-48, -27.5} // TOP-LEFT
    };

    public HexesGraph(List<Hex> hexes) {
        linkHexes(hexes);
    }

    private void linkHexes(List<Hex> hexes) {
        // Create an adjacency list for hex neighbors
        for (Hex hex : hexes) {
            List<Hex> neighbors = findNeighbors(hex, hexes);
            hexesGraph.put(hex, neighbors);
        }
    }

    private List<Hex> findNeighbors(Hex hex, List<Hex> allHexes) {
        List<Hex> neighbors = new ArrayList<>();
        for (Hex candidate : allHexes) {
            if (isNeighbor(hex, candidate)) {
                neighbors.add(candidate);
            }
        }
        return neighbors;
    }

    public boolean isNeighbor(Hex h1, Hex h2) {
        for (double[] direction : directions) {
            double x = h1.getX() + direction[0];
            double y = h1.getY() + direction[1];
            if (x == h2.getX() && y == h2.getY()) {
                // verify the rules of different system level movements
                if (h1.getSystemLevel() == SystemLevel.LEVEL_3 && h2.getSystemLevel() == SystemLevel.LEVEL_3) {
                    return false; //Impossible to move between LEVEL_3
                }
                if (h1.getSystemLevel() == SystemLevel.LEVEL_3) {
                    return false; // Impossible to move from LEVEL_3 to LEVEL_<3
                }
                return true;
            }
        }
        return false;
    }

    public int getMoveCount(Hex start, Hex target) {

        Queue<Hex> queue = new LinkedList<>();
        Set<Hex> visited = new HashSet<>();
        queue.add(start);
        visited.add(start);

        int moves = 0;

        // BFS
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

    public Map<Hex, List<Hex>> getHexesGraph() {
        return hexesGraph;
    }
}
