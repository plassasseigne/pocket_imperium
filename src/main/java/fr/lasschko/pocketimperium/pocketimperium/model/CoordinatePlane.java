package fr.lasschko.pocketimperium.pocketimperium.model;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CoordinatePlane {
    //Initial coor of hexes in the central sector
//    private final int[][] basisHexesCoordinates = {
//            {0, 0}, {0, -1}, {1, -1}, {1, 0}, {0, 1}, {-1, 1}, {-1, 0}
//    };
    private final double[][] basisHexesCoordinates = {
            {450, 270}, {450, 215}, {498, 242.5}, {498, 297.5}, {450, 325}, {402, 297.5}, {402, 242.5}
    };

    private final Map<String, Function<double[], double[]>> transformationFunctions = new HashMap<>();

    public CoordinatePlane() {
        this.setTransformationFunctions();
    }

    //Detect the coor for central hex in diff sides
    private void setTransformationFunctions() {
        Function<double[], double[]> CENTRAL = (vector) -> vector;
        Function<double[], double[]> NE = (vector) -> new double[]{vector[0] + 96, vector[1] - 110};
        Function<double[], double[]> E = (vector) -> new double[]{vector[0] + 144, vector[1] +27.5};
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

    // Transforming the hexes according to the side
    private double[][] transformHexesCoorInSector(String side, double[][] hexesCoordinates) {
        Function<double[], double[]> transformationFunction = this.transformationFunctions.get(side);
        double[][] transformedHexesCoor = new double[hexesCoordinates.length][hexesCoordinates[0].length];
        for (int i = 0; i < hexesCoordinates.length; i++) {
            transformedHexesCoor[i] = transformationFunction.apply(hexesCoordinates[i]);
        }
        return transformedHexesCoor;
    }

    public double[][] getTransformedHexesCoordinates(String side) {
        return transformHexesCoorInSector(side, this.basisHexesCoordinates);
    }

    public double[][] getTransformedHexesCoordinates(String side, double[][] hexesCoordinates) {
        return transformHexesCoorInSector(side, hexesCoordinates);
    }
}
