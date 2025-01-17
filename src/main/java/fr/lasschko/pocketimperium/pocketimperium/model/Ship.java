package fr.lasschko.pocketimperium.pocketimperium.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un vaisseau appartenant à un joueur. Un vaisseau est associé à un hexagone et possède une position
 * dans le plan de jeu.
 */
public class Ship {
    /**
     * Le propriétaire du vaisseau.
     */
    private final Player owner;

    /**
     * L'hexagone dans lequel se trouve le vaisseau.
     */
    private Hex hex;

    /**
     * La position du vaisseau sous forme de liste de coordonnées [x, y].
     */
    private final List<Double> position = new ArrayList<Double>();

    /**
     * Constructeur pour initialiser un vaisseau avec un propriétaire et un hexagone.
     * Le vaisseau prend la position de l'hexagone fourni.
     *
     * @param owner le joueur propriétaire du vaisseau
     * @param hex l'hexagone dans lequel le vaisseau est placé
     */
    public Ship(Player owner, Hex hex) {
        this.owner = owner;
        this.hex = hex;
        setPosition(hex.getX(), hex.getY());
    }

    /**
     * Renvoie le propriétaire du vaisseau.
     *
     * @return le joueur propriétaire du vaisseau
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Vérifie si le vaisseau appartient au joueur spécifié.
     *
     * @param player le joueur à vérifier
     * @return true si le vaisseau appartient au joueur, sinon false
     */
    public boolean isOwner(Player player){
        return getOwner().equals(player);
    }

    /**
     * Renvoie l'hexagone dans lequel se trouve le vaisseau.
     *
     * @return l'hexagone contenant le vaisseau
     */
    public Hex getHex() {
        return hex;
    }

    /**
     * Définit l'hexagone dans lequel le vaisseau doit se déplacer.
     *
     * @param hex le nouvel hexagone dans lequel le vaisseau doit se déplacer
     */
    public void setHex(Hex hex) {
        this.hex = hex;
    }

    /**
     * Renvoie la position du vaisseau sous forme de liste de coordonnées [x, y].
     *
     * @return la position du vaisseau
     */
    public List<Double> getPosition() {
        return position;
    }

    /**
     * Définit la position du vaisseau à l'aide des coordonnées x et y.
     *
     * @param x la coordonnée x de la position
     * @param y la coordonnée y de la position
     */
    public void setPosition(double x, double y) {
        position.add(x);
        position.add(y);
    }

}
