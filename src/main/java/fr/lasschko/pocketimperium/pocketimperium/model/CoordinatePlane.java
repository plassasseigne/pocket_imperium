package fr.lasschko.pocketimperium.pocketimperium.model;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Représente un plan de coordonnées utilisé pour calculer et transformer les coordonnées des hexagones
 * dans un secteur central et ses côtés adjacents.
 */
public class CoordinatePlane {

    /**
     * Coordonnées initiales des hexagones dans le secteur central.
     */
    private final double[][] basisHexesCoordinates = {
            {450, 270}, {450, 215}, {498, 242.5}, {498, 297.5}, {450, 325}, {402, 297.5}, {402, 242.5}
    };

    /**
     * Une map contenant des fonctions de transformation pour différents côtés par rapport au secteur central.
     */
    private final Map<String, Function<double[], double[]>> transformationFunctions = new HashMap<>();

    /**
     * Construit un nouveau {@code CoordinatePlane} et initialise les fonctions de transformation.
     */
    public CoordinatePlane() {
        this.setTransformationFunctions();
    }

    /**
     * Initialise les fonctions de transformation pour différents côtés du secteur central.
     */
    private void setTransformationFunctions() {
        Function<double[], double[]> CENTRAL = (vector) -> vector;
        Function<double[], double[]> NE = (vector) -> new double[]{vector[0] + 96, vector[1] - 110};
        Function<double[], double[]> E = (vector) -> new double[]{vector[0] + 144, vector[1] + 27.5};
        Function<double[], double[]> SE = (vector) -> new double[]{vector[0] + 48, vector[1] + 137.5};
        Function<double[], double[]> SW = (vector) -> new double[]{vector[0] - 96, vector[1] + 110};
        Function<double[], double[]> W = (vector) -> new double[]{vector[0] - 144, vector[1] - 27.5};
        Function<double[], double[]> NW = (vector) -> new double[]{vector[0] - 48, vector[1] - 137.5};

        this.transformationFunctions.put("CENTRAL", CENTRAL);
        this.transformationFunctions.put("NE", NE);
        this.transformationFunctions.put("E", E);
        this.transformationFunctions.put("SE", SE);
        this.transformationFunctions.put("SW", SW);
        this.transformationFunctions.put("W", W);
        this.transformationFunctions.put("NW", NW);
    }

    /**
     * Transforme les coordonnées des hexagones en fonction du côté spécifié.
     *
     * @param side Le côté par rapport au secteur central (par exemple, "NE", "E").
     * @param hexesCoordinates Les coordonnées des hexagones à transformer.
     * @return Les coordonnées transformées des hexagones.
     */
    private double[][] transformHexesCoorInSector(String side, double[][] hexesCoordinates) {
        Function<double[], double[]> transformationFunction = this.transformationFunctions.get(side);
        double[][] transformedHexesCoor = new double[hexesCoordinates.length][hexesCoordinates[0].length];
        for (int i = 0; i < hexesCoordinates.length; i++) {
            transformedHexesCoor[i] = transformationFunction.apply(hexesCoordinates[i]);
        }
        return transformedHexesCoor;
    }

    /**
     * Obtient les coordonnées transformées des hexagones dans le côté spécifié du secteur central
     * en utilisant les coordonnées par défaut des hexagones de base.
     *
     * @param side Le côté par rapport au secteur central (par exemple, "NE", "E").
     * @return Les coordonnées transformées des hexagones.
     */
    public double[][] getTransformedHexesCoordinates(String side) {
        return transformHexesCoorInSector(side, this.basisHexesCoordinates);
    }

    /**
     * Obtient les coordonnées transformées des hexagones dans le côté spécifié du secteur central
     * en utilisant des coordonnées personnalisées des hexagones.
     *
     * @param side Le côté par rapport au secteur central (par exemple, "NE", "E").
     * @param hexesCoordinates Les coordonnées personnalisées des hexagones à transformer.
     * @return Les coordonnées transformées des hexagones.
     */
    public double[][] getTransformedHexesCoordinates(String side, double[][] hexesCoordinates) {
        return transformHexesCoorInSector(side, hexesCoordinates);
    }
}
