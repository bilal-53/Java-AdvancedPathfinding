package finalproject;

import java.util.ArrayList;
import java.util.Arrays;


import finalproject.system.Tile;

public class TilePriorityQ {
    // Heap array to store tiles in min-heap order
    private ArrayList<Tile> heap;

    // Track positions of tiles in heap
    private ArrayList<Tile> positionMap;

    // Current nb of elements of heap
    private int size;

    // Build priority queue from given tiles
    public TilePriorityQ(ArrayList<Tile> vertices) {
        this.heap = new ArrayList<>();
        this.positionMap = new ArrayList<>();
        this.size = 0;

        // Initialize heap and position map
        for (Tile tile : vertices) {
            tile.predecessor = null;

            heap.add(tile);
            positionMap.add(tile);
            size++;
        }

        // Build heap from the bottom up
        for (int i = size / 2 - 1; i >= 0; i--) {
            heapifyDown(i);
        }
    }

    // Remove and return tile with minimum costEstimate
    public Tile removeMin() {
        if (size == 0) {
            return null;
        }

        // Root has minimum value
        Tile minTile = heap.get(0);

        // Move last element to root
        heap.set(0, heap.get(size - 1));
        positionMap.set(positionMap.indexOf(heap.get(0)), heap.get(0));

        // Remove last element
        heap.remove(size - 1);
        size--;

        // Restore heap property
        if (size > 0) {
            heapifyDown(0);
        }

        return minTile;
    }


    // Update tile's predecessor and cost estimate, then fix heap
    public void updateKeys(Tile t, Tile newPred, double newEstimate) {
        // Find tile in heap
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (heap.get(i).equals(t)) {
                index = i;
                break;
            }
        }

        // If tile not in queue, do nothing
        if (index == -1) {
            return;
        }

        // Store old cost for comparison
        double oldCost = t.costEstimate;

        // update the tile's fields
        t.costEstimate = newEstimate;    // Set the new estimate
        t.predecessor = newPred;         // Set the new predecessor

        // Heapify based on whether cost increased or decreased
        if (newEstimate < oldCost) {
            heapifyUp(index);
        }
        else{
            if (newEstimate > oldCost) {
                heapifyDown(index);
            }
        }
        // If equal, no need to move so done :)
    }

    // Move element down to restore heap property
    private void heapifyDown(int index) {
        while (true) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int smallest = index;

            // Find smallest among node and its children
            if (leftChild < size &&
                    heap.get(leftChild).costEstimate < heap.get(smallest).costEstimate) {
                smallest = leftChild;
            }

            if (rightChild < size &&
                    heap.get(rightChild).costEstimate < heap.get(smallest).costEstimate) {
                smallest = rightChild;
            }

            // If node is smallest, heap property satisfied
            if (smallest == index) {
                break;
            }

            // Swap with smallest child
            swap(index, smallest);
            index = smallest;
        }
    }

    // Move element up to restore heap property
    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;

            if (heap.get(index).costEstimate >= heap.get(parentIndex).costEstimate) {
                break; // Heap property satisfied
            }

            // Swap with parent
            swap(index, parentIndex);
            index = parentIndex;
        }
    }

    // Swap two elements in heap
    private void swap(int i, int j) {
        Tile temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
    // Helper method to check if empty
    public boolean isEmpty() {
        return size == 0;
    }
}





