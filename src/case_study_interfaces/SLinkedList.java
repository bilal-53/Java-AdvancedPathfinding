package case_study_interfaces;

public class SLinkedList<E> {
    private SNode<E> head;
    private int size;

    public SLinkedList() {
        this.head = null;
    }

    public SLinkedList(SNode<E> h) {
        this.head = h;
    }

    public int getSize() {
        return this.size;
    }

    // adds an element to the front of the list
    public void addFirst(E element) {
        SNode<E> newNode = new SNode<E>(element);

        if (head == null)
            head = newNode;
        else {
            newNode.next = head;
            head = newNode;
        }

        this.size++;
    }

    public E get(int i) {
        if (i<0 || i >= this.size)
            throw new IndexOutOfBoundsException();

        SNode<E> cur = this.head;

        for (int j=0; j<i; j++)
            cur = cur.next;

        return cur.element;
    }

    public String toString() {
        SNode<E> cur = this.head;

        String allElements = "";

        while (cur != null) {
            allElements += cur.element + " ";
            cur = cur.next;
        }

        return allElements;
    }

    private class SNode<E> {
        private E element;
        private	SNode<E> next;

        SNode(E element) {
            this.element = element;
            this.next = null;
        }
    }


    public static void main(String[] args) {

    }


}

