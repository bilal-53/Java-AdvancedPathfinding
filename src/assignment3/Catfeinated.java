package assignment3;

import java.util.ArrayList;
import java.util.Iterator;

public class Catfeinated implements Iterable<Cat> {

    public CatNode root;

    public Catfeinated() {
    }

    public Catfeinated(CatNode dNode) {
        this.root = dNode;
    }

    // Constructor that makes a shallow copy of a Catfeinated cafe
    // New CatNode objects, but same Cat objects
    public Catfeinated(Catfeinated cafe) {
        if (cafe == null || cafe.root == null) {
            this.root = null;
        } else {
            // helper to copy nodes recursively (creates new CatNode objects but reuses Cat references)
            this.root = copyNode(cafe.root, null);
        }
    }

    private CatNode copyNode(CatNode node, CatNode parent) {
        if (node == null) {
            return null;
        }

        CatNode newNode = new CatNode(node.catEmployee); // shallow copy of Cat reference
        newNode.parent = parent;
        newNode.junior = copyNode(node.junior, newNode);
        newNode.senior = copyNode(node.senior, newNode);
        return newNode;
    }

    // add a cat to the cafe database
    public void hire(Cat c) {
        if (root == null) {
            root = new CatNode(c);
        } else {
            root = root.hire(c);
        }
    }

    // removes a specific cat from the cafe database
    public void retire(Cat c) {
        if (root != null) {
            root = root.retire(c);
        }
    }

    // get the oldest hire in the cafe
    public Cat findMostSenior() {
        if (root == null) {
            return null;
        }

        return root.findMostSenior();
    }

    // get the newest hire in the cafe
    public Cat findMostJunior() {
        if (root == null) {
            return null;
        }

        return root.findMostJunior();
    }

    // returns a list of cats containing the top numOfCatsToHonor cats
    // in the cafe with the thickest fur. Cats are sorted in descending
    // order based on their fur thickness.
    public ArrayList<Cat> buildHallOfFame(int numOfCatsToHonor) {

        ArrayList<Cat> cats = new ArrayList<>();

        for (Cat c : this) {
            cats.add(c);
        }

        cats.sort((c1, c2) -> Integer.compare(c2.getFurThickness(), c1.getFurThickness()));

        if (numOfCatsToHonor >= cats.size()) {
            return cats;
        }

        ArrayList<Cat> result = new ArrayList<>();

        for (int i = 0; i < numOfCatsToHonor; i++) {
            result.add(cats.get(i));
        }

        return result;
    }

    // Returns the expected grooming cost the cafe has to incur in the next numDays days
    public double budgetGroomingExpenses(int numDays) {

        double total = 0;

        for (Cat c : this) {
            if (c.getDaysToNextGrooming() <= numDays) {
                total += c.getExpectedGroomingCost();
            }
        }

        return total;
    }

    // returns a list of list of Cats.
    // The cats in the list at index 0 need be groomed in the next week.
    // The cats in the list at index i need to be groomed in i weeks.
    // Cats in each sublist are listed in from most senior to most junior.
    public ArrayList<ArrayList<Cat>> getGroomingSchedule() {

        ArrayList<ArrayList<Cat>> res = new ArrayList<>();

        if (root == null) {
            return res;
        }

        for (Cat c : this) {
            int week = c.getDaysToNextGrooming() / 7;

            while (res.size() <= week) {
                res.add(new ArrayList<>());
            }

            res.get(week).add(c);
        }

        return res;
    }

    public Iterator<Cat> iterator() {
        return new CatfeinatedIterator();
    }

    public static class CatNode {

        public Cat catEmployee;
        public CatNode junior;
        public CatNode senior;
        public CatNode parent;

        public CatNode(Cat c) {
            this.catEmployee = c;
            this.junior = null;
            this.senior = null;
            this.parent = null;
        }

        // add the c to the tree rooted at this and returns the root of the resulting tree
        public CatNode hire(Cat c) {

            CatNode root = this;

            if (c.getMonthHired() < catEmployee.getMonthHired()) {

                if (senior == null) {
                    senior = new CatNode(c);
                    senior.parent = this;
                    root = upheap(senior);
                } else {
                    root = senior.hire(c);
                }

            } else {

                if (junior == null) {
                    junior = new CatNode(c);
                    junior.parent = this;
                    root = upheap(junior);
                } else {
                    root = junior.hire(c);
                }
            }

            // The upheap might return a NEW ROOT.
            while (root.parent != null) {
                root = root.parent;
            }

            return root;
        }

        private CatNode upheap(CatNode node) {

            while (node.parent != null &&
                    node.catEmployee.getFurThickness() > node.parent.catEmployee.getFurThickness()) {

                CatNode parent = node.parent;
                CatNode grand = parent.parent;

                if (node == parent.senior) {

                    // rotate right
                    CatNode B = node.junior;
                    node.junior = parent;
                    parent.senior = B;

                    if (B != null) {
                        B.parent = parent;
                    }

                } else {

                    // rotate left
                    CatNode B = node.senior;
                    node.senior = parent;
                    parent.junior = B;

                    if (B != null) {
                        B.parent = parent;
                    }
                }

                node.parent = grand;
                parent.parent = node;

                if (grand != null) {

                    if (grand.senior == parent) {
                        grand.senior = node;
                    } else {
                        grand.junior = node;
                    }
                }
            }

            return node;
        }

        // remove c from the tree rooted at this and returns the root of the resulting tree
        public CatNode retire(Cat c) {

            if (c.getMonthHired() > this.catEmployee.getMonthHired()) {

                if (junior != null) {
                    junior = junior.retire(c);

                    if (junior != null) {
                        junior.parent = this;
                    }
                }

                return downheap(this);

            } else if (c.getMonthHired() < this.catEmployee.getMonthHired()) {

                if (senior != null) {
                    senior = senior.retire(c);

                    if (senior != null) {
                        senior.parent = this;
                    }
                }

                return downheap(this);

            } else {

                if (junior == null && senior == null) {
                    return null;
                }

                if (senior == null) {
                    CatNode ch = junior;
                    ch.parent = parent;
                    return ch;
                }

                if (junior == null) {
                    CatNode ch = senior;
                    ch.parent = parent;
                    return ch;
                }

                CatNode p = findMostSeniorNode(this.junior);
                this.catEmployee = p.catEmployee;
                junior = junior.retire(p.catEmployee);

                if (junior != null) {
                    junior.parent = this;
                }

                return downheap(this);
            }
        }

        private CatNode findMostSeniorNode(CatNode n) {
            CatNode cur = n;

            while (cur.senior != null) {
                cur = cur.senior;
            }

            return cur;
        }

        private CatNode rotateRight(CatNode p) {
            CatNode l = p.junior;
            CatNode b = l.senior;
            l.senior = p;
            p.junior = b;
            CatNode g = p.parent;
            l.parent = g;
            p.parent = l;

            if (b != null) {
                b.parent = p;
            }

            if (g != null) {
                if (g.junior == p) {
                    g.junior = l;
                } else {
                    g.senior = l;
                }
            }

            return l;
        }

        private CatNode rotateLeft(CatNode p) {
            CatNode r = p.senior;
            CatNode b = r.junior;
            r.junior = p;
            p.senior = b;
            CatNode g = p.parent;
            r.parent = g;
            p.parent = r;

            if (b != null) {
                b.parent = p;
            }

            if (g != null) {
                if (g.junior == p) {
                    g.junior = r;
                } else {
                    g.senior = r;
                }
            }

            return r;
        }

        private CatNode downheap(CatNode n) {

            if (n == null) {
                return null;
            }

            CatNode root = n;

            while (true) {
                CatNode best = root;

                if (root.junior != null &&
                        root.junior.catEmployee.getFurThickness() > best.catEmployee.getFurThickness()) {
                    best = root.junior;
                }

                if (root.senior != null &&
                        root.senior.catEmployee.getFurThickness() > best.catEmployee.getFurThickness()) {
                    best = root.senior;
                }

                if (best == root) {
                    break;
                }

                if (best == root.junior) {
                    root = rotateRight(root);
                } else {
                    root = rotateLeft(root);
                }
            }

            return root;
        }

        // find the cat with highest seniority in the tree rooted at this
        public Cat findMostSenior() {
            CatNode node = this;

            while (node.senior != null) {
                node = node.senior;
            }

            return node.catEmployee;
        }

        // find the cat with lowest seniority in the tree rooted at this
        public Cat findMostJunior() {
            CatNode cur = this;

            while (cur.junior != null) {
                cur = cur.junior;
            }

            return cur.catEmployee;
        }

        // Feel free to modify the toString() method if you'd like to see something else displayed.
        public String toString() {
            String result = this.catEmployee.toString() + "\n";

            if (this.junior != null) {
                result += "junior than " + this.catEmployee.toString() + " :\n";
                result += this.junior.toString();
            }

            if (this.senior != null) {
                result += "senior than " + this.catEmployee.toString() + " :\n";
                result += this.senior.toString();
            }

            /* if (this.parent != null) {
                result += "parent of " + this.catEmployee.toString() + " :\n";
                result += this.parent.catEmployee.toString() +"\n";
            } */

            return result;
        }
    }

    public class CatfeinatedIterator implements Iterator<Cat> {

        private ArrayList<Cat> catsInOrder;
        private int currentIndex;

        public CatfeinatedIterator() {
            catsInOrder = new ArrayList<>();
            buildInOrder(root, catsInOrder);
            currentIndex = 0;
        }

        private void buildInOrder(CatNode node, ArrayList<Cat> list) {

            if (node == null) {
                return;
            }

            buildInOrder(node.junior, list);
            list.add(node.catEmployee);
            buildInOrder(node.senior, list);
        }

        public Cat next() {

            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            Cat c = catsInOrder.get(currentIndex);
            currentIndex++;
            return c;
        }

        public boolean hasNext() {
            return currentIndex < catsInOrder.size();
        }
    }

    public static void main(String[] args) {

        Cat B = new Cat("Buttercup", 45, 53, 5, 85.0);
        Cat C = new Cat("Chessur", 8, 23, 2, 250.0);
        Cat J = new Cat("Jonesy", 0, 21, 12, 30.0);
        Cat JJ = new Cat("JIJI", 156, 17, 1, 30.0);
        Cat JTO = new Cat("J. Thomas O'Malley", 21, 10, 9, 20.0);
        Cat MrB = new Cat("Mr. Bigglesworth", 71, 0, 31, 55.0);
        Cat MrsN = new Cat("Mrs. Norris", 100, 68, 15, 115.0);
        Cat T = new Cat("Toulouse", 180, 37, 14, 25.0);

        Cat BC = new Cat("Blofeld's cat", 6, 72, 18, 120.0);
        Cat L = new Cat("Lucifer", 10, 44, 20, 50.0);
    }
}

