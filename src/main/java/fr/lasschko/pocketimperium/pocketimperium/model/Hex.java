package fr.lasschko.pocketimperium.pocketimperium.model;

import fr.lasschko.pocketimperium.pocketimperium.controller.GameBoardController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Hex {
    private final int id;
    private final SystemLevel systemLevel;
    private final double x;
    private final double y;
    private final Sector sector;
    private final List<Ship> ships;
    private String type;
    private List<Fleet> fleets;
    private Player controlledBy;

    public Hex(int id, double x, double y, SystemLevel systemLevel, Sector sector) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.ships = new ArrayList<>();
        this.type = x == 0 && y == 0 ? "Central" : "Side";
        this.systemLevel = systemLevel;
        this.sector = sector;
    }

    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public SystemLevel getSystemLevel() {
        return systemLevel;
    }

    public Sector getSector() {
        return sector;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void addFleet(Fleet fleet) {
    }

    public void addShip(Ship ship) {
        ships.add(ship);
    }

    public void removeShip(Ship ship) {
        ships.remove(ship);
    }

    public int getShipCount(Player player, GameBoardController gameBoardController) {
        return (int) gameBoardController.getShipViews().stream()
                .filter(shipView -> shipView.getShip().getOwner().equals(player) && shipView.getHex().equals(this))
                .count();
    }

    public List<Ship> getShips() {
        return ships;
    }


    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hex hex = (Hex) o;
        return id == hex.id;
    }

    public Player getControlledBy() {
        return controlledBy;
    }

    public boolean isControlledBy(Player player) {
        return controlledBy != null && controlledBy.equals(player);
    }

    public boolean isControlledBy() {
        return controlledBy != null;
    }

    public void setControlledBy(Player player) {
        this.controlledBy = player;
    }

    public String toString() {
        return id + ":{ \"x\": " + x + ", \"y\": " + y + "},";
    }
}