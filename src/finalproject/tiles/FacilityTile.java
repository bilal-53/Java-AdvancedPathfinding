package finalproject.tiles;

import finalproject.system.Tile;
import finalproject.system.TileType;

public class FacilityTile extends Tile {
    public FacilityTile() {
        this.type = TileType.Facility;
        this.distanceCost = 1;
        this.timeCost = 2;
        this.damageCost = 0;
    }
}
