import java.util.ArrayList;

/**
 * @author g23
 * @version Oct 29, 2021
 *
 */
public class Block {
    private int size;
    final private int cap = 512;
    private ArrayList<Record> records;
    
    /**
     * Block.java Constructor
     */
    public Block() { 
        this.records = new ArrayList<Record>(512);
        this.size = 0;
    }
    
    /**
     * 
     * insert logic
     * @param t rec to add
     * @return t/f
     */
    public boolean insert(Record t) {
        
        if(this.size == 512) {
            return false;
        }
        this.records.add(t);
        return true;
        
    }

    /**
     * internal sort logic
     */
    public void sort() {
        
        ArrayList<Record> sorted = new ArrayList<Record>(512);
        this.records = sorted; 
        
    }
    
    
    
}
