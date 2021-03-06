import java.io.*;
import java.nio.*;
import java.util.*;


/**
 * CS 3114 project 3 - External Sorting
 */

/**
 * The class containing the main method.
 *
 * @author g23, avim
 * @version init
 * 
 */

// On my honor:
//
// - I have not used source code obtained from another student,
// or any other unauthorized source, either modified or
// unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.

public class Externalsort {

    /**
     * @param args Command line parameters
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        
        if (args.length != 1) {
            System.out.println("Incorrect usage");
            System.exit(1);
        }
        
        RandomAccessFile in = new RandomAccessFile(args[0], "r");
        RandomAccessFile out = new RandomAccessFile("Output.bin", "rw");

        ArrayList<byte[]> bytes = new ArrayList<byte[]>();
        
        // for(int i = 0; i < 512; i++) {
        //     byte[] temp = new byte[16];
        //     in.read(temp);
        //     bytes.add(temp);
        //     Record tempRec = new Record(temp);
        //     System.out.println("id = " + tempRec.getID() + ", key = " + tempRec.getKey());
        // }
        
        RunManager rm = new RunManager(in, out);
        rm.run();
    }
}
