package fr.lasschko.pocketimperium.pocketimperium.view;

import fr.lasschko.pocketimperium.pocketimperium.model.Hex;
import fr.lasschko.pocketimperium.pocketimperium.model.Sector;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente la vue d'un secteur dans l'interface utilisateur. Cette classe permet de transformer les hexagones
 * d'un secteur en vues d'hexagones (HexView) pour l'affichage.
 */
public class SectorView {
    /**
     * Le secteur associé à cette vue.
     */
    private final Sector sector;

    /**
     * Constructeur pour initialiser la vue d'un secteur.
     *
     * @param sector le secteur à afficher.
     */
    public SectorView(Sector sector) {
        this.sector = sector;
    }

    /**
     * Renvoie le secteur associé à cette vue.
     *
     * @return le secteur.
     */
    public Sector getSector() {
        return sector;
    }

    /**
     * Transforme les hexagones d'un secteur en vues d'hexagones (HexView) pour l'interface utilisateur.
     *
     * @return une liste de vues d'hexagones (HexView) représentant les hexagones du secteur.
     */
    public List<HexView> addHexes() {
        List<HexView> hexViews = new ArrayList<>();
        // Parcours des hexagones du secteur et création de la vue correspondante
        for (Hex hex : sector.getHexes()) {
            HexView hexView = new HexView(hex);  // Crée une vue pour chaque hexagone
            hexViews.add(hexView);
        }
        return hexViews;
    }
}
