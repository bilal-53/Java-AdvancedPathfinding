package finalproject;

import finalproject.system.Tile;
import java.util.ArrayList;

public class FastestPath extends PathFindingService {
    //minimizes travel time
    public FastestPath(Tile start) {
        super(start);
        generateGraph(); // Create time-weighted map for escape planning
    }

    @Override
    public void generateGraph() {
        // Find all reachable locations (avoid mountains and obstacles)
        ArrayList<Tile> reachableLocations = GraphTraversal.BFS(source);

        // Create graph for pathfinding weighted by time this time
        g = new Graph(reachableLocations);

        // Connect adjacent locations with time travel costs
        for (Tile location : reachableLocations) {
            for (Tile neighbor : location.adjacentTiles) {
                // Only create paths through traversable terrain
                if (neighbor.isWalkable() && reachableLocations.contains(neighbor)) {
                    // SPECIAL CASE: If both tiles are metro stations cz faster to use metro
                    if (location.type == finalproject.system.TileType.Metro &&
                            neighbor.type == finalproject.system.TileType.Metro) {

                        // Calculate Manhattan distance between metro stations
                        int manhattanDist = Math.abs(location.xCoord - neighbor.xCoord) +
                                Math.abs(location.yCoord - neighbor.yCoord);

                        // Metro time cost = distance * 0.2
                        double metroTimeCost = manhattanDist * 0.2;
                        g.addEdge(location, neighbor, metroTimeCost);

                    } else {
                        // Normal case: use regular time cost
                        g.addEdge(location, neighbor, neighbor.timeCost);
                    }
                }
            }
        }
    }
}


