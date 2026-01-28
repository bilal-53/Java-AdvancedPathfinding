package finalproject;

import finalproject.system.Tile;
import finalproject.tiles.*;
import org.junit.jupiter.api.*;
import finalproject.system.TileType;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MiniTester {

    private static int proficiencyPassed = 0;
    private static int approachMasteryPassed = 0;
    private static int fullMasteryPassed = 0;


    @Test
    @DisplayName("Count number of tests passed")
    @Order(100)
    void testBasicLevelPassed() {
        System.out.println("Proficiency passed: " + proficiencyPassed );
        System.out.println("Approaching Mastery passed: " + approachMasteryPassed);
        System.out.println("Full Mastery passed: " + fullMasteryPassed);
    }


    @Test
    @DisplayName("PROFICIENCY: testTile1")
    @Tag("score:1")
    @Order(1)
    public void testTile1() {
        Tile t;

        t = new PlainTile();
        assertEquals(3, t.distanceCost, "PlainTile distanceCost should be 3");
        assertEquals(1, t.timeCost, "PlainTile timeCost should be 1");
        assertEquals(0, t.damageCost, "PlainTile damageCost should be 0");

        t = new DesertTile();
        assertEquals(2, t.distanceCost, "DesertTile distanceCost should be 2");
        assertEquals(6, t.timeCost, "DesertTile timeCost should be 6");
        assertEquals(3, t.damageCost, "DesertTile damageCost should be 3");

        t = new MountainTile();
        assertEquals(100, t.distanceCost, "MountainTile distanceCost should be 100");
        assertEquals(100, t.timeCost, "MountainTile timeCost should be 100");
        assertEquals(100, t.damageCost, "MountainTile damageCost should be 100");

        // Test passed
        proficiencyPassed++;
    }

    @Test
    @DisplayName("PROFICIENCY: testTile2")
    @Tag("score:1")
    @Order(2)
    public void testTile2() {
        Tile t;

        t = new FacilityTile();
        assertEquals(1, t.distanceCost, "FacilityTile distanceCost should be 1");
        assertEquals(2, t.timeCost, "FacilityTile timeCost should be 2");
        assertEquals(0, t.damageCost, "FacilityTile damageCost should be 0");

        t = new MetroTile();
        assertEquals(1, t.distanceCost, "MetroTile distanceCost should be 1");
        assertEquals(1, t.timeCost, "MetroTile timeCost should be 1");
        assertEquals(2, t.damageCost, "MetroTile damageCost should be 2");

        t = new ZombieInfectedRuinTile();
        assertEquals(1, t.distanceCost, "ZombieInfectedRuinTile distanceCost should be 1");
        assertEquals(3, t.timeCost, "ZombieInfectedRuinTile timeCost should be 3");
        assertEquals(5, t.damageCost, "ZombieInfectedRuinTile damageCost should be 5");

        // Test passed
        proficiencyPassed++;
    }

    @Test
    @DisplayName("PROFICIENCY: testBFS1")
    @Tag("score:1")
    @Order(3)
    public void testBFS1() {
        Tile[] tiles = new Tile[5];
        Tile[] ansTiles = new Tile[5];
        Tile[] altTiles = new Tile[5];

        for (int i = 0; i < tiles.length; ++i) {
            tiles[i] = new TestTile();
            tiles[i].nodeID = i;
        }

        for (int i = 0; i < tiles.length - 1; ++i)
            tiles[i].addNeighbor(tiles[i + 1]);

        // Expected traversal orders
        ansTiles[0] = tiles[2];
        ansTiles[1] = tiles[1];
        ansTiles[2] = tiles[3];
        ansTiles[3] = tiles[0];
        ansTiles[4] = tiles[4];

        altTiles[0] = tiles[2];
        altTiles[1] = tiles[3];
        altTiles[2] = tiles[1];
        altTiles[3] = tiles[4];
        altTiles[4] = tiles[0];

        ArrayList<Tile> arr = GraphTraversal.BFS(tiles[2]);

        assertEquals(tiles.length, arr.size(), "Incorrect number of tiles visited");

        boolean matchesAns = true;
        for (int i = 0; i < tiles.length; ++i) {
            if (arr.get(i) != ansTiles[i]) {
                matchesAns = false;
                break;
            }
        }

        boolean matchesAlt = true;
        for (int i = 0; i < tiles.length; ++i) {
            if (arr.get(i) != altTiles[i]) {
                matchesAlt = false;
                break;
            }
        }

        assertTrue(matchesAns || matchesAlt, "BFS traversal order is incorrect");

        // Test passed
        proficiencyPassed++;
    }

    @Test
    @DisplayName("PROFICIENCY: testDFS1")
    @Tag("score:1")
    @Order(4)
    public void testDFS1() {
        Tile[] tiles = new Tile[5];
        Tile[] ansTiles = new Tile[5];
        Tile[] altTiles = new Tile[5];

        for (int i = 0; i < tiles.length; ++i) {
            tiles[i] = new TestTile();
            tiles[i].nodeID = i;
        }

        for (int i = 0; i < tiles.length - 1; ++i)
            tiles[i].addNeighbor(tiles[i + 1]);

        // Expected traversal orders
        ansTiles[0] = tiles[2];
        ansTiles[1] = tiles[3];
        ansTiles[2] = tiles[4];
        ansTiles[3] = tiles[1];
        ansTiles[4] = tiles[0];

        altTiles[0] = tiles[2];
        altTiles[1] = tiles[1];
        altTiles[2] = tiles[0];
        altTiles[3] = tiles[3];
        altTiles[4] = tiles[4];

        ArrayList<Tile> arr = GraphTraversal.DFS(tiles[2]);

        assertEquals(tiles.length, arr.size(), "Incorrect number of tiles visited");

        boolean matchesAns = true;
        for (int i = 0; i < tiles.length; ++i) {
            if (arr.get(i) != ansTiles[i]) {
                matchesAns = false;
                break;
            }
        }

        boolean matchesAlt = true;
        for (int i = 0; i < tiles.length; ++i) {
            if (arr.get(i) != altTiles[i]) {
                matchesAlt = false;
                break;
            }
        }

        assertTrue(matchesAns || matchesAlt, "DFS traversal order is incorrect");

        // Test passed
        proficiencyPassed++;
    }

    @Test
    @DisplayName("PROFICIENCY: testBFS2")
    @Tag("score:1")
    @Order(5)
    public void testBFS2() {
        Tile[] tiles = new Tile[5];

        for (int i = 0; i < tiles.length; ++i) {
            tiles[i] = new TestTile();
            tiles[i].nodeID = i;
        }

        // Build star-shaped graph
        for (int i = 1; i < tiles.length; ++i) {
            tiles[0].addNeighbor(tiles[i]);
            tiles[i].addNeighbor(tiles[0]);
        }

        ArrayList<Tile> arr = GraphTraversal.BFS(tiles[0]);

        assertEquals(tiles.length, arr.size(), "Incorrect number of tiles visited");

        assertEquals(tiles[0], arr.get(0), "First node should be the starting node");

        Set<Integer> expectedNodeIDs = new HashSet<>(Arrays.asList(1, 2, 3, 4));
        Set<Integer> actualNodeIDs = new HashSet<>();

        for (int i = 1; i < arr.size(); ++i) {
            actualNodeIDs.add(arr.get(i).nodeID);
        }

        assertEquals(expectedNodeIDs, actualNodeIDs, "BFS did not visit all neighbors correctly");

        // Test passed
        proficiencyPassed++;
    }

    @Test
    @DisplayName("PROFICIENCY: testDFS2")
    @Tag("score:1")
    @Order(6)
    public void testDFS2() {
        Tile[] tiles = new Tile[5];

        for (int i = 0; i < tiles.length; ++i) {
            tiles[i] = new TestTile();
            tiles[i].nodeID = i;
        }

        // Build star-shaped graph
        for (int i = 1; i < tiles.length; ++i) {
            tiles[0].addNeighbor(tiles[i]);
            tiles[i].addNeighbor(tiles[0]);
        }

        ArrayList<Tile> arr = GraphTraversal.DFS(tiles[0]);

        assertEquals(tiles.length, arr.size(), "Incorrect number of tiles visited");

        boolean[] visited = new boolean[tiles.length];
        for (Tile t : arr) {
            visited[t.nodeID] = true;
        }
        for (int i = 0; i < visited.length; ++i) {
            assertTrue(visited[i], "DFS did not visit node " + i);
        }

        // Test passed
        proficiencyPassed++;
    }

    public static class TestTile extends Tile {
        public TestTile() {
            super();
        }
    }

    @Test
    @DisplayName("APPROACHING MASTERY: testEdges")
    @Tag("score:1")
    @Order(7)
    public void testEdges() {
        // Build the graph
        ArrayList<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < 4; ++i) {
            Tile t = new TestTile();
            t.nodeID = i;
            tiles.add(t);
        }
        Graph graph = new Graph(tiles);

        // Initially, the graph should have zero edges
        ArrayList<Graph.Edge> edges = graph.getAllEdges();
        assertEquals(0, edges.size(), "Graph should have zero edges initially.");

        // Now add edges
        graph.addEdge(tiles.get(0), tiles.get(1), 2);
        graph.addEdge(tiles.get(1), tiles.get(2), 3);
        graph.addEdge(tiles.get(2), tiles.get(3), 4);

        // Now the graph should have 3 edges
        edges = graph.getAllEdges();
        assertEquals(3, edges.size(), "Graph should have 3 edges after adding edges.");

        // Verify that edges are stored correctly
        boolean edge01Exists = false;
        boolean edge12Exists = false;
        boolean edge23Exists = false;

        for (Graph.Edge edge : edges) {
            if (edge.origin == tiles.get(0) && edge.destination == tiles.get(1) && edge.weight == 2) {
                edge01Exists = true;
            }
            if (edge.origin == tiles.get(1) && edge.destination == tiles.get(2) && edge.weight == 3) {
                edge12Exists = true;
            }
            if (edge.origin == tiles.get(2) && edge.destination == tiles.get(3) && edge.weight == 4) {
                edge23Exists = true;
            }
        }

        assertTrue(edge01Exists, "Edge from tile 0 to tile 1 should exist.");
        assertTrue(edge12Exists, "Edge from tile 1 to tile 2 should exist.");
        assertTrue(edge23Exists, "Edge from tile 2 to tile 3 should exist.");

        // Increment mastery level
        approachMasteryPassed++;
    }

    @Test
    @DisplayName("APPROACHING MASTERY: testNeighbors1")
    @Tag("score:1")
    @Order(8)
    public void testNeighbors1() {
        // Build the graph
        ArrayList<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < 7; ++i) {
            Tile t = new TestTile();
            t.nodeID = i;
            tiles.add(t);
        }
        Graph graph = new Graph(tiles);

        // Add edges as per the test
        graph.addEdge(tiles.get(0), tiles.get(1), 2);
        graph.addEdge(tiles.get(0), tiles.get(2), 6);
        graph.addEdge(tiles.get(1), tiles.get(2), 1);
        graph.addEdge(tiles.get(1), tiles.get(3), 5);
        graph.addEdge(tiles.get(3), tiles.get(2), 8);
        graph.addEdge(tiles.get(3), tiles.get(4), 1);
        graph.addEdge(tiles.get(3), tiles.get(5), 2);
        graph.addEdge(tiles.get(4), tiles.get(6), 2);
        graph.addEdge(tiles.get(5), tiles.get(3), 5);
        graph.addEdge(tiles.get(5), tiles.get(4), 6);
        graph.addEdge(tiles.get(6), tiles.get(5), 6);

        // Test getNeighbors for tile 3
        ArrayList<Tile> neighbors = graph.getNeighbors(tiles.get(3));

        assertEquals(3, neighbors.size(), "Tile 3 should have 3 neighbors.");

        ArrayList<Tile> expectedNeighbors = new ArrayList<>();
        expectedNeighbors.add(tiles.get(2));
        expectedNeighbors.add(tiles.get(4));
        expectedNeighbors.add(tiles.get(5));

        assertTrue(neighbors.containsAll(expectedNeighbors), "Tile 3's neighbors should be tiles 2, 4, and 5.");

        // Increment mastery level
        approachMasteryPassed++;
    }

    @Test
    @DisplayName("APPROACHING MASTERY: testNeighbors2")
    @Tag("score:1")
    @Order(9)
    public void testNeighbors2() {
        // Build the graph
        ArrayList<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < 7; ++i) {
            Tile t = new TestTile();
            t.nodeID = i;
            tiles.add(t);
        }
        Graph graph = new Graph(tiles);

        // Add edges as per the test
        graph.addEdge(tiles.get(0), tiles.get(1), 2);
        graph.addEdge(tiles.get(0), tiles.get(2), 6);
        graph.addEdge(tiles.get(1), tiles.get(2), 1);
        graph.addEdge(tiles.get(1), tiles.get(3), 5);
        graph.addEdge(tiles.get(3), tiles.get(2), 8);
        graph.addEdge(tiles.get(3), tiles.get(4), 1);
        graph.addEdge(tiles.get(3), tiles.get(5), 2);
        graph.addEdge(tiles.get(4), tiles.get(6), 2);
        graph.addEdge(tiles.get(5), tiles.get(3), 5);
        graph.addEdge(tiles.get(5), tiles.get(4), 6);
        graph.addEdge(tiles.get(6), tiles.get(5), 6);
        graph.addEdge(tiles.get(4), tiles.get(0), 9);
        graph.addEdge(tiles.get(5), tiles.get(1), 1);

        // Test getNeighbors for tile 5
        ArrayList<Tile> neighbors = graph.getNeighbors(tiles.get(5));

        assertEquals(3, neighbors.size(), "Tile 5 should have 3 neighbors.");

        ArrayList<Tile> expectedNeighbors = new ArrayList<>();
        expectedNeighbors.add(tiles.get(1));
        expectedNeighbors.add(tiles.get(3));
        expectedNeighbors.add(tiles.get(4));

        assertTrue(neighbors.containsAll(expectedNeighbors), "Tile 5's neighbors should be tiles 1, 3, and 4.");

        // Increment mastery level
        approachMasteryPassed++;
    }

    public static Graph graph = null;
    public static HashMap<Tile, Integer> tile2id = null;
    public static ArrayList<Tile> tiles = null;

    public static void buildGraph() {
        if (graph != null)
            return;
        tiles = new ArrayList<Tile>();
        tile2id = new HashMap<Tile, Integer>();
        for (int i = 0; i < 5; ++i) {
            TestTile t  = new TestTile();
            t.nodeID = i;
            tile2id.put(t, i);
            tiles.add(t);
        }

        graph = new Graph(tiles);
        graph.addEdge(tiles.get(0), tiles.get(1), 3);
        graph.addEdge(tiles.get(0), tiles.get(3), 2);
        graph.addEdge(tiles.get(1), tiles.get(2), 1);
        graph.addEdge(tiles.get(1), tiles.get(3), 5);
        graph.addEdge(tiles.get(2), tiles.get(3), 4);
        graph.addEdge(tiles.get(2), tiles.get(4), 5);
        graph.addEdge(tiles.get(3), tiles.get(4), 2);
    }

    @Test
    @DisplayName("APPROACHING MASTERY: testPathCost1")
    @Tag("score:1")
    @Order(10)
    public void testPathCost1() {
        // Build the graph
        buildGraph();
        ArrayList<Tile> path = new ArrayList<Tile>();
        for (int i = 0; i < 5; ++i)
            path.add(tiles.get(i));
        double cost = graph.computePathCost(path);

        assertEquals(10.0, cost, "Cost of path 0-1-2-3-4 should be 10.");

        // Increment mastery level
        approachMasteryPassed++;
    }

    @Test
    @DisplayName("APPROACHING MASTERY: testRemoveMin")
    @Tag("score:1")
    @Order(11)
    public void testRemoveMin() {
        ArrayList<Tile> tiles = new ArrayList<Tile>();
        int a[] = { 3, 1, 8, 4, 9, 5, 2, 6, 10, 7 };
        for (int i = 0; i < a.length; ++i) {
            Tile t = new TestTile();
            t.costEstimate = a[i];
            tiles.add(t);
        }
        Arrays.sort(a);
        TilePriorityQ q = new TilePriorityQ(tiles);
        for (int i = 0; i < a.length; ++i) {
            Tile t = q.removeMin();
            assertEquals(a[i], t.costEstimate, "Priority queue returned incorrect costEstimate.");
        }
        assertTrue(true);

        // Increment mastery level
        approachMasteryPassed++;
    }

    @Test
    @DisplayName("APPROACHING MASTERY: testUpdateKeys")
    @Tag("score:1")
    @Order(12)
    public void testUpdateKeys() {
        ArrayList<Tile> tiles = new ArrayList<>();
        int[] initialCosts = {3, 11, 8, 4, 9, 5, 2, 6, 10, 7};
        int[] expectedCosts = {1, 2, 3, 4, 5, 7, 8, 9, 10, 12};

        for (int i = 0; i < initialCosts.length; ++i) {
            Tile t = new TestTile();
            t.costEstimate = initialCosts[i];
            tiles.add(t);
        }

        TilePriorityQ q = new TilePriorityQ(tiles);

        // Update the costEstimate of tiles
        // Update tiles.get(1) costEstimate to 1
        q.updateKeys(tiles.get(1), null, 1);

        // Update tiles.get(7) costEstimate to 12
        q.updateKeys(tiles.get(7), null, 12);

        // Now remove min and check the order
        for (int expectedCost : expectedCosts) {
            Tile t = q.removeMin();
            assertEquals(expectedCost, t.costEstimate, "Priority queue returned incorrect costEstimate.");
        }

        // Increment mastery level
        approachMasteryPassed++;
    }

    public static char[][] smMap = {
            { 's', 'd' },
            { 'z', 'e' },
    };
    public static char[][] lgMap = {
            { 's', 'M', 'p', 'z' },
            { 'f', 'm', 'p', 'd' },
            { 'f', 'f', 'd', 'p' },
            { 'd', 'f', 'M', 'e' },
    };
    public static char[][] dgMap = {
            { 's', 'z', 'z', 'p' },
            { 'p', 'm', 'z', 'p' },
            { 'p', 'p', 'z', 'z' },
            { 'z', 'p', 'p', 'e' },
    };
    public static ArrayList<Tile> world = null;
    public static Tile[][] tileArray = null;

    public static void buildWorld(char[][] map, boolean useMetro) {
        world = new ArrayList<Tile>();
        tileArray = new Tile[map.length][map[0].length];
        ArrayList<MetroTile> metros = new ArrayList<MetroTile>();

        int id = 0;
        for (int i = 0; i < map.length; ++i)
            for (int j = 0; j < map[0].length; ++j) {
                Tile t = null;
                switch (map[i][j]) {
                    case 's':
                        t = new FacilityTile();
                        t.isStart = true;
                        break;

                    case 'e':
                        t = new FacilityTile();
                        t.isDestination = true;
                        break;

                    case 'p':
                        t = new PlainTile();
                        break;

                    case 'd':
                        t = new DesertTile();
                        break;

                    case 'm':
                        t = new MountainTile();
                        break;

                    case 'f':
                        t = new FacilityTile();
                        break;

                    case 'M':
                        t = new MetroTile();
                        metros.add((MetroTile) t);
                        break;

                    case 'z':
                        t = new ZombieInfectedRuinTile();
                        break;

                    default:
                        t = new MountainTile();
                }

                t.nodeID = id++;
                tileArray[i][j] = t;
                world.add(t);
            }

        for (int i = 0; i < map.length; ++i)
            for (int j = 0; j < map[0].length - 1; ++j)
                tileArray[i][j].addNeighbor(tileArray[i][j + 1]);

        for (int i = 0; i < map.length - 1; ++i)
            for (int j = 0; j < map[0].length; ++j)
                tileArray[i][j].addNeighbor(tileArray[i + 1][j]);

        if (useMetro) {
            for (int i = 0; i < metros.size() - 1; ++i)
                for (int j = i + 1; j < metros.size(); ++j) {
                    MetroTile a = metros.get(i);
                    MetroTile b = metros.get(j);
                    a.fixMetro(b);
                    if (a.metroTimeCost < 100 && a.metroDistanceCost < 100)
                        a.addNeighbor(b);
                }
        }
    }

    // Start from Order 13

    @Test
    @DisplayName("MASTERY: testSPathGenerateGraph1")
    @Tag("score:2")
    @Order(13)
    public void testSPathGenerateGraph1() {
        buildWorld(smMap, false);
        ShortestPath shortest = new ShortestPath(world.get(0));
        ArrayList<Graph.Edge> edges = shortest.g.getAllEdges();
        assertEquals(8, edges.size(), "There should be 8 edges.");
        for (Graph.Edge e : edges) {
            assertEquals(e.destination.distanceCost, e.weight, "Weight should be the distance cost.");
        }
        // Increment mastery level
        fullMasteryPassed++;
    }

    @Test
    @DisplayName("MASTERY: testSPathGenerateGraph2")
    @Tag("score:3")
    @Order(14)
    public void testSPathGenerateGraph2() {
        buildWorld(lgMap, false);
        ShortestPath shortest = new ShortestPath(world.get(0));
        ArrayList<Graph.Edge> edges = shortest.g.getAllEdges();
        assertEquals(40, edges.size(), "There should be 40 edges. Note that MountainTile is not reachable.");
        for (Graph.Edge e : edges) {
            assertEquals(e.destination.distanceCost, e.weight, "Weight should be the distance cost.");
        }
        // Increment mastery level
        fullMasteryPassed++;
    }

    @Test
    @DisplayName("MASTERY: testFindPath1Arg1")
    @Tag("score:2")
    @Order(15)
    public void testFindPath1Arg1() {
        buildWorld(smMap, false);
        ShortestPath shortest = new ShortestPath(world.get(0));
        ArrayList<Tile> path = shortest.findPath(world.get(0));
        assertEquals(3, path.size(), "Path length (number of vertices, including start and end) should be 3.");
        assertEquals(2.0, shortest.g.computePathCost(path), 0.001, "Path cost should be 2.");
        // Increment mastery level
        fullMasteryPassed++;
    }

    @Test
    @DisplayName("MASTERY: testFindPath1Arg2")
    @Tag("score:3")
    @Order(16)
    public void testFindPath1Arg2() {
        buildWorld(lgMap, false);
        ShortestPath shortest = new ShortestPath(world.get(0));
        ArrayList<Tile> path = shortest.findPath(world.get(0));

        if (path.size() == 6) { // To furthest (weighted) vertex
            Tile[] expectedPath = { tileArray[0][0], tileArray[1][0], tileArray[2][0],
                    tileArray[2][1], tileArray[2][2], tileArray[2][3] };
            for (int i = 0; i < expectedPath.length; ++i) {
                assertEquals(expectedPath[i], path.get(i), "Wrong path at position " + i);
            }
            assertEquals(8.0, shortest.g.computePathCost(path), 0.001, "Path cost should be 8.");
        } else if (path.size() == 7) { // To destination
            Tile[] expectedPath = { tileArray[0][0], tileArray[1][0], tileArray[2][0], tileArray[2][1],
                    tileArray[3][1], tileArray[3][2], tileArray[3][3] };
            for (int i = 0; i < expectedPath.length; ++i) {
                assertEquals(expectedPath[i], path.get(i), "Wrong path at position " + i);
            }
            assertEquals(6.0, shortest.g.computePathCost(path), 0.001, "Path cost should be 6.");
        } else {
            fail("Wrong path length");
        }
        // Increment mastery level
        fullMasteryPassed++;
    }

    @Test
    @DisplayName("APPROACHING MASTERY: testFindPath2Args")
    @Tag("score:1")
    @Order(17)
    public void testFindPath2Args() {
        buildWorld(lgMap, false);
        ShortestPath shortest = new ShortestPath(world.get(0));
        ArrayList<Tile> path = shortest.findPath(world.get(0), world.get(world.size() - 1));
        assertEquals(7, path.size(), "Path length (number of vertices, including start and end) should be 7.");
        assertEquals(6.0, shortest.g.computePathCost(path), 0.001, "Path cost should be 6.");
        // Increment mastery level
        approachMasteryPassed++;
    }

    @Test
    @DisplayName("APPROACHING MASTERY: testFindPath3Args")
    @Tag("score:1")
    @Order(18)
    public void testFindPath3Args() {
        buildWorld(lgMap, false);
        ShortestPath shortest = new ShortestPath(world.get(0));
        LinkedList<Tile> waypoints = new LinkedList<Tile>();
        waypoints.add(tileArray[2][2]);
        ArrayList<Tile> path = shortest.findPath(world.get(0), waypoints);
        assertEquals(7, path.size(), "Path length (number of vertices, including start and end) should be 7.");
        assertEquals(7.0, shortest.g.computePathCost(path), 0.001, "Path cost should be 7.");
        // Increment mastery level
        approachMasteryPassed++;
    }

    @Test
    @DisplayName("PROFICIENCY: testFPathGenerateGraph1")
    @Tag("score:1")
    @Order(19)
    public void testFPathGenerateGraph1() {
        buildWorld(smMap, false);
        FastestPath fastest = new FastestPath(world.get(0));
        ArrayList<Graph.Edge> edges = fastest.g.getAllEdges();
        assertEquals(8, edges.size(), "There should be 8 edges.");
        for (Graph.Edge e : edges) {
            assertEquals(e.destination.timeCost, e.weight, "Weight should be the time cost.");
        }
        // Increment mastery level
        proficiencyPassed++;
    }

    @Test
    @DisplayName("PROFICIENCY: testFPathGenerateGraph2")
    @Tag("score:1")
    @Order(20)
    public void testFPathGenerateGraph2() {
        buildWorld(lgMap, false);
        FastestPath fastest = new FastestPath(world.get(0));
        ArrayList<Graph.Edge> edges = fastest.g.getAllEdges();
        assertEquals(40, edges.size(), "There should be 40 edges. Note that MountainTile is not reachable.");
        for (Graph.Edge e : edges) {
            assertEquals(e.destination.timeCost, e.weight, "Weight should be the time cost.");
        }
        // Increment mastery level
        proficiencyPassed++;
    }

    @Test
    @DisplayName("APPROACHING MASTERY: testSPathGenerateGraphWithMetro")
    @Tag("score:1")
    @Order(21)
    public void testSPathGenerateGraphWithMetro() {
        buildWorld(lgMap, true);
        ShortestPath shortest = new ShortestPath(world.get(0));
        ArrayList<Graph.Edge> edges = shortest.g.getAllEdges();
        assertEquals(42, edges.size(), "There should be 42 edges. Note that MountainTile is not reachable.");
        for (Graph.Edge e : edges) {
            if (e.origin.getTileType() == TileType.Metro && e.destination.getTileType() == TileType.Metro) {
                assertEquals(((MetroTile) e.destination).metroDistanceCost, e.weight,
                        "Weight should be the metro distance cost.");
            } else {
                assertEquals(e.destination.distanceCost, e.weight, "Weight should be the distance cost.");
            }
        }
        // Increment mastery level
        approachMasteryPassed++;
    }

    @Test
    @DisplayName("APPROACHING MASTERY: testFPathGenerateGraphWithMetro")
    @Tag("score:1")
    @Order(22)
    public void testFPathGenerateGraphWithMetro() {
        buildWorld(lgMap, true);
        FastestPath fastest = new FastestPath(world.get(0));
        ArrayList<Graph.Edge> edges = fastest.g.getAllEdges();
        assertEquals(42, edges.size(), "There should be 42 edges. Note that MountainTile is not reachable.");
        for (Graph.Edge e : edges) {
            if (e.origin.getTileType() == TileType.Metro && e.destination.getTileType() == TileType.Metro) {
                assertEquals(((MetroTile) e.destination).metroTimeCost, e.weight,
                        "Weight should be the metro time cost.");
            } else {
                assertEquals(e.destination.timeCost, e.weight, "Weight should be the time cost.");
            }
        }
        // Increment mastery level
        approachMasteryPassed++;
    }

    @Test
    @DisplayName("MASTERY: testSSPathGenerateGraph")
    @Tag("score:2")
    @Order(23)
    public void testSSPathGenerateGraph() {
        buildWorld(lgMap, false);
        SafestShortestPath sspath = new SafestShortestPath(world.get(0), 100);
        ArrayList<Graph.Edge> edges;

        // Test costGraph
        edges = sspath.costGraph.getAllEdges();
        assertEquals(40, edges.size(), "There should be 40 edges in costGraph. Note that MountainTile is not reachable.");
        for (Graph.Edge e : edges) {
            assertEquals(e.destination.distanceCost, e.weight, "Weight should be the distance cost.");
        }

        // Test damageGraph
        edges = sspath.damageGraph.getAllEdges();
        assertEquals(40, edges.size(), "There should be 40 edges in damageGraph. Note that MountainTile is not reachable.");
        for (Graph.Edge e : edges) {
            assertEquals(e.destination.damageCost, e.weight, "Weight should be the damage cost.");
        }

        // Test aggregatedGraph
        edges = sspath.aggregatedGraph.getAllEdges();
        assertEquals(40, edges.size(), "There should be 40 edges in aggregatedGraph. Note that MountainTile is not reachable.");
        for (Graph.Edge e : edges) {
            assertEquals(e.destination.damageCost, e.weight, "Weight should be the damage cost.");
        }
        // Increment mastery level
        fullMasteryPassed++;
    }

    @Test
    @DisplayName("MASTERY: testSafeFindPath1")
    @Tag("score:1")
    @Order(24)
    public void testSafeFindPath1() {
        buildWorld(dgMap, false);
        SafestShortestPath shortest = new SafestShortestPath(world.get(0), 100);
        LinkedList<Tile> waypoints = new LinkedList<Tile>();
        ArrayList<Tile> path = shortest.findPath(world.get(0), waypoints);

        assertEquals(7, path.size(), "Path length (number of vertices, including start and end) should be 7.");

        Tile[] expectedPath = { tileArray[0][0], tileArray[0][1], tileArray[0][2], tileArray[1][2],
                tileArray[2][2], tileArray[2][3], tileArray[3][3] };
        for (int i = 0; i < expectedPath.length; ++i) {
            assertEquals(expectedPath[i], path.get(i), "Wrong path at position " + i);
        }

        assertEquals(6.0, shortest.costGraph.computePathCost(path), 0.001, "Path cost should be 6.");
        // Increment mastery level
        fullMasteryPassed++;
    }

    @Test
    @DisplayName("MASTERY: testSafeFindPath2")
    @Tag("score:1")
    @Order(25)
    public void testSafeFindPath2() {
        buildWorld(dgMap, false);
        SafestShortestPath shortest = new SafestShortestPath(world.get(0), 1);
        LinkedList<Tile> waypoints = new LinkedList<Tile>();
        ArrayList<Tile> path = shortest.findPath(world.get(0), waypoints);

        assertEquals(7, path.size(), "Path length (number of vertices, including start and end) should be 7.");
        assertEquals(16.0, shortest.costGraph.computePathCost(path), 0.001, "Path cost should be 16.");
        // Increment mastery level
        fullMasteryPassed++;
    }

    @Test
    @DisplayName("MASTERY: testSafeFindPath3")
    @Tag("score:1")
    @Order(26)
    public void testSafeFindPath3() {
        buildWorld(dgMap, false);
        SafestShortestPath shortest = new SafestShortestPath(world.get(0), 100);
        LinkedList<Tile> waypoints = new LinkedList<Tile>();
        waypoints.add(tileArray[3][2]);
        ArrayList<Tile> path = shortest.findPath(world.get(0), waypoints);

        assertEquals(7, path.size(), "Path length (number of vertices, including start and end) should be 7.");
        assertEquals(8.0, shortest.costGraph.computePathCost(path), 0.001, "Path cost should be 8.");
        // Increment mastery level
        fullMasteryPassed++;
    }

    @Test
    @DisplayName("MASTERY: testSafeFindPath4")
    @Tag("score:1")
    @Order(27)
    public void testSafeFindPath4() {
        buildWorld(dgMap, false);
        SafestShortestPath shortest = new SafestShortestPath(world.get(0), 100);
        LinkedList<Tile> waypoints = new LinkedList<Tile>();
        waypoints.add(tileArray[3][0]);
        waypoints.add(tileArray[3][2]);
        ArrayList<Tile> path = shortest.findPath(world.get(0), waypoints);

        assertEquals(7, path.size(), "Path length (number of vertices, including start and end) should be 7.");

        Tile[] expectedPath = { tileArray[0][0], tileArray[1][0], tileArray[2][0], tileArray[3][0],
                tileArray[3][1], tileArray[3][2], tileArray[3][3] };
        for (int i = 0; i < expectedPath.length; ++i) {
            assertEquals(expectedPath[i], path.get(i), "Wrong path at position " + i);
        }

        assertEquals(14.0, shortest.costGraph.computePathCost(path), 0.001, "Path cost should be 14.");
        // Increment mastery level
        fullMasteryPassed++;
    }

}
