package finalproject.tiles;

import finalproject.system.Tile;
import finalproject.system.TileType;

public class MetroTile extends Tile {
    public double metroTimeCost = 100;
    public double metroDistanceCost = 100;
    public double metroCommuteFactor = 0.2;

    public MetroTile() {
        this.type = TileType.Metro;
        this.distanceCost = 1;
        this.timeCost = 1;
        this.damageCost = 2;
    }

    // Updates the distance and time cost differently between metro tiles
    public void fixMetro(Tile node) {

        // Check if the other tile is also a metro station
        if (node instanceof MetroTile) {
            MetroTile otherMetro = (MetroTile) node;

            // Calculate Manhattan distance between stations
            int manhattanDistance = Math.abs(this.xCoord - otherMetro.xCoord) +
                    Math.abs(this.yCoord - otherMetro.yCoord);

            // Calculate metro-specific costs using the formulas given
            this.metroTimeCost = manhattanDistance * this.metroCommuteFactor;
            this.metroDistanceCost = manhattanDistance / this.metroCommuteFactor;

            // Also set the other metro's costs : same
            otherMetro.metroTimeCost = this.metroTimeCost;
            otherMetro.metroDistanceCost = this.metroDistanceCost;
        }
    }

    // Helper method to check if this is a MetroTile
    public boolean isMetro() {
        return this.type == TileType.Metro;
    }

    // Get metro time cost (for FastestPath)
    public double getMetroTimeCost() {
        return this.metroTimeCost;
    }

    // Get metro distance cost (for ShortestPath)
    public double getMetroDistanceCost() {
        return this.metroDistanceCost;
    }
}