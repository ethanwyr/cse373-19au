package deques;

public class LinkedDeque<T> implements Deque<T> {
    private int size;
    private Node sentinel;

    public LinkedDeque() {
        size = 0;
        sentinel = new Node(null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    private class Node {
        private T value;
        private Node next;
        private Node prev;

        Node(T value) {
            this(value, null, null);
        }

        Node(T value, Node next, Node prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    public void addFirst(T item) {
        Node temp = new Node(item, sentinel.next, sentinel.next.prev);
        sentinel.next.prev = temp;
        sentinel.next = temp;
        size += 1;
    }

    public void addLast(T item) {
        Node temp = new Node(item, sentinel.prev.next, sentinel.prev);
        sentinel.prev.next = temp;
        sentinel.prev = temp;
        size += 1;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        Node temp = sentinel.next;
        temp.next.prev = temp.prev;
        sentinel.next = temp.next;
        size -= 1;
        // clear the node
        temp.next = null;
        temp.prev = null;
        return temp.value;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        Node temp = sentinel.prev;
        temp.prev.next = temp.next;
        sentinel.prev = temp.prev;
        size -= 1;
        // clear the node
        temp.next = null;
        temp.prev = null;
        return temp.value;
    }

    public T get(int index) {
        if ((index > size) || (index < 0)) {
            return null;
        }
        Node curr = sentinel.next;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        return curr.value;
    }

    public int size() {
        return size;
    }
}
