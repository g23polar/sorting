import java.util.*;

public class replacementSelection {

    private byte[] inputBuf;
    private byte[] outputBuf;
    private ArrayList<Record> records;


 
    public replacementSelection() {
        this.inputBuf = new byte[512];
        this.outputBuf = new byte[512];
    }

    //ToDo: where is file being written from?
    private void fileReader(){
        //fielreader that reads 512 lines and inserts its values into getInputBuffer()
    }
    public byte[] getInputBuf() {
        //will contain values passed from fileReader()
        return null;
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
