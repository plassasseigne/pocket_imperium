package fr.lasschko.pocketimperium.pocketimperium.model;

public class Ship {
    private final Player owner;
    private int position;

    public Ship(Player owner, Hex hex) {
        this.owner = owner;
        this.position = hex.getId();
    }

    public Player getOwner() {
        return owner;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void detroy() {
    }
}


