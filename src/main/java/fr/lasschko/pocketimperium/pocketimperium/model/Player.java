package fr.lasschko.pocketimperium.pocketimperium.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.scene.paint.Color;

public class Player {
    private final String name;
    private final int score;
    private final List<Ship> ships;
    private final List<Fleet> fleets;
    private final Color color;

    public Player(String name, Color color) {
        this.name = name;
        this.score = 0;
        this.ships = new ArrayList<>();
        this.fleets = new ArrayList<>();
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public List<Fleet> getFleets() {
        return fleets;
    }

    public Color getColor() {
        return color;
    }

    public void addShip(Ship ship) {
        ships.add(ship);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}