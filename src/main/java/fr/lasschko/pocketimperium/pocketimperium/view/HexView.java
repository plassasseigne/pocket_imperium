package fr.lasschko.pocketimperium.pocketimperium.view;

import fr.lasschko.pocketimperium.pocketimperium.model.Hex;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * Représente la vue d'un hexagone sur le panneau de jeu. Cette classe gère la création graphique de l'hexagone,
 * son positionnement, sa couleur selon le niveau du système, ainsi que les événements de survol et de clic.
 */
public class HexView {
    /**
     * L'hexagone associé à cette vue.
     */
    private final Hex hex;

    /**
     * Le panneau sur lequel l'hexagone est dessiné.
     */
    private final Pane pane;

    /**
     * Le polygone représentant l'hexagone sur le panneau.
     */
    private final Polygon polygon;

    /**
     * La couleur de l'hexagone selon son niveau de système.
     */
    private Color color;

    /**
     * Constructeur pour initialiser la vue d'un hexagone avec ses propriétés graphiques.
     *
     * @param hex l'hexagone à afficher.
     */
    public HexView(Hex hex) {
        this.hex = hex;
        pane = new Pane();

        polygon = new Polygon();
        polygon.getPoints().addAll(generateHexagonPoints(30));  // Génère les points du polygone hexagonal
        color = getColorBySystemLevel();  // Détermine la couleur de l'hexagone en fonction de son niveau de système
        polygon.setFill(color);  // Remplissage de l'hexagone avec la couleur
        polygon.setStroke(Color.BLACK);  // Bordure noire
        polygon.setSmooth(true);  // Lissage du polygone

        // Calcul des offsets pour centrer le polygone
        double offsetX = polygon.getBoundsInLocal().getWidth() / 2;
        double offsetY = polygon.getBoundsInLocal().getHeight() / 2;

        // Centrage du polygone sur le panneau
        polygon.setTranslateX(offsetX);
        polygon.setTranslateY(offsetY);

        // Effet de survol : épaissir le contour de l'hexagone au survol
        polygon.setOnMouseEntered(event -> polygon.setStrokeWidth(3));
        polygon.setOnMouseExited(event -> polygon.setStrokeWidth(1));  // Réinitialisation du contour à sa largeur initiale

        // Action de clic : affiche un message lorsque l'hexagone est cliqué
        polygon.setOnMouseClicked(event -> System.out.println("Hexagone cliqué : " + hex));

        pane.getChildren().add(polygon);
        pane.setLayoutX(hex.getX());
        pane.setLayoutY(hex.getY());
    }

    /**
     * Renvoie le panneau contenant l'hexagone.
     *
     * @return le panneau de la vue de l'hexagone.
     */
    public Pane getPane() {
        return pane;
    }

    /**
     * Renvoie l'hexagone associé à cette vue.
     *
     * @return l'hexagone.
     */
    public Hex getHex(){
        return hex;
    }

    /**
     * Renvoie le polygone représentant l'hexagone.
     *
     * @return le polygone représentant l'hexagone.
     */
    public Polygon getPolygon(){
        return polygon;
    }

    /**
     * Génère les points nécessaires pour dessiner un hexagone de la taille spécifiée.
     *
     * @param size la taille de l'hexagone.
     * @return un tableau contenant les points (x, y) de l'hexagone.
     */
    private static Double[] generateHexagonPoints(double size) {
        Double[] points = new Double[12];
        for (int i = 0; i < 6; i++) {
            points[i * 2] = size * Math.cos(Math.PI / 3 * i);  // Coordonnée x
            points[i * 2 + 1] = size * Math.sin(Math.PI / 3 * i);  // Coordonnée y
        }
        return points;
    }

    /**
     * Détermine la couleur de l'hexagone en fonction de son niveau de système.
     *
     * @return la couleur de l'hexagone.
     */
    private Color getColorBySystemLevel() {
        return switch (hex.getSystemLevel()) {
            case LEVEL_1 -> Color.LIGHTBLUE;  // Niveau 1 : bleu clair
            case LEVEL_2 -> Color.ORANGE;  // Niveau 2 : orange
            case LEVEL_3 -> Color.GOLD;  // Niveau 3 : or
            default -> Color.LIGHTGRAY;  // Niveau 0 : gris clair
        };
    }
}
