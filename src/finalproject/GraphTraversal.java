package finalproject;

import finalproject.system.Tile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class GraphTraversal
{


    public static ArrayList<Tile> BFS(Tile s) {
        ArrayList<Tile> orderVisited = new ArrayList<>();

        if (s == null || !s.isWalkable()) {
            return orderVisited;
        }

        // process nodes in the order they are discovered using queue
        LinkedList<Tile> queue = new LinkedList<>();
        // Set to avoid revisiting tiles and infinite loops
        HashSet<Tile> visited = new HashSet<>();

        // Start with the first tile
        queue.add(s);
        visited.add(s);

        while (!queue.isEmpty()) {
            // Remove from front of queue : FIFO
            Tile current = queue.poll();
            // Record visit order
            orderVisited.add(current);

            for (Tile neighbor : current.adjacentTiles) {
                // Only visit walkable and unvisited tiles
                if (neighbor.isWalkable() && !visited.contains(neighbor)) {
                    // Add to back of queue
                    queue.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }

        return orderVisited;
    }


    public static ArrayList<Tile> DFS(Tile s) {
        ArrayList<Tile> orderVisited = new ArrayList<>();

        if (s == null || !s.isWalkable()) {
            return orderVisited;
        }

        //LinkedList as a stack push to front, pop from front
        LinkedList<Tile> stack = new LinkedList<>();
        HashSet<Tile> visited = new HashSet<>();

        stack.push(s);
        visited.add(s);

        while (!stack.isEmpty()) {
            // Remove from front of stack : LIFO
            Tile current = stack.pop();
            orderVisited.add(current);

            for (Tile neighbor : current.adjacentTiles) {
                if (neighbor.isWalkable() && !visited.contains(neighbor)) {
                    // Add to front of stack
                    stack.push(neighbor);
                    visited.add(neighbor);
                }
            }
        }

        return orderVisited;
    }
}

