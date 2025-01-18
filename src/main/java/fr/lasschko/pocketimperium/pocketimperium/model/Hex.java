package fr.lasschko.pocketimperium.pocketimperium.model;

import fr.lasschko.pocketimperium.pocketimperium.controller.GameBoardController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Représente un hexagone du plateau de jeu dans "Pocket Imperium".
 * Chaque hexagone possède un identifiant unique, des coordonnées (x, y),
 * un niveau de système (défini par {@link SystemLevel}), et est rattaché
 * à un {@link Sector}.
 * <p>
 * Un hexagone peut contenir des vaisseaux ({@link Ship}) et être contrôlé
 * par un joueur. Les hexagones peuvent être de type "Central" (si x = 0
 * et y = 0) ou "Side" dans le cas contraire.
 * </p>
 *
 * @author Paul et Yevhenii
 * @version 1.0
 */
public class Hex {

    /**
     * Identifiant unique de l'hexagone.
     */
    private final int id;

    /**
     * Niveau du système auquel appartient cet hexagone
     * (par exemple, LOW, MEDIUM, HIGH).
     */
    private final SystemLevel systemLevel;

    /**
     * Coordonnée X de l'hexagone sur le plateau.
     */
    private final double x;

    /**
     * Coordonnée Y de l'hexagone sur le plateau.
     */
    private final double y;

    /**
     * Secteur auquel appartient cet hexagone.
     */
    private final Sector sector;

    /**
     * Liste des vaisseaux ({@link Ship}) présents sur cet hexagone.
     */
    private final List<Ship> ships;

    /**
     * Type de l'hexagone, pouvant être "Central" ou "Side"
     * (selon les coordonnées x et y).
     */
    private String type;

    /**
     * Liste des flottes ({@link Fleet}) présentes sur cet hexagone.
     */
    private List<Fleet> fleets;

    /**
     * Joueur qui contrôle actuellement cet hexagone.
     * Peut-être {@code null} si aucun joueur n'a le contrôle.
     */
    private Player controlledBy;

    /**
     * Construit un nouvel hexagone avec les informations spécifiées.
     * <p>
     * Le type est défini automatiquement :
     * s'il s'agit des coordonnées (0,0), le type sera "Central".
     * Sinon, il sera "Side".
     * </p>
     *
     * @param id L'identifiant unique de l'hexagone
     * @param x La coordonnée X de l'hexagone
     * @param y La coordonnée Y de l'hexagone
     * @param systemLevel Le niveau du système (ex : LOW, MEDIUM, HIGH)
     * @param sector Le secteur auquel appartient l'hexagone
     */
    public Hex(int id, double x, double y, SystemLevel systemLevel, Sector sector) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.ships = new ArrayList<>();
        this.type = (x == 0 && y == 0) ? "Central" : "Side";
        this.systemLevel = systemLevel;
        this.sector = sector;
    }

    /**
     * Retourne l'identifiant unique de cet hexagone.
     *
     * @return L'ID de l'hexagone
     */
    public int getId() {
        return id;
    }

    /**
     * Retourne la coordonnée X de cet hexagone.
     *
     * @return La coordonnée X
     */
    public double getX() {
        return x;
    }

    /**
     * Retourne la coordonnée Y de cet hexagone.
     *
     * @return La coordonnée Y
     */
    public double getY() {
        return y;
    }

    /**
     * Retourne le niveau du système de cet hexagone
     * (par exemple, {@link SystemLevel}).
     *
     * @return Le niveau du système
     */
    public SystemLevel getSystemLevel() {
        return systemLevel;
    }

    /**
     * Retourne le secteur auquel cet hexagone est rattaché.
     *
     * @return Le secteur de l'hexagone
     */
    public Sector getSector() {
        return sector;
    }

    /**
     * Modifie le type de l'hexagone (par exemple, "Central", "Side", etc.).
     *
     * @param type Le nouveau type à assigner
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Ajoute une flotte à la liste de flottes présentes sur cet hexagone.
     * (pas développée)
     *
     * @param fleet La flotte à ajouter
     */
    public void addFleet(Fleet fleet) { }

    /**
     * Ajoute un vaisseau ({@link Ship}) à la liste de vaisseaux présents
     * sur cet hexagone.
     *
     * @param ship Le vaisseau à ajouter
     */
    public void addShip(Ship ship) {
        ships.add(ship);
    }

    /**
     * Retire un vaisseau ({@link Ship}) de la liste de vaisseaux présents
     * sur cet hexagone.
     *
     * @param ship Le vaisseau à retirer
     */
    public void removeShip(Ship ship) {
        ships.remove(ship);
    }

    /**
     * Calcule le nombre de vaisseaux d'un joueur donné sur cet hexagone,
     * en se basant sur les vues de vaisseaux présentes dans le
     * {@link GameBoardController}.
     *
     * @param player Le joueur pour lequel on souhaite compter les vaisseaux
     * @param gameBoardController Le contrôleur du plateau de jeu, contenant la liste
     *                            des ShipView représentées
     * @return Le nombre de vaisseaux appartenant au joueur sur cet hexagone
     */
    public int getShipCount(Player player, GameBoardController gameBoardController) {
        return (int) gameBoardController.getShipViews().stream()
                .filter(shipView -> shipView.getShip().getOwner().equals(player)
                        && shipView.getHex().equals(this))
                .count();
    }

    /**
     * Retourne la liste de tous les vaisseaux ({@link Ship}) présents
     * sur cet hexagone.
     *
     * @return La liste des vaisseaux
     */
    public List<Ship> getShips() {
        return ships;
    }

    /**
     * Calcule le hashCode de l'hexagone, basé sur son ID unique.
     *
     * @return Le hashCode de cet objet
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Détermine l'égalité entre cet hexagone et un autre objet.
     * Deux hexagones sont considérés égaux s'ils partagent
     * le même ID.
     *
     * @param o L'objet à comparer
     * @return {@code true} si les deux objets sont égaux, {@code false} sinon
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hex hex = (Hex) o;
        return id == hex.id;
    }

    /**
     * Retourne le joueur qui contrôle actuellement cet hexagone,
     * ou {@code null} si aucun joueur n'en a le contrôle.
     *
     * @return Le joueur contrôlant l'hexagone, ou {@code null}
     */
    public Player getControlledBy() {
        return controlledBy;
    }

    /**
     * Vérifie si cet hexagone est contrôlé par un joueur donné.
     *
     * @param player Le joueur à vérifier
     * @return {@code true} si l'hexagone est contrôlé par ce joueur,
     *         {@code false} sinon
     */
    public boolean isControlledBy(Player player) {
        return controlledBy != null && controlledBy.equals(player);
    }

    /**
     * Vérifie si cet hexagone est contrôlé par un quelconque joueur.
     *
     * @return {@code true} si l'hexagone est contrôlé par un joueur,
     *         {@code false} sinon
     */
    public boolean isControlledBy() {
        return controlledBy != null;
    }

    /**
     * Définit le joueur qui prend le contrôle de cet hexagone.
     *
     * @param player Le joueur qui prend le contrôle
     */
    public void setControlledBy(Player player) {
        this.controlledBy = player;
    }

    /**
     * Retourne une représentation textuelle de cet hexagone,
     * incluant son ID et ses coordonnées (x, y).
     *
     * @return Une chaîne de caractères décrivant l'hexagone
     */
    @Override
    public String toString() {
        return id + ":{ \"x\": " + x + ", \"y\": " + y + "},";
    }
}