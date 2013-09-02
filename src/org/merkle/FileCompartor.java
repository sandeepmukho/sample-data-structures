package org.merkle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.io.FileUtility;

public class FileCompartor {

    public static void fileCompare(String path1, String path2) {
        MerkleTree t1 = new MerkleTree(path1);
        MerkleTree t2 = new MerkleTree(path2);

        byte[] data1 = FileUtility.readFile(path1);

        List<Integer> diffList = new ArrayList<Integer>();
        t1.diff(t2, 0, diffList);

        for (Iterator<Integer> iterator = diffList.iterator(); iterator.hasNext();) {
            int i = iterator.next();
            int startLength = (t1.dataBlocks - 1) * t1.dataBlockSize;
            int endLength = (i == t1.numberOfNodes - 1) ? (data1.length - startLength) : t1.dataBlockSize;
            endLength = startLength + endLength;

            System.out.println("Different Node: " + i + " StartLength: " + startLength + " " + "Length: " + endLength);

        }

    }

    public static void main(String[] args) throws IOException {

        String path1 = "/tmp/a1.txt";
        String path2 = "/tmp/a2.txt";

        fileCompare(path1, path2);
    }
}
