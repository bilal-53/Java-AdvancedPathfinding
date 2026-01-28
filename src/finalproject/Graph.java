package finalproject;

import java.util.ArrayList;
import java.util.HashSet;

import finalproject.system.Tile;
import finalproject.system.TileType;
import finalproject.tiles.*;

public class Graph {
    // for each vertex : store its outgoing edges
    private ArrayList<ArrayList<Edge>> adjList;

    // Store all vertices in the graph
    private ArrayList<Tile> vertices;

    // Store all edges for getAllEdges() method
    private ArrayList<Edge> allEdges;

    // Map to quickly find a tile's index in the vertices list
    private HashSet<Tile> vertexSet;

    // Initialize graph with vertices, no edges initially
    public Graph(ArrayList<Tile> vertices) {
        this.vertices = new ArrayList<>(vertices);
        this.vertexSet = new HashSet<>(vertices);
        this.allEdges = new ArrayList<>();

        // Initialize adjacency list with empty lists for each vertex
        this.adjList = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            adjList.add(new ArrayList<Edge>());
        }
    }

    // Add a directed edge from origin to destination with given weight
    public void addEdge(Tile origin, Tile destination, double weight){

        // Check if both vertices exist in the graph
        if (!vertexSet.contains(origin) || !vertexSet.contains(destination)) {
            return;
        }

        Edge edge = new Edge(origin, destination, weight);

        // Find origin's index and add edge to its adjacency list
        int originIndex = vertices.indexOf(origin);
        adjList.get(originIndex).add(edge);

        // Add to all edges list
        allEdges.add(edge);
    }


    // Return list of all edges in the graph
    public ArrayList<Edge> getAllEdges() {
        return new ArrayList<>(allEdges);
    }

    // Return list of tiles adjacent to t
    public ArrayList<Tile> getNeighbors(Tile t) {
        ArrayList<Tile> neighbors = new ArrayList<>();

        if (vertexSet.contains(t)) {
            int tileIndex = vertices.indexOf(t);
            for (Edge edge : adjList.get(tileIndex)) {
                neighbors.add(edge.destination);
            }
        }

        return neighbors;
    }

    // Compute total cost for the given path
    public double computePathCost(ArrayList<Tile> path) {
        if (path == null || path.size() < 2) {
            return 0.0;
        }
        double totalCost = 0.0;

        // Sum weights between consecutive tiles in the path
        for (int i = 0; i < path.size() - 1; i++) {
            Tile current = path.get(i);
            Tile next = path.get(i + 1);

            // Find edge from current to next
            boolean foundEdge = false;
            if (vertexSet.contains(current)) {
                int currentIndex = vertices.indexOf(current);
                for (Edge edge : adjList.get(currentIndex)) {
                    if (edge.destination.equals(next)) {
                        totalCost += edge.weight;
                        foundEdge = true;
                        break;
                    }
                }
            }
        }

        return totalCost;
    }


    public static class Edge{
        Tile origin;
        Tile destination;
        double weight;

        public Edge(Tile s, Tile d, double cost){
            this.origin = s;
            this.destination = d;
            this.weight = cost;
        }

        // Get start tile of this edge
        public Tile getStart(){
            return this.origin;
        }


        // Get end tile of this edge
        public Tile getEnd() {
            return this.destination;
        }

    }

    // Helper method to check if two tiles are both metro stations
    public static boolean areBothMetro(Tile t1, Tile t2) {
        return t1.type == TileType.Metro && t2.type == TileType.Metro;
    }

    // Helper method : this uses Tile's coordinates to calculate Manhattan distance
    public static double getMetroWeight(Tile metro1, Tile metro2, boolean useTimeCost) {
        if (metro1.type != TileType.Metro || metro2.type != TileType.Metro) {
            return 0;
        }

        // Calculate Manhattan distance
        int manhattanDistance = Math.abs(metro1.xCoord - metro2.xCoord) +
                Math.abs(metro1.yCoord - metro2.yCoord);

        double metroCommuteFactor = 0.2;

        if (useTimeCost) {
            // For FastestPath: time cost = distance * factor
            return manhattanDistance * metroCommuteFactor;
        } else {
            // For ShortestPath: distance cost = distance / factor
            return manhattanDistance / metroCommuteFactor;
        }
    }
}

