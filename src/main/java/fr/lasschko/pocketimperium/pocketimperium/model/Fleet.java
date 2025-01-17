package fr.lasschko.pocketimperium.pocketimperium.model;

import java.util.List;

/**
 * Représente une flotte de vaisseaux appartenant à un joueur dans le jeu.
 * Une flotte est située dans un hexagone spécifique sur la carte du jeu
 * et se compose de plusieurs vaisseaux.
 */
public class Fleet {
    /**
     * Le joueur qui possède cette flotte.
     */
    private Player owner;

    /**
     * La liste des vaisseaux qui composent cette flotte.
     */
    private List<Ship> ships;

    /**
     * L'emplacement actuel de la flotte sur la carte du jeu.
     */
    private Hex currentHex;

    /**
     * Construit une flotte avec un propriétaire, une liste de vaisseaux et une position actuelle.
     *
     * @param owner      le joueur propriétaire de cette flotte
     * @param ships      la liste des vaisseaux dans cette flotte
     * @param currentHex l'emplacement actuel de la flotte sur la carte
     */
    public Fleet(Player owner, List<Ship> ships, Hex currentHex) {
        this.owner = owner;
        this.ships = ships;
        this.currentHex = currentHex;
    }

    /**
     * Renvoie le propriétaire de la flotte.
     *
     * @return le joueur propriétaire de cette flotte
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Renvoie la liste des vaisseaux de la flotte.
     *
     * @return la liste des vaisseaux
     */
    public List<Ship> getShips() {
        return ships;
    }

    /**
     * Renvoie l'emplacement actuel de la flotte sur la carte du jeu.
     *
     * @return l'hexagone où la flotte est actuellement située
     */
    public Hex getCurrentHex() {
        return currentHex;
    }
}
