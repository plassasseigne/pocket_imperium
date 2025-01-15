package fr.lasschko.pocketimperium.pocketimperium.model;

import fr.lasschko.pocketimperium.pocketimperium.view.HexView;
import fr.lasschko.pocketimperium.pocketimperium.view.ShipView;

public class ExploreCommand {
    private final HexesGraph graph;
    public ExploreCommand(Game game) {
        this.graph = game.getHexesGraph();
    }

    public void execute(ShipView selectedShipView, HexView targetHexView) {
        int moveCount = graph.getMoveCount(selectedShipView.getHex(), targetHexView.getHex());
        if (moveCount >= 0 && moveCount <= 2) {
            selectedShipView.display(targetHexView.getHex());
            selectedShipView.getShip().setHex(targetHexView.getHex());
        }
    }
}
