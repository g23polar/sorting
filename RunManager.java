import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.util.ArrayList;

public class RunManager {

    private ArrayList<Record> stash;
    private FileWriter flushes;
    private MinHeap heap;
    private RandomAccessFile inFile;
    private RandomAccessFile runFile;
    private byte[][] inputBuffer;
    private byte[][] outputBuffer;
    private int oBufferCounter;
    private int iBufferCounter;

    private boolean fileEndReached;
    private long inFilePointer;
    private int outFilePointer;

    private boolean outBufferFull;
    private Record lastSent;
    private boolean inBufferEmpty;

    public RunManager(RandomAccessFile in, RandomAccessFile run) throws IOException {
        flushes = new FileWriter(new File("Flushes.txt"));
        this.heap = new MinHeap(4096);
        this.inFile = in;
        this.runFile = run;
        inputBuffer = new byte[512][16];
        outputBuffer = new byte[512][16];
        inFilePointer = 0;
        outFilePointer = 0;
        this.inBufferEmpty = true;
        this.outBufferFull = false;
        this.oBufferCounter = 0;
        this.iBufferCounter = 0;
        fileEndReached = false;
        this.stash = new ArrayList<Record>();
        this.lastSent = null;
    }

    /**
     * Load input buffer with one block
     * 
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public void loadBuffer() throws IOException, InterruptedException {
        System.out.println("Reloading buffer        < - ");

        byte[] rec = new byte[16];
        int x = 0;

        int stashSize = this.stash.size();

        if(stashSize == 0){
            for (int i = 0; i < 512; i++) {

                x = this.inFile.read(rec);
                this.inputBuffer[i] = rec;
                Record tempRec = new Record(rec);
                if (tempRec.getID() == 0) {
                    System.out.println("Input File EOF");
                    this.iBufferCounter = -1;
                    // Thread.sleep(5000);
                    return;
                }
                // System.out.println("id = " + tempRec.getID() + ", key = " +
                // tempRec.getKey());
                // empty rec ?
                rec = new byte[16];
            }
        }
        else{
            int c = 0;
            System.out.println("Stash size = " + stash.size());

            for(Record r: stash){
                this.inputBuffer[c] = r.getCompleteRecord();
                c++;
            }
            for(int i = 0; i < (512 - c) ; i++){
                x = this.inFile.read(rec);
                this.inputBuffer[i] = rec;
                Record tempRec = new Record(rec);
                rec = new byte[16];
            }
            stash = new ArrayList<Record>();
        }

        

        // System.out.println("RM => Finished loading buffer");
        this.inBufferEmpty = false;
        this.iBufferCounter = 0;
    }

    /**
     * Help load records to memory
     * 
     * @return each record
     * @throws IOException
     */
    private Record loadOneRecord() throws IOException {
        byte[] rec = new byte[16];
        this.inFile.read(rec);
        inFilePointer = this.inFile.getFilePointer();
        Record t = new Record(rec);
        // System.out.println("rm - loadOne, 1");
        // System.out.println(t.toString());
        return t;
    }

    /**
     * Read one block from input file to memory
     * 
     * @throws IOException
     */
    public void loadOneBlockToMemory() throws IOException {
        // System.out.println("Loading one block to memory");
        for (int i = 0; i < 512; i++) {
            // Record curr = this.loadOneRecord();
            

            // System.out.println("got " + i);
            Record curr = new Record(this.inputBuffer[i]);
            this.heap.insert(curr);

            // System.out.println("Heap after insert");
            // System.out.println(this.heap.toString());
        }
        // System.out.println("Finished loading one block to memory");
        // System.out.println("Heap size after inserting block = " + this.heap.size());
        this.inputBuffer = new byte[512][16];
        this.inBufferEmpty = true;
    }

    /**
     * Master run
     * 
     * @throws IOException
     * @throws InterruptedException
     */
    public void run() throws IOException, InterruptedException {

        // if (this.inFile.getFilePointer() == -1) {
        //     this.inFile.close();
        //     System.out.println("Input file empty");
        // }

        for (int i = 0; i < 8; i++) {
            this.loadBuffer();
            this.loadOneBlockToMemory();
        }
        

        while (true) {
            if (this.oBufferCounter == 512) {
                this.outBufferFull = true;
            }
            if (this.iBufferCounter == 512) {
                this.inBufferEmpty = true;
            }
            if (this.heap.size() == 0) {
                System.out.println("\t!!! DONE !!!\t");
                // flushes.close();
                // System.exit(1);
                break;
            }
            if (this.outBufferFull) {
                // TODO: flush to runfile
                this.flushToRun();
                
            }
            if (inBufferEmpty) {
                // TODO: load input buffer from file
                this.loadBuffer();
            }

            /**
             * 1. Remove from minheap to output buffer
             */
            Record min = this.heap.getMin();
            if (lastSent == null){
                lastSent = this.heap.removeMin();
                // this.heap.
                this.outputBuffer[this.oBufferCounter] = lastSent.getCompleteRecord();
                this.oBufferCounter++;
                // System.out.println("Output buffer empty");
                // Thread.sleep(3000);
            }
            else {
                if(min.compareTo(lastSent) == -1){
                    this.stash.add(this.heap.removeMin());
                    // System.out.println("Stashed because " + min.getKey() +  " < " + lastSent.getKey());
                    // Thread.sleep(3000);
                    this.heap.decrementHeapSize();
                }
                else {
                    this.outputBuffer[this.oBufferCounter] = this.heap.removeMin().getCompleteRecord();
                    this.oBufferCounter++;
                    if(!this.inBufferEmpty){
                        // Thread.sleep(3000);
                        Record tempRecord = new Record(this.inputBuffer[this.iBufferCounter]);
                        // System.out.println("Inserting " + tempRecord.getKey());
                        this.heap.insert(tempRecord);
                        this.iBufferCounter++;
                    }
                }
            }

        }
        flushes.close();
        System.out.println("Stash size = " + stash.size());
        
    }

    private void flushToRun() throws IOException, InterruptedException {
        System.out.println("Flushing to runfile");
        // System.out.println(new Record(this.outputBuffer[0]).getKey());
        // System.out.println("-------------------");
        flushes.write(new Record(this.outputBuffer[0]).toString() + "\n");

        // Thread.sleep(5000);
        // for (byte b[] : this.outputBuffer) {
        // flushes.write(new Record(b).toString2()+"\n");
        // // this.runFile.write(b);
        // }
        this.lastSent = null;
        flushes.write("-------------------" + "\n\n");
        this.outputBuffer = new byte[512][16];
        this.oBufferCounter = 0;
        this.outBufferFull = false;
    }
}
