import javax.swing.text.DefaultStyledDocument.ElementSpec;

/**
 * Min heap class
 * 
 * @author g23, avim
 * @version init
 */
public class MinHeap {
    private int capacity;
    private int size;

    private Record[] heap;

    /**
     * get min
     * 
     * @return min val
     */
    public Record getMin() {
        return this.heap[1];
    }

    /**
     * Default constructor
     * 
     * @param cap capacity
     */
    public MinHeap(int cap) {
        this.capacity = cap;
        this.size = 0;
        this.heap = new Record[this.capacity + 1];
        // this.heap[0] = Integer.MIN_VALUE;
    }

    /**
     * removes min from the heap
     * 
     * @return min
     */
    public Record removeMin() {
        Record min = this.heap[1];
        this.heap[1] = this.heap[this.size--];
        if (min.getID() == 0) {
            return null;
        }
        minHeapify(1);
        return min;
    }

    /**
     * make node at position 'i' a minHeap
     * 
     * @param i
     */
    private void minHeapify(int i) {
        if (!isLeaf(i)) {
            if (this.heap[i].compareTo(this.heap[leftChild(i)]) == -1
                    || this.heap[i].compareTo(this.heap[rightChild(i)]) == -1) {

                if (this.heap[leftChild(i)].compareTo(this.heap[rightChild(i)]) == -1) {
                    swap(i, leftChild(i));
                    minHeapify(leftChild(i));
                } else {
                    swap(i, rightChild(i));
                    minHeapify(rightChild(i));
                }
            }
        }
    }

    /**
     * swap helper
     * 
     * @param p1 first pos
     * @param p2 second pos
     */
    private void swap(int p1, int p2) {

        Record temp = null;
        temp = this.heap[p1];

        this.heap[p1] = this.heap[p2];
        this.heap[p2] = temp;
    }

    /**
     * check end leaf
     * 
     * @param idx index to check
     * @return t/f
     */
    private boolean isLeaf(int idx) {
        return ((this.size / 2) < idx && idx <= this.size);
    }

    /**
     * get the parent
     * 
     * @param idx index
     * @return val
     */
    private int parent(int idx) {
        return idx / 2;
    }

    /**
     * get left most item
     * 
     * @param idx index
     * @return val
     */
    private int leftChild(int idx) {
        return (2 * idx);
    }

    /**
     * get right most item
     * 
     * @param idx index
     * @return val
     */
    private int rightChild(int idx) {
        return (2 * idx) + 1;
    }

    /**
     * insert into minHeap
     * 
     * @param el to add
     */
    public void insert(Record el) {
        // System.out.println("minheap - insert, 1");
        if (this.size >= this.capacity) {
            return;
        }
        // System.out.println("minheap - insert, 2");
        this.heap[++this.size] = el;
        int cur = this.size;
        // System.out.println("minheap - insert, 3");
        // System.out.println(this.heap[cur].toString());
        // System.out.println("minheap - insert, 3-1");
        // System.out.println(parent(cur));
        // System.out.println("minheap - insert, 3-2");
        if (parent(cur) <= 0) {

        } else {
            // System.out.println("cur = " + cur);
            // System.out.println("cur = " + this.heap[cur]);
            // System.out.println("parent = " + parent(cur));
            // System.out.println("parent = " + this.heap[parent(cur)]);
            try {
                while (this.heap[cur].compareTo(this.heap[parent(cur)]) == -1) {
                    // System.out.println("minheap - insert, 4");
                    swap(cur, parent(cur));
                    cur = parent(cur);
                    // System.out.println("minheap - insert, 5");
                }
            } catch (Exception e) {
                return;
            }

            // System.out.println("minheap - insert, 6");
        }

    }

    /**
     * size getter
     * 
     * @return size
     */
    public int size() {
        return this.size;
    }

    /**
     * checks if empty
     * 
     * @return t/f
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    // Modify the value at the given position
    public void modify(int pos, Comparable newVal) {
        if ((pos < 0) || (pos >= size)) {
            return;
        } // Illegal heap position
        heap[pos] = (Record) newVal;
        update(pos);
    }

    // The value at pos has been changed, restore the heap property
    void update(int pos) {
        while ((pos > 0) && (heap[pos].compareTo(heap[parent(pos)]) == 1)) {
            this.swap(pos, parent(pos)); // heap, pos, parent[pos]
            pos = parent(pos);
        }
        minHeapify(pos); // If it is little, push down
    }

    // whenever adding to the end of the heap, capacity would be capacity -1

    /**
     * decrement heap size
     */
    public void decrementHeapSize() {
        this.size--;
    }
}
