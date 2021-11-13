import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

public class replacementSelection {

    private byte[] inputBuf;
    private byte[] outputBuf;
    private ArrayList<Record> records;


 
    public replacementSelection() {
        this.inputBuf = new byte[8192]; //bytes
        this.outputBuf = new byte[8192];
    }


    public int getInputBuf(RandomAccessFile in) throws IOException {
        return in.read(inputBuf);
    }

    public byte[] getOutputBuf() {
        return this.outputBuf;
    }

    public void intoHeap(byte[] inputBuf) {
        MinHeap heap = new MinHeap(inputBuf.length);
        for(int i = 0; i < inputBuf.length; i++) {
            heap.insert(i);
        }
         
    }


}
