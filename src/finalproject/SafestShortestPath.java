package finalproject;


import java.util.ArrayList;
import java.util.LinkedList;

import finalproject.system.Tile;

public class SafestShortestPath extends ShortestPath {
    public int health;
    public Graph costGraph;
    public Graph damageGraph;
    public Graph aggregatedGraph;

    //Find the safest shortest path with given health constraint
    public SafestShortestPath(Tile start, int health) {
        super(start);
        this.health = health;
        generateGraph();// Initialize all three graphs
    }


    public void generateGraph() {
        // Find all reachable locations (avoid obstacles)
        ArrayList<Tile> reachableLocations = GraphTraversal.BFS(source);

        //COST GRAPH : Weighted by distance (like normal ShortestPath)
        costGraph = new Graph(reachableLocations);

        //DAMAGE GRAPH : Weighted by zombie damage
        damageGraph = new Graph(reachableLocations);

        //AGGREGATED GRAPH : Will combine both costs using LARAC formula
        aggregatedGraph = new Graph(reachableLocations);

        // Build all three graphs with different edge weights
        for (Tile location : reachableLocations) {
            for (Tile neighbor : location.adjacentTiles) {
                if (neighbor.isWalkable() && reachableLocations.contains(neighbor)) {
                    // Add edge to COST graph: weight = distance cost
                    costGraph.addEdge(location, neighbor, neighbor.distanceCost);

                    // Add edge to DAMAGE graph: weight = damage cost (zombie risk)
                    damageGraph.addEdge(location, neighbor, neighbor.damageCost);

                    // Add edge to AGGREGATED graph: weight = damage cost and will be updated after LARAC iterations)
                    aggregatedGraph.addEdge(location, neighbor, neighbor.damageCost);
                }
            }
        }
    }

    // Calculate total cost of a path in a specific graph
    private double getPathCost(ArrayList<Tile> path, Graph graph) {
        if (path == null || path.size() < 2){
            return 0.0;
        }

        double totalCost = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            Tile current = path.get(i);
            Tile next = path.get(i + 1);

            // Find edge weight between consecutive tiles
            for (Graph.Edge edge : graph.getAllEdges()) {
                if (edge.getStart().equals(current) && edge.getEnd().equals(next)) {
                    totalCost += edge.weight;
                    break;
                }
            }
        }
        return totalCost;
    }

    // Calculate total distance cost of a path
    private double getDistanceCost(ArrayList<Tile> path) {
        return getPathCost(path, costGraph);
    }

    // Calculate total damage cost of a path
    private double getDamageCost(ArrayList<Tile> path) {
        return getPathCost(path, damageGraph);
    }

    // Update aggregated graph weights: cλ = distance + λ * damage
    private void updateAggregatedGraph(double lambda) {
        // For each edge in costGraph, calculate aggregated weight
        for (Graph.Edge edge : costGraph.getAllEdges()) {
            double distanceCost = edge.weight;

            // Find corresponding damage cost
            double damageCost = 0;
            // For each edge, match distance and damage costs
            for (Graph.Edge dEdge : damageGraph.getAllEdges()) {
                if (dEdge.getStart().equals(edge.getStart()) &&
                        dEdge.getEnd().equals(edge.getEnd())) {
                    damageCost = dEdge.weight;
                    break;
                }
            }

            // Calculate aggregated cost: c + λ * d
            double aggregatedCost = distanceCost + (lambda * damageCost);

            // Update aggregated graph edge weight
            for (Graph.Edge aEdge : aggregatedGraph.getAllEdges()) {
                if (aEdge.getStart().equals(edge.getStart()) &&
                        aEdge.getEnd().equals(edge.getEnd())) {
                    aEdge.weight = aggregatedCost;
                    break;
                }
            }
        }
    }

    // Find path in a specific graph using Dijkstra
    private ArrayList<Tile> findPathInGraph(Graph graph, Tile start, Tile end) {
        // Temporarily set parent's graph field
        g = graph;
        ArrayList<Tile> path = findPath(start, end);
        return path;
    }

    // Find destination tile (safe house)
    private Tile findDestination() {
        ArrayList<Tile> allTiles = new ArrayList<>();
        for (Graph.Edge edge : costGraph.getAllEdges()) {
            if (!allTiles.contains(edge.getStart())){
                allTiles.add(edge.getStart());
            }
            if (!allTiles.contains(edge.getEnd())){
                allTiles.add(edge.getEnd());
            }
        }

        // There is exactly one destination tile in the map
        for (Tile tile : allTiles) {
            if (tile.isDestination) {
                return tile;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints) {
        // Find safe house
        Tile destination = findDestination();
        if (destination == null){
            return null;
        }

        // Combine waypoints with destination
        LinkedList<Tile> allStops = new LinkedList<>(waypoints);
        allStops.add(destination);

        // LARAC Algorithm Step 1: Find shortest distance path (pc)
        g = costGraph;
        ArrayList<Tile> pc = super.findPath(start, allStops);
        if (pc.isEmpty()){
            return null;
        }

        double c_pc = getDistanceCost(pc);  // Distance cost of shortest path
        double d_pc = getDamageCost(pc);    // Damage cost of shortest path

        // If shortest path is already safe enough, return it
        if (d_pc <= health) {
            return pc;
        }

        // LARAC Step 2: Find safest path (minimize damage) (pd)
        g = damageGraph;
        ArrayList<Tile> pd = super.findPath(start, allStops);
        if (pd.isEmpty()){
            return null;
        }

        double c_pd = getDistanceCost(pd);  // Distance cost of safest path
        double d_pd = getDamageCost(pd);    // Damage cost of safest path

        // If even safest path is too dangerous
        if (d_pd > health) {
            return null;
        }

        // LARAC iterative optimization
        while (true) {
            // Step 3: Calculate lambda multiplier
            double lambda = (c_pc - c_pd) / (d_pd - d_pc);

            // Update aggregated graph weights
            updateAggregatedGraph(lambda);

            // Step 4: Find optimal path with aggregated costs (pr)
            g = aggregatedGraph;
            ArrayList<Tile> pr = super.findPath(start, allStops);
            if (pr.isEmpty()){
                return pd; // Fallback to safest path
            }

            double c_pr = getDistanceCost(pr); //distance cost of pr
            double d_pr = getDamageCost(pr); //damage cost of pr
            double aggregated_pr = getPathCost(pr, aggregatedGraph);
            double aggregated_pc = getPathCost(pc, aggregatedGraph);

            // If aggregated cost does not improve over shortest path
            if (Math.abs(aggregated_pr - aggregated_pc) < 0.0001) {
                //return safest path found
                return pd;
            } else if (d_pr <= health) {
                // New path is safe : update safest path
                pd = pr;
                c_pd = c_pr;
                d_pd = d_pr;
            } else {
                // New path is unsafe : update shortest path
                pc = pr;
                c_pc = c_pr;
                d_pc = d_pr;
            }
        }
    }
}



