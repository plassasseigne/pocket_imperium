package fr.lasschko.pocketimperium.pocketimperium.model;

import java.util.List;

public class Fleet {
    private Player owner;
    private List<Ship> ships;
    private Hex currentHex;

    public Fleet(Player owner, List<Ship> ships, Hex currentHex) {
        this.owner = owner;
        this.ships = ships;
        this.currentHex = currentHex;
    }

    public Player getOwner() {
        return owner;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public Hex getCurrentHex() {
        return currentHex;
    }
}
