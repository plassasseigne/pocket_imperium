package fr.lasschko.pocketimperium.pocketimperium.view;

import fr.lasschko.pocketimperium.pocketimperium.model.Hex;
import fr.lasschko.pocketimperium.pocketimperium.model.Sector;

import java.util.ArrayList;
import java.util.List;

public class SectorView{
    private final Sector sector;


    public SectorView(Sector sector) {
        this.sector = sector;
    }

    public Sector getSector() {
        return sector;
    }

    //transform Hex to HexView for our UI
    public List<HexView> addHexes(){
        List<HexView> hexViews = new ArrayList<>();
        for(Hex hex: sector.getHexes()){
            HexView hexView = new HexView(hex);
            hexViews.add(hexView);
        }
        return hexViews;
    }
}

