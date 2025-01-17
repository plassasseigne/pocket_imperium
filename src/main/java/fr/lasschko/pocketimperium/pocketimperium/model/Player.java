package fr.lasschko.pocketimperium.pocketimperium.model;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Représente un joueur dans le jeu, incluant ses navires, ses flottes et ses informations personnelles.
 * Chaque joueur a un nom, un score, un nombre de navires, et des flottes qu'il contrôle.
 */
public class Player {
    /**
     * Le nom du joueur.
     */
    private final String name;

    /**
     * Le score actuel du joueur.
     */
    private int score;

    /**
     * La liste des navires contrôlés par le joueur.
     */
    private final List<Ship> ships;

    /**
     * La liste des flottes contrôlées par le joueur.
     */
    private final List<Fleet> fleets;

    /**
     * La couleur associée au joueur (utilisée pour les éléments graphiques).
     */
    private final Color color;

    /**
     * Le nombre de navires restants pour le joueur.
     */
    private int numOfShips;

    /**
     * L'ordre des commandes à exécuter par le joueur.
     */
    private List<String> commandOrder;

    /**
     * Constructeur pour initialiser un joueur avec son nom et sa couleur.
     *
     * @param name le nom du joueur
     * @param color la couleur du joueur
     */
    public Player(String name, Color color) {
        this.name = name;
        this.score = 0;
        this.numOfShips = 15;
        this.ships = new ArrayList<>();
        this.fleets = new ArrayList<>();
        this.color = color;
        this.commandOrder = new ArrayList<>();
    }

    /**
     * Renvoie le nom du joueur.
     *
     * @return le nom du joueur
     */
    public String getName() {
        return name;
    }

    /**
     * Renvoie le score actuel du joueur.
     *
     * @return le score du joueur
     */
    public int getScore() {
        return score;
    }

    /**
     * Définit le score du joueur.
     *
     * @param score le nouveau score du joueur
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Renvoie la liste des navires contrôlés par le joueur.
     *
     * @return la liste des navires
     */
    public List<Ship> getShips() {
        return ships;
    }

    /**
     * Renvoie la liste des flottes contrôlées par le joueur.
     *
     * @return la liste des flottes
     */
    public List<Fleet> getFleets() {
        return fleets;
    }

    /**
     * Renvoie l'ordre des commandes à exécuter par le joueur.
     *
     * @return la liste des commandes
     */
    public List<String> getCommandOrder() {
        return commandOrder;
    }

    /**
     * Définit l'ordre des commandes du joueur.
     *
     * @param commandOrder la liste des commandes à exécuter
     */
    public void setCommandOrder(List<String> commandOrder) {
        this.commandOrder = commandOrder;
    }

    /**
     * Renvoie la couleur associée au joueur.
     *
     * @return la couleur du joueur
     */
    public Color getColor() {
        return color;
    }

    /**
     * Ajoute un navire à la liste des navires du joueur et diminue le nombre de navires disponibles.
     *
     * @param ship le navire à ajouter
     */
    public void addShip(Ship ship) {
        ships.add(ship);
        numOfShips--;
    }

    /**
     * Retire un navire de la liste des navires du joueur et augmente le nombre de navires disponibles.
     *
     * @param ship le navire à retirer
     */
    public void removeShip(Ship ship) {
        ships.remove(ship);
        numOfShips++;
    }

    /**
     * Renvoie le nombre de navires restants pour le joueur.
     *
     * @return le nombre de navires restants
     */
    public int getNumOfShips() {
        return numOfShips;
    }

    /**
     * Renvoie un code de hachage unique basé sur le nom du joueur.
     *
     * @return le code de hachage du joueur
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Renvoie une représentation textuelle du joueur (son nom).
     *
     * @return le nom du joueur
     */
    public String toString() {
        return getName();
    }
}
