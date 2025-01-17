package fr.lasschko.pocketimperium.pocketimperium.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Représente un secteur sur la carte du jeu, contenant un ensemble de hexagones.
 * Chaque secteur a un identifiant, un côté (ou une orientation), et un ensemble de hexagones.
 * Il peut également être marqué comme scoré ou déployé initialement.
 */
public class Sector {
    /**
     * L'identifiant unique du secteur.
     */
    private final int id;

    /**
     * Le côté (ou orientation) du secteur, représenté par une chaîne de caractères.
     */
    private final String side;

    /**
     * La liste des hexagones associés à ce secteur.
     */
    private List<Hex> hexes;

    /**
     * Indique si le secteur a été scoré ou non.
     */
    private boolean isScored;

    /**
     * Indique si le secteur a été déployé initialement.
     */
    private boolean isInitialDeployed;

    /**
     * Constructeur pour initialiser un secteur avec un identifiant, des hexagones et un côté.
     *
     * @param id l'identifiant du secteur
     * @param hexes la liste des hexagones du secteur
     * @param side le côté (ou orientation) du secteur
     */
    public Sector(int id, List<Hex> hexes, String side) {
        this.id = id;
        this.hexes = hexes;
        this.side = side;
        this.isScored = false;
        this.isInitialDeployed = false;
        resetIsScored();
    }

    /**
     * Renvoie l'identifiant du secteur.
     *
     * @return l'identifiant du secteur
     */
    public int getId() {
        return id;
    }

    /**
     * Renvoie le côté du secteur.
     *
     * @return le côté du secteur
     */
    public String getSide() {
        return side;
    }

    /**
     * Renvoie le côté du secteur sous forme de liste de chaînes.
     *
     * @return la liste des éléments du côté du secteur
     */
    public List<String> getSideAsList() {
        return Arrays.asList(side.split(" "));
    }

    /**
     * Renvoie la liste des hexagones associés à ce secteur.
     *
     * @return la liste des hexagones du secteur
     */
    public List<Hex> getHexes() {
        return hexes;
    }

    /**
     * Définit les hexagones associés à ce secteur.
     *
     * @param hexes la nouvelle liste des hexagones du secteur
     */
    public void setHexes(List<Hex> hexes) {
        this.hexes = hexes;
    }

    /**
     * Vérifie si le secteur est un secteur central (c'est-à-dire, un secteur avec le côté "CENTRAL").
     *
     * @return true si le secteur est central, sinon false
     */
    public boolean IsTriPrime() {
        return Objects.equals(this.side, "CENTRAL");
    }

    /**
     * Renvoie si le secteur a été scoré.
     *
     * @return true si le secteur est scoré, sinon false
     */
    public boolean getIsScored() {
        return isScored;
    }

    /**
     * Définit si le secteur a été scoré.
     *
     * @param isScored true si le secteur doit être scoré, sinon false
     */
    public void setIsScored(boolean isScored) {
        this.isScored = isScored;
    }

    /**
     * Réinitialise l'état de scoré du secteur à false.
     */
    public void resetIsScored() {
        isScored = false;
    }

    /**
     * Renvoie si le secteur a été déployé initialement.
     *
     * @return true si le secteur a été déployé initialement, sinon false
     */
    public boolean isInitialDeployed() {
        return isInitialDeployed;
    }

    /**
     * Définit si le secteur a été déployé initialement.
     *
     * @param isInitialDeployed true si le secteur a été déployé initialement, sinon false
     */
    public void setIsInitialDeployed(boolean isInitialDeployed) {
        this.isInitialDeployed = isInitialDeployed;
    }

    /**
     * Renvoie une représentation textuelle du secteur, y compris l'identifiant et les hexagones associés.
     *
     * @return une chaîne représentant le secteur
     */
    public String toString() {
        StringBuilder hexesString = new StringBuilder();
        for (Hex hex : hexes) {
            hexesString.append("\n  ").append(hex);
        }
        return " \"Sector " + id + "\":" + "\n{" + hexesString + "\n},";
    }

}
