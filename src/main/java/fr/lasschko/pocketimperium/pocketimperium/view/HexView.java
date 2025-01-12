package fr.lasschko.pocketimperium.pocketimperium.view;

import fr.lasschko.pocketimperium.pocketimperium.model.Hex;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.List;

public class HexView {
    private final Hex hex;
    private final Pane pane;
    private final Polygon polygon;
    private Color color;

    public HexView(Hex hex) {
        this.hex = hex;
        pane = new Pane();

        polygon = new Polygon();
        polygon.getPoints().addAll(generateHexagonPoints(30));
        color = getColorBySystemLevel();
        polygon.setFill(color);
        polygon.setStroke(Color.BLACK);
        polygon.setSmooth(true);

        // Getting of center offset
        double offsetX = polygon.getBoundsInLocal().getWidth() / 2;
        double offsetY = polygon.getBoundsInLocal().getHeight() / 2;

        // Center the polygon according to the pane
        polygon.setTranslateX(offsetX);
        polygon.setTranslateY(offsetY);


        // Add hover effect on the polygon itself
        polygon.setOnMouseEntered(event -> polygon.setStrokeWidth(3)); // Thicker stroke on hover
        polygon.setOnMouseExited(event -> polygon.setStrokeWidth(1));  // Reset stroke on exit

        // Add click event on the polygon itself
        polygon.setOnMouseClicked(event -> System.out.println("Hex clicked: " + hex));

        pane.getChildren().add(polygon);
        pane.setLayoutX(hex.getX());
        pane.setLayoutY(hex.getY());
    }

    public Pane getPane() {
        return pane;
    }

    public Hex getHex(){
        return hex;
    }

    private static Double[] generateHexagonPoints(double size) {
        Double[] points = new Double[12];
        for (int i = 0; i < 6; i++) {
            points[i * 2] = size * Math.cos(Math.PI / 3 * i);
            points[i * 2 + 1] = size * Math.sin(Math.PI / 3 * i);
        }
        return points;
    }

    //Set a color of hex according to its system level
    private Color getColorBySystemLevel() {
        return switch (hex.getSystemLevel()) {
            case LEVEL_1 -> Color.LIGHTBLUE;
            case LEVEL_2 -> Color.ORANGE;
            case LEVEL_3 -> Color.GOLD;
            default -> Color.LIGHTGRAY;
        };
    }

}