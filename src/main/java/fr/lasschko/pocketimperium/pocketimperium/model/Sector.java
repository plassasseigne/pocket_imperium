package fr.lasschko.pocketimperium.pocketimperium.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Sector {
    private final int id;
    private final String side;
    private List<Hex> hexes;
    private boolean isScored;
    private boolean isInitialDeployed;

    public Sector(int id, List<Hex> hexes, String side) {
        this.id = id;
        this.hexes = hexes;
        this.side = side;
        this.isScored = false;
        this.isInitialDeployed = false;
        resetIsScored();
    }

    public int getId() {
        return id;
    }

    public String getSide() {
        return side;
    }

    public List<String> getSideAsList() {
        return Arrays.asList(side.split(" "));
    }

    public List<Hex> getHexes() {
        return hexes;
    }

    public void setHexes(List<Hex> hexes) {
        this.hexes = hexes;
    }

    public boolean IsTriPrime() {
        return Objects.equals(this.side, "CENTRAL");
    }

    public boolean getIsScored() {
        return isScored;
    }
    public void setIsScored(boolean isScored) {
        this.isScored = isScored;
    }
    public void resetIsScored() {
        isScored = false;
    }

    public boolean isInitialDeployed() {
        return isInitialDeployed;
    }

    public void setIsInitialDeployed(boolean isInitialDeployed) {
        this.isInitialDeployed = isInitialDeployed;
    }

    public String toString() {
        StringBuilder hexesString = new StringBuilder();
        for (Hex hex : hexes) {
            hexesString.append("\n  ").append(hex);
        }
        return " \"Sector " + id + "\":" + "\n{" + hexesString + "\n},";
    }

}