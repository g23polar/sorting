/**
 * Min heap class
 * @author g23, avim
 * @version init
 */
public class MinHeap {
    private int capacity;
    private int size;

    private int[] heap;
    /**
     * Default constructor
     * @param cap capacity
     */
    public MinHeap(int cap){
        this.capacity = cap;
        this.size = 0;
        this.heap = new int[this.capacity + 1];
        this.heap[0] = Integer.MIN_VALUE;
    }

    private int removeMin(){
        int m = this.heap[1];
        this.heap[1] = this.heap[this.size--];
        minHeapify(1);
        return m;
    }
    
    
    
    /**
     * make node at position 'i' a minHeap
     * @param i
     */
    private void minHeapify(int i) {
        if (!isLeaf(i)) {
            if (this.heap[i] > this.heap[leftChild(i)]
                || this.heap[i] > this.heap[rightChild(i)]) {
  
                if (this.heap[leftChild(i)]
                    < this.heap[rightChild(i)]) {
                    swap(i, leftChild(i));
                    minHeapify(leftChild(i));
                }
  
                else {
                    swap(i, rightChild(i));
                    minHeapify(rightChild(i));
                }
            }
        }
    }

    /**
     * swap helper
     * @param p1 first pos
     * @param p2 second pos
     */
    private void swap(int p1, int p2) {

        int temp = 0;
        temp = this.heap[p1];

        this.heap[p1] = this.heap[p2];
        this.heap[p2] = temp;
    }

    /**
     * check end leaf
     * @param idx index to check 
     * @return t/f
     */
    private boolean isLeaf(int idx){
        return (idx > (this.size / 2) && idx <= this.size);
    }

    /**
     * get the parent
     * @param idx index
     * @return val
     */
    private int parent(int idx) {
        return idx / 2;
    }

    /**
     * get left most item
     * @param idx index
     * @return val
     */
    private int leftChild(int idx) {
        return (2 * idx);
    }

    /**
     * get right most item
     * @param idx index 
     * @return val
     */
    private int rightChild(int idx) {
        return (2 * idx) + 1;
    }


    /**
     * insert into minHeap
     * @param el to add
     */
    public void insert(int el) {
        if (this.size >= this.capacity) {
            return;
        }

        this.heap[++this.size] = el;
        int cur = this.size;

        while (this.heap[cur] < this.heap[parent(cur)]) {
            swap(cur, parent(cur));
            cur = parent(cur);
        }
    }
}
