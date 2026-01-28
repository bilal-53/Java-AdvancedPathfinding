package finalproject;


import finalproject.system.Tile;
import java.util.ArrayList; //saw on ed that we are allowed to :)

public class ShortestPath extends PathFindingService {
    // Ignores time and zombie risk, finds minimize meters traveled
    public ShortestPath(Tile start) {
        super(start);
        generateGraph(); //creates a map with distances when survivor starts planning
    }

    @Override
    // Scout all reachable locations using BFS (can't cross mountains or other obstacles)
    public void generateGraph() {
        ArrayList<Tile> reachableLocations = GraphTraversal.BFS(source); //cz it returns an arraylist

        // Create map representation using physical distance costs
        g = new Graph(reachableLocations);

        // Connect adjacent locations with distance costs
        for (Tile location : reachableLocations) {
            for (Tile adjacentLocation : location.adjacentTiles) {
                // Only create paths through traversable terrain
                if (adjacentLocation.isWalkable() && reachableLocations.contains(adjacentLocation)) {
                    // SPECIAL CASE: If both tiles are metro stations cz faster to use metro
                    if (location.type == finalproject.system.TileType.Metro &&
                            adjacentLocation.type == finalproject.system.TileType.Metro) {

                        // Calculate Manhattan distance between metro stations
                        int manhattanDist = Math.abs(location.xCoord - adjacentLocation.xCoord) +
                                Math.abs(location.yCoord - adjacentLocation.yCoord);

                        // Metro distance cost = distance / 0.2
                        double metroDistanceCost = manhattanDist / 0.2;
                        g.addEdge(location, adjacentLocation, metroDistanceCost);

                    } else {
                        // Normal case: use regular distance cost
                        g.addEdge(location, adjacentLocation, adjacentLocation.distanceCost);
                    }
                }
            }
        }
    }
}