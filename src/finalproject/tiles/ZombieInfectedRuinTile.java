package finalproject.tiles;

import finalproject.system.Tile;
import finalproject.system.TileType;

public class ZombieInfectedRuinTile extends Tile {
    public ZombieInfectedRuinTile() {
        this.type = TileType.ZombieInfectedRuin;
        this.distanceCost = 1;
        this.timeCost = 3;
        this.damageCost = 5;
    }
}
