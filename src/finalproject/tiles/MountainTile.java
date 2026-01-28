package finalproject.tiles;

import finalproject.system.Tile;
import finalproject.system.TileType;

public class MountainTile extends Tile {
    public MountainTile() {
        this.type = TileType.Moutain;
        this.distanceCost = 100;
        this.timeCost = 100;
        this.damageCost = 100;
    }
}
