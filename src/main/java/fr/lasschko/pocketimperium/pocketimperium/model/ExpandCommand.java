package fr.lasschko.pocketimperium.pocketimperium.model;

import fr.lasschko.pocketimperium.pocketimperium.controller.GameBoardController;
import fr.lasschko.pocketimperium.pocketimperium.view.HexView;
import fr.lasschko.pocketimperium.pocketimperium.view.ShipView;

/**
 * Représente une commande pour exécuter l'action d'expansion dans le jeu.
 */
public class ExpandCommand {

        /**
         * Exécute l'action d'expansion en ajoutant un nouveau vaisseau pour un joueur
         * sur un hexagone spécifique et en mettant à jour les vues correspondantes.
         *
         * @param gameBoardView Le contrôleur de la table de jeu.
         * @param player Le joueur qui effectue l'expansion.
         * @param hexView La vue de l'hexagone où l'expansion a lieu.
         */
        public void execute(GameBoardController gameBoardView, Player player, HexView hexView) {
                // Crée un nouveau vaisseau pour le joueur sur l'hexagone spécifié
                Ship ship = new Ship(player, hexView.getHex());
                player.addShip(ship);

                // Crée et ajoute la vue du vaisseau
                ShipView shipView = new ShipView(ship, hexView);
                gameBoardView.addShipView(shipView);
                shipView.display();

                // Ajoute la vue du vaisseau à la disposition principale
                gameBoardView.getRootLayout().getChildren().add(shipView.getBody());

                // Met à jour l'état de l'hexagone et du secteur correspondant
                hexView.getHex().setControlledBy(player);
                hexView.getHex().addShip(ship);
                hexView.getHex().getSector().setIsInitialDeployed(true);
        }
}
