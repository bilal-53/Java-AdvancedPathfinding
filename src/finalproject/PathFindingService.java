package finalproject;

import finalproject.system.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public abstract class PathFindingService {
    Tile source;
    Graph g;

    public PathFindingService(Tile start) {
        this.source = start;
    }

    public abstract void generateGraph();

    // Dijkstra's algorithm: Find safest escape route from start tile to any safe house
    public ArrayList<Tile> findPath(Tile startNode) {

        // Get all locations in the world
        ArrayList<Tile> allLocations = new ArrayList<>();
        for (Graph.Edge edge : g.getAllEdges()) {
            if (!allLocations.contains(edge.getStart())) {
                allLocations.add(edge.getStart());
            }
            if (!allLocations.contains(edge.getEnd())) {
                allLocations.add(edge.getEnd());
            }
        }

        // Initialize survivor's knowledge of the world : nothing in the beginning
        for (Tile location : allLocations) {
            location.costEstimate = Double.MAX_VALUE;
            location.predecessor = null;
        }
        startNode.costEstimate = 0;  // Survivor starts here : zero distance from start

        // Create priority queue to explore most probable locations first
        TilePriorityQ explorationQueue = new TilePriorityQ(allLocations);

        // Explore the world, always moving to closest known location
        while (!explorationQueue.isEmpty()) {
            Tile currentLocation = explorationQueue.removeMin();

            // If a safe house is find : stop exploring
            if (currentLocation.isDestination) {
                break;
            }

            // Check all adjacent locations from current position
            for (Tile neighbor : g.getNeighbors(currentLocation)) {

                // Find terrain cost to move to neighbor
                double terrainCost = 0;
                for (Graph.Edge path : g.getAllEdges()) {
                    if (path.getStart().equals(currentLocation) &&
                            path.getEnd().equals(neighbor)) {
                        terrainCost = path.weight;
                        break;
                    }
                }

                // Calculate new total distance if we go through current location
                double newTotalDistance = currentLocation.costEstimate + terrainCost;

                // If we found a shorter route to neighbor we replace it
                if (newTotalDistance < neighbor.costEstimate) {
                    explorationQueue.updateKeys(neighbor, currentLocation, newTotalDistance);
                }
            }
        }

        // Find which tile is the safe house (destination)
        Tile safeHouse = null;
        for (Tile location : allLocations) {
            if (location.isDestination) {
                safeHouse = location;
                break;
            }
        }

        // If safe house unreachable (zombie blockade or no path), then no escape route
        if (safeHouse == null || safeHouse.predecessor == null) {
            return new ArrayList<>();
        }

        // Trace back steps from safe house to starting point
        ArrayList<Tile> escapeRoute = new ArrayList<>();
        Tile current = safeHouse;
        while (current != null) {
            escapeRoute.add(0, current); // Build path backwards safe house-> start
            current = current.predecessor;
        }

        return escapeRoute;  // Complete route from start to safe house
    }

    //Find path to specific destination
    public ArrayList<Tile> findPath(Tile start, Tile end) {

        // Get all locations in the world
        ArrayList<Tile> allLocations = new ArrayList<>();

        for (Graph.Edge edge : g.getAllEdges()) {
            if (!allLocations.contains(edge.getStart())) {
                allLocations.add(edge.getStart());
            }
            if (!allLocations.contains(edge.getEnd())) {
                allLocations.add(edge.getEnd());
            }
        }

        // Initialize survivor's knowledge of the world
        for (Tile location : allLocations) {
            location.costEstimate = Double.MAX_VALUE;
            location.predecessor = null;
        }
        start.costEstimate = 0;

        // Create priority queue
        TilePriorityQ explorationQueue = new TilePriorityQ(allLocations);

        // Explore until reaching the specific target location
        while (!explorationQueue.isEmpty()) {
            Tile currentLocation = explorationQueue.removeMin();

            // Found the specific location we're looking for
            if (currentLocation.equals(end)) {
                break;
            }

            // Check adjacent locations
            for (Tile neighbor : g.getNeighbors(currentLocation)) {
                double terrainCost = 0;
                for (Graph.Edge path : g.getAllEdges()) {
                    if (path.getStart().equals(currentLocation) &&
                            path.getEnd().equals(neighbor)) {
                        terrainCost = path.weight;
                        break;
                    }
                }

                double newTotalDistance = currentLocation.costEstimate + terrainCost;

                if (newTotalDistance < neighbor.costEstimate) {
                    explorationQueue.updateKeys(neighbor, currentLocation, newTotalDistance);
                }
            }
        }

        // If target location is unreachable
        if (end.predecessor == null && !start.equals(end)) {
            return new ArrayList<>();
        }

        // Trace back path from target location to start
        ArrayList<Tile> path = new ArrayList<>();
        Tile current = end;
        while (current != null) {
            path.add(0, current);
            current = current.predecessor;
        }

        return path;
    }

    //Find path visiting multiple waypoints, then to safe house
    public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints){

        ArrayList<Tile> completeRoute = new ArrayList<>();
        Tile currentPosition = start;  // survivor's current location

        // Find the final safe house
        Tile safeHouse = null;
        ArrayList<Tile> allLocations = new ArrayList<>();
        for (Graph.Edge edge : g.getAllEdges()) {
            if (!allLocations.contains(edge.getStart())) {
                allLocations.add(edge.getStart());
            }
            if (!allLocations.contains(edge.getEnd())) {
                allLocations.add(edge.getEnd());
            }
        }

        for (Tile location : allLocations) {
            if (location.isDestination) {
                safeHouse = location;
                break;
            }
        }

        if (safeHouse == null) {
            return new ArrayList<>();  // no safe house found
        }

        // Combine waypoints with final destination
        LinkedList<Tile> allStops = new LinkedList<>(waypoints);
        allStops.add(safeHouse);

        // Navigate to each stop in the specified order
        for (Tile nextStop : allStops) {
            // Find path from current position to next waypoint
            ArrayList<Tile> segment = findPath(currentPosition, nextStop);

            // If can't reach this stop
            if (segment.isEmpty()) {
                return new ArrayList<>();
            }

            // Avoid duplicate tile where segments connect
            if (!completeRoute.isEmpty() && !segment.isEmpty() &&
                    completeRoute.get(completeRoute.size() - 1).equals(segment.get(0))) {
                segment.remove(0);  // Remove overlapping connection point
            }

            // Add this segment to complete route
            completeRoute.addAll(segment);
            currentPosition = nextStop;  // Move survivor to this location
        }

        return completeRoute;
    }
}



