package fr.lasschko.pocketimperium.pocketimperium.view;

import fr.lasschko.pocketimperium.pocketimperium.model.Hex;
import fr.lasschko.pocketimperium.pocketimperium.model.Ship;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ShipView {
    private final Circle body;
    private final Ship ship;
    private final double pane_x;
    private final double pane_y;

    public ShipView(Ship ship, HexView hexView) {
        this.ship = ship;
        pane_x = hexView.getPane().getWidth() / 2;
        pane_y = hexView.getPane().getHeight() / 2;
        body = new Circle(10, ship.getOwner().getColor());
        body.setStroke(Color.BLACK);
        body.setStrokeWidth(1);
    }

    public Circle getBody() {
        return body;
    }

    public Ship getShip() {
        return ship;
    }

    public Hex getHex(){
        return ship.getHex();
    }

    public void select(){
        this.body.setStrokeWidth(2);
    }

    public void deselect(){
        this.body.setStrokeWidth(1);
    }

    public void display() {
        double coef = Math.random() * 20;
        double x = ship.getPosition().getFirst() + pane_x-10 + coef;
        double y = ship.getPosition().getLast() + pane_y-10 + coef;
        body.setLayoutX(x);
        body.setLayoutY(y);
    }

    public void display(Hex hex) {
        double coef = Math.random() * 20;
        double x = hex.getX() + pane_x-10 + coef;
        double y = hex.getY() + pane_y-10 + coef;
        body.setLayoutX(x);
        body.setLayoutY(y);
    }
}
