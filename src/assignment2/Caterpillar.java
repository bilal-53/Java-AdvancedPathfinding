package assignment2;

import java.awt.Color;
import java.util.Random;

import assignment2.food.*;

public class Caterpillar {
    // All the fields have been declared public for testing purposes
    public Segment head;
    public Segment tail;
    public int length;
    public EvolutionStage stage;

    public MyStack<Position> positionsPreviouslyOccupied;
    public int goal;
    public int turnsNeededToDigest;

    public static Random randNumGenerator = new Random(1);

    // Creates a Caterpillar with one Segment. It is up to students to decide how to implement this.
    public Caterpillar(Position p, Color c, int goal) {
        Segment newSegment = new Segment(p, c);
        this.goal = goal;
        this.head = newSegment;
        this.tail = newSegment;
        this.stage = EvolutionStage.FEEDING_STAGE;
        this.positionsPreviouslyOccupied = new MyStack<>();
        length = 1;
    }

    public EvolutionStage getEvolutionStage() {return this.stage;}

    public Position getHeadPosition() {return this.head.position;}

    public int getLength() {return this.length;}

    // returns the color of the segment in position p. Returns null if such segment does not exist
    public Color getSegmentColor(Position p) {
        Segment temp = this.head;
        while (temp != null) {
            if (temp.position.equals(p)){
                return temp.color;
            }
            temp = temp.next;
        }
        return null;
    }

    // Methods that need to be added for the game to work
    public Color[] getColors() {
        Color[] cs = new Color[this.length];
        Segment chk = this.head;
        for (int i = 0; i < this.length; i++) {
            cs[i] = chk.color;
            chk = chk.next;
        }
        return cs;
    }

    public Position[] getPositions() {
        Position[] ps = new Position[this.length];
        Segment chk = this.head;
        for (int i = 0; i < this.length; i++) {
            ps[i] = chk.position;
            chk = chk.next;
        }
        return ps;
    }

    // shift all Segments to the previous Position while maintaining the old color
    // the length of the caterpillar is not affected by this
    public void move(Position p) {
        if (Position.getDistance(head.position, p) > 1){
            throw new IllegalStateException("Input position " + p + " is unreachable (you dumb Comp 250 student!!) from " + head.position);
        }

        if (Position.getDistance(head.position, p) == 0){
            return;

        }

        Segment currentSegment = this.head;
        Position newPosition = p;

        while (currentSegment != null) {
            if (!tail.position.equals(p) && currentSegment.position.equals(p)) {
                stage = EvolutionStage.ENTANGLED;
                return;
            }
            Position tempPosition = currentSegment.position;
            currentSegment.position = newPosition;
            newPosition = tempPosition;
            currentSegment = currentSegment.next;
        }

        positionsPreviouslyOccupied.push(newPosition);

        if (turnsNeededToDigest > 0 && isPositionOccupied(positionsPreviouslyOccupied.peek())) {
            addRandomSegment();
            turnsNeededToDigest--;
            if (goal <= length) {
                stage = EvolutionStage.BUTTERFLY;
                turnsNeededToDigest = 0;
            }
        }
        if (stage == EvolutionStage.GROWING_STAGE && turnsNeededToDigest == 0) {
            stage = EvolutionStage.FEEDING_STAGE;
        }

        System.out.println("I moved it to " + this);
        System.out.println("Here are the previously occupied positions: " + this.positionsPreviouslyOccupied + "\n");
    }

    // a segment of the fruit's color is added at the end
    public void eat(Fruit f) {
        Position previouslyOccupied = this.positionsPreviouslyOccupied.pop();

        Segment newSegment = new Segment(previouslyOccupied, f.getColor());
        this.tail.next = newSegment;
        this.tail = newSegment;
        length++;
        if (length >= goal) {
            stage = EvolutionStage.BUTTERFLY;
        }
        System.out.println("fruit eaten" + this);
    }

    // the caterpillar moves one step backwards because of sourness
    public void eat(Pickle p) {
        Segment currentSegment = this.head;
        while (currentSegment.next != null) {
            Segment nextSegment = currentSegment.next;
            currentSegment.position = nextSegment.position;
            currentSegment = nextSegment;
        }
        currentSegment.position = positionsPreviouslyOccupied.pop();
        System.out.println("pickle eaten " + this);
    }

    // all the caterpillar's colors shuffle around
    public void eat(Lollipop lolly) {
        Color[] colors = getColors();
        for (int i = length - 1; i > 0; i--) {
            int j = randNumGenerator.nextInt(i + 1);
            Color temp = colors[i];
            colors[i] = colors[j];
            colors[j] = temp;
        }

        Segment chk = this.head;
        for (int i = 0; i < this.length; i++) {
            chk.color = colors[i];
            chk = chk.next;
        }
        System.out.println("lollipop eaten " + this);
    }

    // brain freeze!!
    // It reverses and its (new) head turns blue
    public void eat(IceCream gelato) {
        Segment curr = this.head;

        Segment prev = null;
        while (curr != null) {
            Segment temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }

        Segment temp = head;
        head = tail;
        tail = temp;

        head.color = GameColors.BLUE;

        positionsPreviouslyOccupied.clear();

        System.out.println("icecream eaten " + this);
        System.out.println("Here are the previously occupied positions: " + this.positionsPreviouslyOccupied + "\n");
    }

    // the caterpillar embodies a slide of Swiss cheese loosing half of its segments.
    public void eat(SwissCheese cheese) {
        if (length < 2){
            return;
        }

        Segment fast = this.head;
        Segment slow = this.head;
        while (fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            slow.color = fast.color;
            length--;
        }

        tail = slow;
        MyStack<Position> tempStack = new MyStack<>();
        while (slow.next != null) {
            slow = slow.next;
            tempStack.push(slow.position);
        }
        while (!tempStack.empty()) {
            positionsPreviouslyOccupied.push(tempStack.pop());
        }
        tail.next = null;
        length--;
        System.out.println("swisscheese eaten " + this);
    }

    public void eat(Cake cake) {
        stage = EvolutionStage.GROWING_STAGE;

        int energy = cake.getEnergyProvided();
        int grown = 0;

        for (int i = 0; i < energy && !positionsPreviouslyOccupied.empty(); i++) {
            boolean occupied = isPositionOccupied(positionsPreviouslyOccupied.peek());

            if (occupied) {
                turnsNeededToDigest = energy - grown;
                System.out.println("cake eaten, break at occupied " + this);
                return;
            }

            addRandomSegment();
            grown++;

            if (length >= goal) {
                stage = EvolutionStage.BUTTERFLY;
                System.out.println("cake eaten, it is now a butterfly slay!! " + this);
                return;
            }
        }

        if (grown == energy) {
            stage = EvolutionStage.FEEDING_STAGE;
            turnsNeededToDigest = 0;
            System.out.println("cake eaten, total consumed energy: " + grown + " " + this);
        } else {
            turnsNeededToDigest = energy - grown;
            System.out.println("cake eaten, total number of turns needed to digest " + turnsNeededToDigest + " " + this);
        }
    }

    private void addRandomSegment() {
        if (positionsPreviouslyOccupied.empty()){
            return;
        }
        Color randomColor = GameColors.SEGMENT_COLORS[randNumGenerator.nextInt(GameColors.SEGMENT_COLORS.length)];
        Segment newSegment = new Segment(positionsPreviouslyOccupied.pop(),randomColor);
        tail.next = newSegment;
        tail = newSegment;
        length++;
    }

    private boolean isPositionOccupied(Position position) {
        Segment currentSegment = this.head;
        while (currentSegment != null) {
            if (currentSegment.position.equals(position)) {
                return true;
            }
            currentSegment = currentSegment.next;
        }
        return false;
    }

    // This nested class was declared public for testing purposes
    public class Segment {
        private Position position;
        private Color color;
        private Segment next;

        public Segment(Position p, Color c) {
            this.position = p;
            this.color = c;
        }
    }

    public String toString() {
        Segment s = this.head;

        String snake = "";
        while (s != null) {
            String coloredPosition = GameColors.colorToANSIColor(s.color) +
                    s.position.toString() + GameColors.colorToANSIColor(Color.WHITE);
            snake = coloredPosition + " " + snake;
            s = s.next;
        }
        return snake;
    }

    private int getStackSize() {
        String stackString = positionsPreviouslyOccupied.toString();
        int size = 1;
        for (int i = 0; i < stackString.length(); i++) {
            if (stackString.charAt(i) == ',') size++;
        }
        return size;
    }

    public static void main(String[] args) {
        Position startingPoint = new Position(3, 2);
        Caterpillar gus = new Caterpillar(startingPoint, GameColors.GREEN, 10);

        System.out.println("1) Gus: " + gus);
        System.out.println("Stack of previously occupied positions: " + gus.positionsPreviouslyOccupied);

        gus.move(new Position(3, 1));
        gus.eat(new Fruit(GameColors.RED));
        gus.move(new Position(2, 1));
        gus.move(new Position(1, 1));
        gus.eat(new Fruit(GameColors.YELLOW));

        System.out.println("\n2) Gus: " + gus);
        System.out.println("Stack of previously occupied positions: " + gus.positionsPreviouslyOccupied);

        gus.move(new Position(1, 2));
        gus.eat(new IceCream());

        System.out.println("\n3) Gus: " + gus);
        System.out.println("Stack of previously occupied positions: " + gus.positionsPreviouslyOccupied);

        gus.move(new Position(3, 1));
        gus.move(new Position(3, 2));
        gus.eat(new Fruit(GameColors.ORANGE));

        System.out.println("\n4) Gus: " + gus);
        System.out.println("Stack of previously occupied positions: " + gus.positionsPreviouslyOccupied);

        gus.move(new Position(2, 2));
        gus.eat(new SwissCheese());

        System.out.println("\n5) Gus: " + gus);
        System.out.println("Stack of previously occupied positions: " + gus.positionsPreviouslyOccupied);

        gus.move(new Position(2, 3));
        gus.eat(new Cake(4));

        System.out.println("\n6) Gus: " + gus);
        System.out.println("Stack of previously occupied positions: " + gus.positionsPreviouslyOccupied);
    }
}


