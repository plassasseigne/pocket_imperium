package fr.lasschko.pocketimperium.pocketimperium.model;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    private final Player owner;
    private Hex hex;
    private final List<Double> position = new ArrayList<Double>();

    public Ship(Player owner, Hex hex) {
        this.owner = owner;
        this.hex = hex;
        setPosition(hex.getX(), hex.getY());
    }

    public Player getOwner() {
        return owner;
    }

    public boolean isOwner(Player player){
        return getOwner().equals(player);
    }

    public Hex getHex() {
        return hex;
    }

    public void setHex(Hex hex) {
        this.hex = hex;
    }

    public List<Double> getPosition() {
        return position;
    }

    public void setPosition(double x, double y) {
        position.add(x);
        position.add(y);
    }

}


