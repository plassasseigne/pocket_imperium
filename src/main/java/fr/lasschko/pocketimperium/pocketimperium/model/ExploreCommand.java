package fr.lasschko.pocketimperium.pocketimperium.model;

import fr.lasschko.pocketimperium.pocketimperium.view.HexView;
import fr.lasschko.pocketimperium.pocketimperium.view.ShipView;

/**
 * Représente une commande pour explorer de nouveaux hexagones dans le jeu.
 */
public class ExploreCommand {

    /**
     * Le graphe des hexagones utilisé pour calculer les déplacements.
     */
    private final HexesGraph graph;

    /**
     * Initialise une nouvelle commande d'exploration.
     *
     * @param game L'instance du jeu contenant le graphe des hexagones.
     */
    public ExploreCommand(Game game) {
        this.graph = game.getHexesGraph();
    }

    /**
     * Exécute l'action d'exploration en déplaçant un vaisseau sélectionné vers un hexagone cible,
     * si les conditions de déplacement sont respectées.
     *
     * @param selectedShipView La vue du vaisseau sélectionné.
     * @param targetHexView La vue de l'hexagone cible.
     */
    public void execute(ShipView selectedShipView, HexView targetHexView) {
        // Calcule le nombre de déplacements nécessaires pour atteindre l'hexagone cible
        int moveCount = graph.getMoveCount(selectedShipView.getHex(), targetHexView.getHex());

        // Vérifie si le déplacement est valide (entre 0 et 2 inclus)
        if (moveCount >= 0 && moveCount <= 2) {
            // Retire le vaisseau de l'hexagone actuel
            selectedShipView.getHex().removeShip(selectedShipView.getShip());
            if (selectedShipView.getHex().getShips().isEmpty()) {
                selectedShipView.getHex().setControlledBy(null);
            }

            // Ajoute le vaisseau à l'hexagone cible et met à jour le contrôle
            targetHexView.getHex().setControlledBy(selectedShipView.getShip().getOwner());
            targetHexView.getHex().addShip(selectedShipView.getShip());

            // Met à jour l'affichage de la vue du vaisseau
            selectedShipView.display(targetHexView.getHex());

            // Met à jour la position du vaisseau dans le modèle
            selectedShipView.getShip().setHex(targetHexView.getHex());
        }
    }
}
