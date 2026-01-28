package assignment2;

import assignment2.Caterpillar.Segment;

import java.lang.reflect.Field;


import assignment2.food.*;
import org.junit.jupiter.api.*;


import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.Random;
import java.util.concurrent.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class miniTester {
    private static int proficiencyPassed = 0;
    private static int approachMasteryPassed = 0;
    private Caterpillar gus;
    private Position initialPosition;
    private Color initialColor;
    private int goalLength;

    @Test
    @DisplayName("Count number of tests passed")
    @Order(100)
    void testBasicLevelPassed() {
        System.out.println("Proficiency passed: " + proficiencyPassed );
        System.out.println("Approaching Mastery passed: " + approachMasteryPassed);
    }

    @BeforeEach
    public void setUp() {
        initialPosition = new Position(1,1);
        initialColor = GameColors.GREEN;
        goalLength = 10;
        gus = new Caterpillar(initialPosition, initialColor, goalLength);
    }

    public void addSegment(Segment tail, Position p, Color c) {
        // adds a segment to the caterpillar
        try {
            Field tailNext = tail.getClass().getDeclaredField("next");
            tailNext.setAccessible(true);
            Segment toAdd = gus.new Segment(p, c);
            tailNext.set(tail, toAdd);
            gus.tail = toAdd;
            gus.length++;
        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    @Test
    @DisplayName("PROFICIENCY: Test Constructor 1")
    @Tag("score:1")
    @Order(1)
    // Test a caterpillar with a valid position, color, and goal as input is successfully created. This test verify the head attribute is valid or not
    public void testConstructorAndFeedingStage_1() {
        // TEST HERE
        assertEquals(initialPosition, gus.getHeadPosition(), "Caterpillar should start at the given position");
        proficiencyPassed++;
    }

    @Test
    @DisplayName("PROFICIENCY: Test Constructor 2 ")
    @Tag("score:1")
    @Order(2)
    // Test a caterpillar with a valid position, color, and goal as input is successfully created. This test verify the tail attribute is valid or not
    public void testConstructorAndFeedingStage_2() {
        // TEST HERE
        assertTrue(gus.head == gus.tail && gus.head != null, "Initially, the caterpillar tail should be identical to its head.");
        proficiencyPassed++;
    }

    @Test
    @DisplayName("PROFICIENCY: Test Constructor 3 ")
    @Tag("score:1")
    @Order(3)
    // Test a caterpillar with a valid position, color, and goal as input is successfully created. This test verify the length attribute is valid or not
    public void testConstructorAndFeedingStage_3() {
        // TEST HERE
        assertEquals(gus.getLength(), 1, "Caterpillar length should be equal to 1");
        proficiencyPassed++;
    }

    @Test
    @DisplayName("PROFICIENCY: Test Constructor 4")
    @Tag("score:1")
    @Order(4)
    // Test a caterpillar with a valid position, color, and goal as input is successfully created. This test verify the stage attribute is valid or not
    public void testConstructorAndFeedingStage_4() {
        // TEST HERE
        Assertions.assertEquals(EvolutionStage.FEEDING_STAGE, gus.getEvolutionStage(), "Caterpillar should start in the feeding stage");
        proficiencyPassed++;
    }

    @Test
    @DisplayName("PROFICIENCY: Test Constructor 5")
    @Tag("score:1")
    @Order(5)
    // Test a caterpillar with a valid position, color, and goal as input is successfully created. This test verify the color attribute is valid or not
    public void testConstructorAndFeedingStage_5() {
        // TEST HERE
        Color[] colors = gus.getColors();
        assertEquals(initialColor, colors[0], "The head color has not been correctly initialized");
        proficiencyPassed++;
    }

    @Test
    @DisplayName("PROFICIENCY: Test getSegmentColor() 1")
    @Tag("score:1")
    @Order(6)
    // Test getSegmentColor should return null if no segment is on the input position
    public void testGetSegmentColorNoSegment() {
        // TEST HERE
        Position p1 = new Position(this.initialPosition.getX(), this.initialPosition.getY()-1);
        Position p2 = new Position(this.initialPosition.getX()-1, this.initialPosition.getY()-1);

        Position p3 = new Position(this.initialPosition.getX()+1, this.initialPosition.getY()+1);

        addSegment(gus.tail, p1, GameColors.RED);
        addSegment(gus.tail, p2, GameColors.BLUE);

        assertNull(gus.getSegmentColor(p3), "Should return null for no segment at position");
        proficiencyPassed++;
    }

    @Test
    @DisplayName("PROFICIENCY: Test eat fruit 1")
    @Tag("score:3")
    @Order(11)
        // Test a segment of the same color of the fruit and with the correct position is added correctly to the caterpillar
    void testEatFruit1() {
        // TEST HERE
        Random gen = new Random();
        int x = gen.nextInt();
        int y = gen.nextInt();

        int i = gen.nextInt(GameColors.SEGMENT_COLORS.length);
        Color fruitColor = GameColors.SEGMENT_COLORS[i];

        Position toAdd = new Position(x,y);

        gus.positionsPreviouslyOccupied.push(toAdd);
        Fruit fruit = new Fruit(fruitColor);

        gus.eat(fruit);

        Color[] colors=gus.getColors();
        Position[] positions = gus.getPositions();
        assertTrue(fruitColor == colors[1] && toAdd == positions[1], "The new segment does not have the right color or position");
        proficiencyPassed++;
        proficiencyPassed++;
        proficiencyPassed++;
    }

    @Test
    @DisplayName("PROFICIENCY: Test eat fruit 2")
    @Tag("score:1")
    @Order(12)
        // Test the length of the caterpillar is updated after eating a fruit
    void testEatFruit2() {
        // TEST HERE
        gus.positionsPreviouslyOccupied.push(new Position(1, 0));
        Fruit fruit = new Fruit(GameColors.RED);
        gus.eat(fruit);

        assertEquals(2, gus.getLength(), "The length of the caterpillar should be updated");
        proficiencyPassed++;

    }

    private static int getStackSize(MyStack<?> stack) {
        try {
            Field sizeField = MyStack.class.getDeclaredField("size");
            sizeField.setAccessible(true);
            return (int) sizeField.get(stack);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Unable to read MyStack.size via reflection", e);
        }
    }

    @Test
    @DisplayName("PROFICIENCY: Test eat fruit 3")
    @Tag("score:1")
    @Order(13)
        // Test the position in previouslyOccupiedStack is being popped after eating a fruit
    void testEatFruit3() {
        // TEST HERE
        Random gen = new Random();
        int x = gen.nextInt();
        int y = gen.nextInt();

        int i = gen.nextInt(GameColors.SEGMENT_COLORS.length);
        Color fruitColor = GameColors.SEGMENT_COLORS[i];

        Position toAdd = new Position(x, y);

        gus.positionsPreviouslyOccupied.push(new Position(x - 1, y));
        gus.positionsPreviouslyOccupied.push(new Position(x, y - 1));
        gus.positionsPreviouslyOccupied.push(toAdd);
        Fruit fruit = new Fruit(fruitColor);

        gus.eat(fruit);


        assertEquals(2, getStackSize(gus.positionsPreviouslyOccupied), "The stack should be smaller after eating the fruit");

    }

    @Test
    @DisplayName("APPROACHING MASTERY: Test eat fruit 4")
    @Tag("score:1")
    @Order(14)
        // Test the tail field has been correctly updated
    void testEatFruit4() {
        Random gen = new Random();
        int x = gen.nextInt();
        int y = gen.nextInt();

        int i = gen.nextInt(GameColors.SEGMENT_COLORS.length);
        Color fruitColor = GameColors.SEGMENT_COLORS[i];

        Position toAdd = new Position(x,y);

        Segment oldTail = gus.tail;
        gus.positionsPreviouslyOccupied.push(toAdd);
        Fruit fruit = new Fruit(fruitColor);

        gus.eat(fruit);

        assertTrue(gus.tail != oldTail, "The color of the tail should be the same as the color of the fruit");

        approachMasteryPassed++;
    }

    @Test
    @DisplayName("PROFICIENCY: Test eat fruit 5")
    @Tag("score:1")
    @Order(15)
        // Test that if eating a fruit makes the caterpillar reach its goal, then its stage is updated correctly to BUTTERFLY
    void testEatFruit5() {
        gus.goal = 2;

        gus.positionsPreviouslyOccupied.push(new Position(1, 0));
        Fruit fruit = new Fruit(GameColors.RED);
        gus.eat(fruit);

        assertEquals(EvolutionStage.BUTTERFLY, gus.getEvolutionStage(), "The stage of the caterpillar should be update to butterfly if it reaches the goal");

        proficiencyPassed++;
    }

    @Test
    @DisplayName("APPROACHING MASTERY: Test eat fruit 6")
    @Tag("score:1")
    @Order(16)
        // Test a more complex case of caterpillar reach the goal, move() and eat() would be called multiple times
    void testEatFruit6() {
        gus.goal = 5;

        Random gen = new Random();
        int x = gen.nextInt();
        int y = gen.nextInt();

        int i = gen.nextInt(GameColors.SEGMENT_COLORS.length);

        Color[] fruitColors = new Color[4];
        Position[] newPos = new Position[4];

        for (int j = 0; j < 4; j++) {
            newPos[newPos.length - 1 - j] = new Position(x - j, y);
            fruitColors[j] = GameColors.SEGMENT_COLORS[(i + j) % GameColors.SEGMENT_COLORS.length];
            gus.positionsPreviouslyOccupied.push(newPos[newPos.length - 1 - j]);
        }

        for (int j = 0; j < 4; j++) {
            Fruit fruit = new Fruit(fruitColors[j]);
            gus.eat(fruit);
        }

        assertEquals(EvolutionStage.BUTTERFLY, gus.getEvolutionStage(), "The stage of the caterpillar should update to butterfly if it reaches the goal");
        Color[] colors = gus.getColors();
        Position[] pos = gus.getPositions();
        for (int j = 1; j <= 4; j++) {
            assertTrue(colors[j] == fruitColors[j - 1] && pos[j].equals(newPos[j - 1]), "the colors and/or the positions of the segments of the caterpillar are incorrect");
        }

        approachMasteryPassed++;

    }

    @Test
    @DisplayName("PROFICIENCY: Test eat pickle 1")
    @Tag("score:1")
    @Order(17)
        // Test that the color of the segments did not change after eating the pickle
    void testEatPickle() {
        // TEST HERE
        Random gen = new Random();

        int i = gen.nextInt(GameColors.SEGMENT_COLORS.length);
        Color newColor = GameColors.SEGMENT_COLORS[i];

        int x = initialPosition.getX();
        int y = initialPosition.getY();

        gus.positionsPreviouslyOccupied.push(new Position(x+2, y));
        addSegment(gus.tail, new Position(x+1, y), newColor);

        Pickle pickle = new Pickle();
        gus.eat(pickle);

        Color []c=gus.getColors();
        assertEquals(initialColor, c[0], "The color of the caterpillar should not change");
        assertEquals(newColor, c[1], "The color of the caterpillar should not change");
        proficiencyPassed++;
    }


    @Test
    @DisplayName("PROFICIENCY: Test eat pickle 2")
    @Tag("score:1")
    @Order(18)
        // Test that the position of the segments update correctly after eating the pickle
    void testEatPickleRetract() {
        Random gen = new Random();

        int i = gen.nextInt(GameColors.SEGMENT_COLORS.length);
        Color newColor = GameColors.SEGMENT_COLORS[i];

        int x = initialPosition.getX();
        int y = initialPosition.getY();

        gus.positionsPreviouslyOccupied.push(new Position(x+2, y));
        addSegment(gus.tail, new Position(x+1, y), newColor);

        Pickle pickle = new Pickle();
        gus.eat(pickle);

        Position []pos=gus.getPositions();

        assertEquals(new Position(x+1, y), pos[0], "The caterpillar should move one step backward");
        assertEquals(new Position(x+2, y), pos[1], "The caterpillar should move one step backward");
        proficiencyPassed++;
    }

    @Test
    @DisplayName("PROFICIENCY: Test eat lollipop 2")
    @Order(22)
    @Tag("score:1")
        // Test all segment colors are shuffled as expected with a fix seed of random generator after eating the lollipop with a caterpillar that only has 1 segment
    void testEatLollipopWithOneSegment() {
        // TEST HERE

        // Eat a lollipop
        Lollipop lolly = new Lollipop();
        gus.eat(lolly);

        // Get the state after eating the lollipop
        Color headColorAfter = gus.getColors()[0];

        // Assert that the caterpillar's color did not change
        assertEquals(initialColor, headColorAfter, "Caterpillar of length 1. Its color should not change after eating a lollipop");
        proficiencyPassed++;
    }

    @Test
    @DisplayName("PROFICIENCY: Test eat lollipop 3")
    @Order(23)
    @Tag("score:1")
        //  Test the segment color of the caterpillar is always updating correctly while eating several lollipop
    void testEatLollipopMultiple() {
        // TEST HERE
        int x = gus.getHeadPosition().getX();
        int y = gus.getHeadPosition().getY();

        int i;
        for (i=0; i<GameColors.SEGMENT_COLORS.length; i++) {
            if (this.initialColor == GameColors.SEGMENT_COLORS[i])
                break;
        }

        Color[] newColors = new Color[5];

        newColors[0] = initialColor;

        for (int j = 1; j<5; j++) {
            newColors[j] = GameColors.SEGMENT_COLORS[(i+j) % GameColors.SEGMENT_COLORS.length];
            addSegment(gus.tail, new Position(x, y+j), newColors[j]);
        }

        Color[] first = {newColors[3],newColors[4],newColors[0],newColors[1],newColors[2]};
        Color[] second = {newColors[0],newColors[3],newColors[4],newColors[2],newColors[1]};
        Color[] third = {newColors[2],newColors[1],newColors[0],newColors[4],newColors[3]};

        Color[][] expectedShuffles = {first, second, third};
        Color[][] shuffles = new Color[3][];

        Caterpillar.randNumGenerator = new Random(23);
        gus.eat(new Lollipop());
        shuffles[0] = gus.getColors();

        gus.eat(new Lollipop());
        shuffles[1] = gus.getColors();

        gus.eat(new Lollipop());
        shuffles[2] = gus.getColors();

        for (int k=0; k<3; k++) {
            for (int h=0; h<5; h++) {
                assertEquals(expectedShuffles[k][h], shuffles[k][h], "Colors were not shuffled correctly");
            }
        }

        proficiencyPassed++;

    }

    @Test
    @DisplayName("PROFICIENCY: Test eat icecream 1")
    @Order(24)
    @Tag("score:1")
        // Test head and tail have been updated correctly (i.e. they have been swapped) after eating the ice cream
    void testEatIceCream1() {
        addSegment(gus.tail, new Position(1, 0), GameColors.RED);
        Segment originalHead = gus.head;
        Segment originalTail = gus.tail;

        gus.eat(new IceCream());


        assertEquals(originalHead, gus.tail, "head and tail should be swapped");
        assertEquals(originalTail,gus.head,"head and tail should be swapped");

        proficiencyPassed++;
    }

    @Test
    @DisplayName("PROFICIENCY: Test eat icecream 3")
    @Order(26)
    @Tag("score:1")
        // Test the head of the caterpillar turns blue after eating the ice cream
    void testEatIceCreamNewHeadBlue() {
        gus = new Caterpillar(new Position(1,1), GameColors.GREEN, 5);
        gus.positionsPreviouslyOccupied.push(new Position(2, 0));
        addSegment(gus.tail,new Position(1,0),GameColors.YELLOW);
        gus.eat(new IceCream());

        assertEquals(GameColors.BLUE, gus.getColors()[0],
                "The new head segment should turn blue after eating ice cream.");
        proficiencyPassed++;
    }

    @Test
    @DisplayName("PROFICIENCY: Test eat icecream 4")
    @Order(27)
    @Tag("score:1")
        // Test after eating the ice cream, the stack is empty
    void testEatIceCreamClearStack() {
        int x = gus.getHeadPosition().getX();
        int y = gus.getHeadPosition().getY();

        Random gen = new Random();

        int n = 2 + gen.nextInt(5);

        for (int i=1; i<=n; i++) {
            gus.positionsPreviouslyOccupied.push(new Position(x-i, y));
        }

        gus.eat(new IceCream());

        assertTrue(gus.positionsPreviouslyOccupied.empty(),
                "The stack of positions should be cleared after eating ice cream.");
        proficiencyPassed++;
    }

    @Test
    @DisplayName("PROFICIENCY: Test eat swissCheese 1")
    @Tag("score:1")
    @Order(29)
        // Test swissCheese with a caterpillar that has an even # of segments. The length has been updated correctly.
    void testEatSwissCheese1() {
        // TEST HERE
        int x = gus.getHeadPosition().getX();
        int y = gus.getHeadPosition().getY();

        int i;
        for (i=0; i<GameColors.SEGMENT_COLORS.length; i++) {
            if (this.initialColor == GameColors.SEGMENT_COLORS[i])
                break;
        }

        Random gen = new Random();
        int numSegments = 2*(3 + gen.nextInt(5));

        Color[] originalColors = new Color[numSegments];
        originalColors[0] = initialColor;


        for (int j = 1; j<numSegments; j++) {
            originalColors[j] = GameColors.SEGMENT_COLORS[(i+j) % GameColors.SEGMENT_COLORS.length];
            addSegment(gus.tail, new Position(x, y+j), originalColors[j]);
        }

        Position[] originalPositions = gus.getPositions();

        gus.eat(new SwissCheese());

        assertEquals(numSegments/2, gus.getLength(), "The length of the caterpillar is incorrect.");
        proficiencyPassed++;
    }


    @Test
    @DisplayName("APPROACHING MASTERY: Test eat swissCheese 2")
    @Tag("score:1")
    @Order(30)
        // Test swissCheese with a caterpillar that has an even # of segments. The body of the caterpillar is still connected correctly.
    void testEatSwissCheese2() {
        // TEST HERE
        // add four segments
        int x = gus.getHeadPosition().getX();
        int y = gus.getHeadPosition().getY();

        int i;
        for (i=0; i<GameColors.SEGMENT_COLORS.length; i++) {
            if (this.initialColor == GameColors.SEGMENT_COLORS[i])
                break;
        }

        Random gen = new Random();
        int numSegments = 2*(3 + gen.nextInt(5));

        Color[] originalColors = new Color[numSegments];
        originalColors[0] = initialColor;


        for (int j = 1; j<numSegments; j++) {
            originalColors[j] = GameColors.SEGMENT_COLORS[(i+j) % GameColors.SEGMENT_COLORS.length];
            addSegment(gus.tail, new Position(x, y+j), originalColors[j]);
        }

        Position[] originalPositions = gus.getPositions();

        gus.eat(new SwissCheese());
        Position[] positions = gus.getPositions();
        // compare positions using getDistance() make sure they are not further than 1
        for (int j = 0; j < positions.length - 1; j++) {
            assertTrue(Position.getDistance(positions[j],positions[j + 1]) <= 1, "The body of the caterpillar should still be connected after eating Swiss cheese.");
        }

        assertEquals(numSegments / 2, gus.getPositions().length, "Incorrect number of positions, or positions not proprerly connected");
        approachMasteryPassed++;
    }

    @Test
    @DisplayName("PROFICIENCY: Test eat Cake 1")
    @Tag("score:1")
    @Order(36)
	/*
	 * A caterpillar of length 2 and with goal 10, eats a cake with energy 5, " +
			"while having 8 positions in the stack that can all be used by the method. " +
			"The length of the caterpillar is now 7.
	 */
    public void testEatCakeWithSufficientEnergy1() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            Future<?> future = executor.submit(() -> {
                // TEST HERE
                int x = gus.getHeadPosition().getX();
                int y = gus.getHeadPosition().getY();

                addSegment(gus.tail, new Position(x+1,y), GameColors.RED);

                for (int i=1; i<=8; i++) {
                    gus.positionsPreviouslyOccupied.push(new Position(x+1, y+i));
                }

                gus.goal = 10;

                Cake cake = new Cake(5);
                gus.eat(cake);


                assertEquals(7, gus.getLength(), "Caterpillar should have grown by five segments after eating cake");
                proficiencyPassed++;
            });

            // Set a timeout for the thread execution (e.g., 10 seconds)
            future.get(500, TimeUnit.MILLISECONDS);
        } catch (
                TimeoutException e) {
            // Thread execution took longer than the specified timeout
            fail("Test timed out");
        } catch (Exception e) {
            // Handle other exceptions
            fail("Test failed with an exception: " + e.getMessage());
        } finally {
            executor.shutdownNow(); // Interrupt the thread
        }
    }

    @Test
    @DisplayName("APPROACHING MASTERY: Test eat Cake 2")
    @Tag("score:1")
    @Order(37)
	/*
	 * A caterpillar of length 2 and with goal 10, eats a cake with energy 5, " +
			"while having 8 positions in the stack that can all be used by the method. " +
			"The stage of the caterpillar is FEEDING_STAGE.
	 */
    public void testEatCakeWithSufficientEnergy2() {
        // TEST HERE
        int x = gus.getHeadPosition().getX();
        int y = gus.getHeadPosition().getY();

        addSegment(gus.tail, new Position(x+1,y), GameColors.RED);

        for (int i=1; i<=8; i++) {
            gus.positionsPreviouslyOccupied.push(new Position(x+1, y+i));
        }

        gus.goal = 10;

        Cake cake = new Cake(5);
        gus.eat(cake);

        assertEquals(EvolutionStage.FEEDING_STAGE, gus.getEvolutionStage(), "The caterpillar should be in the FEEDING_STAGE after eating a cake with sufficient energy.");
        approachMasteryPassed++;
    }
}
