package fr.lasschko.pocketimperium.pocketimperium.view;

import fr.lasschko.pocketimperium.pocketimperium.model.Hex;
import fr.lasschko.pocketimperium.pocketimperium.model.Ship;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Représente la vue d'un vaisseau dans l'interface utilisateur. Cette classe permet d'afficher un vaisseau sur
 * un hexagone dans la vue en utilisant un cercle.
 */
public class ShipView {
    /**
     * Le corps du vaisseau (représenté sous forme de cercle).
     */
    private final Circle body;

    /**
     * Le vaisseau associé à cette vue.
     */
    private final Ship ship;

    /**
     * Coordonnée X du vaisseau dans le panneau.
     */
    private final double pane_x;

    /**
     * Coordonnée Y du vaisseau dans le panneau.
     */
    private final double pane_y;

    /**
     * Constructeur pour initialiser la vue d'un vaisseau.
     *
     * @param ship le vaisseau à afficher.
     * @param hexView la vue de l'hexagone où le vaisseau sera affiché.
     */
    public ShipView(Ship ship, HexView hexView) {
        this.ship = ship;
        this.pane_x = hexView.getPane().getWidth() / 2;
        this.pane_y = hexView.getPane().getHeight() / 2;
        this.body = new Circle(10, ship.getOwner().getColor());  // Le vaisseau est représenté par un cercle
        body.setStroke(Color.BLACK);  // Contour noir pour le cercle
        body.setStrokeWidth(1);  // Largeur du contour
    }

    /**
     * Renvoie le corps du vaisseau.
     *
     * @return le corps du vaisseau (cercle).
     */
    public Circle getBody() {
        return body;
    }

    /**
     * Renvoie le vaisseau associé à cette vue.
     *
     * @return le vaisseau.
     */
    public Ship getShip() {
        return ship;
    }

    /**
     * Renvoie l'hexagone où se trouve le vaisseau.
     *
     * @return l'hexagone du vaisseau.
     */
    public Hex getHex() {
        return ship.getHex();
    }

    /**
     * Sélectionne le vaisseau en augmentant l'épaisseur de son contour.
     */
    public void select() {
        this.body.setStrokeWidth(2);
    }

    /**
     * Désélectionne le vaisseau en réinitialisant l'épaisseur de son contour.
     */
    public void deselect() {
        this.body.setStrokeWidth(1);
    }

    /**
     * Affiche le vaisseau en fonction de ses coordonnées dans l'interface utilisateur.
     * La position du vaisseau est ajustée de manière aléatoire pour créer un léger décalage visuel.
     */
    public void display() {
        double coef = Math.random() * 20;  // Décalage aléatoire pour un affichage dynamique
        double x = ship.getPosition().get(0) + pane_x - 10 + coef;  // Calcul de la position X
        double y = ship.getPosition().get(1) + pane_y - 10 + coef;  // Calcul de la position Y
        body.setLayoutX(x);  // Positionnement du vaisseau sur le panneau
        body.setLayoutY(y);  // Positionnement du vaisseau sur le panneau
    }

    /**
     * Affiche le vaisseau en fonction de la position d'un hexagone spécifique.
     * La position du vaisseau est ajustée de manière aléatoire pour un affichage dynamique.
     *
     * @param hex l'hexagone où afficher le vaisseau.
     */
    public void display(Hex hex) {
        double coef = Math.random() * 20;  // Décalage aléatoire pour un affichage dynamique
        double x = hex.getX() + pane_x - 10 + coef;  // Calcul de la position X à partir de l'hexagone
        double y = hex.getY() + pane_y - 10 + coef;  // Calcul de la position Y à partir de l'hexagone
        body.setLayoutX(x);  // Positionnement du vaisseau sur le panneau
        body.setLayoutY(y);  // Positionnement du vaisseau sur le panneau
    }
}
