import java.util.*;
import java.nio.ByteBuffer;

/**
 * Holds a single record
 * 
 * @author CS Staff
 * @version 2020-10-15
 */
public class Record implements Comparable<Record> {

    private byte[] completeRecord;
    private ArrayList<Record> records;

    /**
     * The constructor for the Record class
     * 
     * @param record
     *            The byte for this object
     */
    public Record(byte[] record) {
        completeRecord = record;
        // System.out.println("rec - constr, 1");
        // System.out.println(this.toString());   
    }

    public Record() {
        
    }


    /**
     * returns the complete record
     * 
     * @return complete record
     */
    public byte[] getCompleteRecord() {
        return completeRecord;
    }

    /**
     * Returns the object's key
     * 
     * @return the key
     */
    public double getKey() {
        ByteBuffer buff = ByteBuffer.wrap(completeRecord);
        return buff.getDouble(8); // second 8 bytes
    }

    /**
     * Returns the object's ID
     * @return the id
     */
    public long getID() {
        ByteBuffer buff = ByteBuffer.wrap(completeRecord);
        return buff.getLong(0); // first 8 bytes
    }

    /**
     * Compare Two Records based on their keys
     * 
     * @param o
     *            - The Record to be compared.
     * @return A negative integer, zero, or a positive integer as this employee
     *         is less than, equal to, or greater than the supplied record
     *         object.
     */
    @Override
    public int compareTo(Record toBeCompared) {
        
        try {
            return Double.compare(this.getKey(), toBeCompared.getKey());
        } catch (Exception e) {
            System.out.println("this = " + this.getKey());
            System.out.println("other = " + toBeCompared.getKey());
            return -1;
        }
    }


    /**
     * Outputs the record as a String
     * 
     * @return a string of what the record contains
     */
    public String toString() {
        return "" + this.getKey();
    }

    /**
     * Outputs the record as a String
     * 
     * @return a string of what the record contains
     */
    public String toString2() {
        return this.getID()+" " + this.getKey();
    }

}
