package case_study_interfaces;

public class Cat {
    private String name;
    private int age;

    public Cat() {
        this.name = "Grumpy";
        this.age = 7;
    }

    public Cat(String n) {
        this.name = n;
        this.age = 0;
    }

    public Cat(String n, int a) {
        this.name = n;
        this.age = a;
    }

    /*
     * ADD CODE HERE
     */


    public String toString() {
        return "(" + this.name + ", " + this.age + ")";
    }

    public static void main(String[] args) {
        SLinkedList<Cat> myCats = new SLinkedList<Cat>();
        myCats.addFirst(new Cat());
        myCats.addFirst(new Cat("Tiger"));
        myCats.addFirst(new Cat("Spritz", 12));
        myCats.addFirst(new Cat("Kitty", 2));
        myCats.addFirst(new Cat("Ginger", 2));

        System.out.println(myCats);
    }
}