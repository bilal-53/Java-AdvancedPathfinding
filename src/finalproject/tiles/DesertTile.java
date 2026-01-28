package finalproject.tiles;

import finalproject.system.Tile;
import finalproject.system.TileType;

public class DesertTile extends Tile {
    public DesertTile() {
        this.type = TileType.Desert;
        this.distanceCost = 2;
        this.timeCost = 6;
        this.damageCost = 3;
    }
}