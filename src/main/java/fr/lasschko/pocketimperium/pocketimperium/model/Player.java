package fr.lasschko.pocketimperium.pocketimperium.model;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {
    private final String name;
    private int score;
    private final List<Ship> ships;
    private final List<Fleet> fleets;
    private final Color color;
    private int numOfShips;
    private List<String> commandOrder;

    public Player(String name, Color color) {
        this.name = name;
        this.score = 0;
        this.numOfShips = 15;
        this.ships = new ArrayList<>();
        this.fleets = new ArrayList<>();
        this.color = color;
        this.commandOrder = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public List<Fleet> getFleets() {
        return fleets;
    }

    public List<String> getCommandOrder() {
        return commandOrder;
    }

    public void setCommandOrder(List<String> commandOrder) {
        this.commandOrder = commandOrder;
    }

    public Color getColor() {
        return color;
    }

    public void addShip(Ship ship) {
        ships.add(ship);
        numOfShips--;
    }

    public void removeShip(Ship ship) {
        ships.remove(ship);
        numOfShips++;
    }

    public int getNumOfShips() {
        return numOfShips;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String toString() {
        return getName();
    }
}