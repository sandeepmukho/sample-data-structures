package org.io;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.merkle.MerkleTree;

public class FileUtility {

    /**
     * Reads file and return the byte Array
     * 
     * @param path
     * @return
     */
    public static byte[] readFile(String path) {
        File file = new File(path);
        byte[] fileData = new byte[(int) file.length()];
        DataInputStream dis;
        try {
            dis = new DataInputStream((new FileInputStream(file)));

            dis.readFully(fileData);
            dis.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileData;
    }
    
    public static void main(String[] args) {

        System.out.println(MerkleTree.getHeightOfMerkleTree("/home/sandy/Desktop/The-Wicked-Manager-Problem_testcases.zip", 1024));
        System.out.println(MerkleTree.getHeightOfMerkleTree("/home/sandy/Desktop/distributed_systems_and_teams.txt", 1024));

    }

    

}
