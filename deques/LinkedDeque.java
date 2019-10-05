package deques;

public class LinkedDeque<T> implements Deque<T> {
    private int size;

    public LinkedDeque() {
        // TODO: your code here
        size = 0;
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    private class Node {
        private T value;
        // TODO: your fields here

        Node(T value) {
            this.value = value;
            // TODO: your code here
            throw new UnsupportedOperationException("Not implemented yet.");
        }
    }

    public void addFirst(T item) {
        // TODO: your code here
        size += 1;
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void addLast(T item) {
        // TODO: your code here
        size += 1;
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        // TODO: your code here
        size -= 1;
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        // TODO: your code here
        size -= 1;
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public T get(int index) {
        if ((index > size) || (index < 0)) {
            return null;
        }
        // TODO: your code here
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public int size() {
        return size;
    }
}
