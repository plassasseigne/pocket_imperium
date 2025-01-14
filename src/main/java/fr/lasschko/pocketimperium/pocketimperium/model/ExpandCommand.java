package fr.lasschko.pocketimperium.pocketimperium.model;

import fr.lasschko.pocketimperium.pocketimperium.view.HexView;
import fr.lasschko.pocketimperium.pocketimperium.view.ShipView;
import javafx.scene.layout.Pane;

public class ExpandCommand {
    private final int amount;

    public ExpandCommand(int amount) {
        this.amount = amount;
    }
    public void execute(Pane layout, Player player, HexView hexView) {
        for(int i =0; i<this.amount; i++){
            Ship ship = new Ship(player, hexView.getHex());
            player.addShip(ship);
            ShipView shipView = new ShipView(ship, hexView);
            shipView.display();
            layout.getChildren().add(shipView.getBody());
            hexView.getHex().setControlledBy(player);
            hexView.getHex().getSector().setIsInitialDeployed(true);
        }
    }
}
