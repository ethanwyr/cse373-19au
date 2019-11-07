package heap;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private ArrayList<PriNode> heap;
    private ArrayList<PriNode> key;
    private int hashSize;
    private PriNode goalNode;

    /**
     * one priority node, an item of type T with the given priority
     * with link to the next node
     */
    private class PriNode implements Comparable<PriNode> {
        private T item;
        private double priority;
        private int loc;
        private PriNode next;

        PriNode(T e, double p, int l, PriNode n) {
            this.item = e;
            this.priority = p;
            this.loc = l;
            this.next = n;
        }

        PriNode(T e, double p) {
            this(e, p, 0, null);
        }

        T getItem() {
            return item;
        }

        double getPriority() {
            return priority;
        }

        void setPriority(double priority) {
            this.priority = priority;
        }

        @Override
        public int compareTo(PriNode other) {
            if (other == null) {
                return -1;
            }
            return Double.compare(this.getPriority(), other.getPriority());
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            } else {
                return ((PriNode) o).getItem().equals(getItem());
            }
        }

        @Override
        public int hashCode() {
            int h = item.hashCode();
            if (h < 0) {
                return -h;
            }
            return h;
        }
    }

    // Initializes an empty priority queue with the given num size
    private ArrayHeapMinPQ(int num) {
        heap = new ArrayList<>();
        key = new ArrayList<>(num);
        hashSize = num;
        heap.add(null);
        for (int i = 0; i < hashSize; i++) {
            key.add(null);
        }
    }

    public ArrayHeapMinPQ() {
        this(4);
    }

    /*
    Here's helper methods. Some of the method adapt from MinPQ.java class
    Data files:   https://algs4.cs.princeton.edu/24pq/tinyPQ.txt
     */

    /**
     * A helper method to resize the size of the heap array with new num
     * 'Heap' resize heap, 'Key' resize key
     */
    private void resizeKey(int num) {
        ArrayList<PriNode> temp = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            temp.add(null);
        }
        for (int i = 0; i < hashSize; i++) {
            addAllKey(temp, key.get(i), num);
        }
        hashSize = num;
        key = temp;
    }

    private void addAllKey(ArrayList<PriNode> a, PriNode b, int num) {
        if (b != null) {
            addAllKey(a, b.next, num);
            int index = b.hashCode() % num;
            b.next = a.get(index);
            a.set(index, b);
        }
    }

    /**
     * A helper method for restoring the heap invariant
     */
    private void swim(int k) {
        while (k > 1 && heap.get(k).compareTo(heap.get(k / 2)) < 0) {
            swap(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= this.size()) {
            int j = 2 * k;
            if (j < this.size() && heap.get(j).compareTo(heap.get(j + 1)) > 0) {
                j++;
            }
            if (heap.get(k).compareTo(heap.get(j)) <= 0) {
                break;
            }
            swap(k, j);
            k = j;
        }
    }

    /**
     * A helper method for swapping the items at two indices of the array heap.
     */
    private void swap(int a, int b) {
        PriNode temp = heap.get(a);
        heap.set(a, heap.get(b));
        heap.set(b, temp);
        heap.get(a).loc = a;
        heap.get(b).loc = b;
    }

    /*
    Here's Public methods. Some of the method adapt from MinPQ.java class
    Data files:   https://algs4.cs.princeton.edu/24pq/tinyPQ.txt
     */

    /**
     * Adds an item with the given priority value.
     * Assumes that item is never null.
     * Runs in O(log N) time (except when resizing).
     * @throws IllegalArgumentException if item is already present in the PQ
     */
    @Override
    public void add(T item, double priority) {
        // resize  key array if necessary
        if ((2 * this.size() / hashSize) > 3) {
            resizeKey(2 * hashSize);
        }

        // add and percolate it up to maintain heap invariant
        // need to check duplicates
        if (contains(item)) {
            throw new IllegalArgumentException("PQ already contain " + item);
        }
        PriNode temp = new PriNode(item, priority);
        int index = temp.hashCode() % hashSize;
        temp.next = key.get(index);
        key.set(index, temp);
        heap.add(temp);
        heap.get(this.size()).loc = this.size();
        swim(this.size());
    }

    /**
     * Returns true if the PQ contains the given item; false otherwise.
     * Runs in O(log N) time.
     */
    @Override
    public boolean contains(T item) {
        PriNode goal = new PriNode(item, 0);
        int index = goal.hashCode() % hashSize;
        PriNode curr = key.get(index);
        while (curr != null) {
            if (curr.equals(goal)) {
                goalNode = curr;
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    /**
     * Returns the item with the smallest priority.
     * Runs in O(log N) time.
     * @throws NoSuchElementException if the PQ is empty
     */
    @Override
    public T getSmallest() {
        if (this.size() == 0) {
            throw new NoSuchElementException("PQ is empty");
        }
        return heap.get(1).item;
    }

    /**
     * Removes and returns the item with the smallest priority.
     * Runs in O(log N) time (except when resizing).
     * @throws NoSuchElementException if the PQ is empty
     */
    @Override
    public T removeSmallest() {
        if (this.size() == 0) {
            throw new NoSuchElementException("PQ is empty");
        }
        PriNode min = heap.get(1);
        // remove from heap
        swap(1, this.size());
        heap.remove(this.size());
        sink(1);
        // remove from key
        int index = min.hashCode() % hashSize;
        if (key.get(index).equals(min)) {
            key.set(index, min.next);
        } else {
            PriNode curr = key.get(index);
            while (!curr.next.equals(min)) {
                curr = curr.next;
            }
            curr.next = curr.next.next;
        }
        min.next = null;

        // resize heap or key array if necessary
        if ((this.size() > 0) && (this.size() == (hashSize - 1) / 4)) {
            resizeKey(hashSize / 2);
        }
        return min.item;
    }

    /**
     * Changes the priority of the given item.
     * Runs in O(log N) time.
     * @throws NoSuchElementException if the item is not present in the PQ
     */
    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        goalNode.setPriority(priority);
        sink(goalNode.loc);
        swim(goalNode.loc);
    }

    /**
     * Returns the number of items in the PQ.
     * Runs in O(log N) time.
     */
    @Override
    public int size() {
        return heap.size() - 1;
    }

    public T getItem(T item) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        return goalNode.item;
    }

}
