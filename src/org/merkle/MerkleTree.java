package org.merkle;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import org.io.FileUtility;

/**
 * 
 * @author sandy
 * 
 */
public class MerkleTree {

    public static final String algorthim = "MD5";
    public static final int DEFAULT_HEIGHT = 0;
    public static final int DEFAULT_CHUNK_SIZE = 50;

    public String filePath;

    public int numberOfNodes;
    public int startDataNode;
    public int height;
    public int hashSize;
    public int dataBlockSize;
    public int dataBlocks;
    public MessageDigest hash_function;
    public MerkleTreeNode[] nodes;

    public MerkleTree(String filePath) {
        this.filePath = filePath;
        byte[] readData = FileUtility.readFile(this.filePath);
        this.height = getHeightOfMerkleTree(this.filePath, DEFAULT_CHUNK_SIZE);
        System.out.println("filePath: " + filePath + " Length: " + readData.length + " Height: " + height);
        buildTree(readData);
    }

    public void buildTree(byte[] data) {
        // number of nodes
        startDataNode = (int) Math.pow((double) 2, (double) this.height) - 1;
        dataBlocks = (int) Math.pow((double) 2, (double) this.height);
        numberOfNodes = 2 * dataBlocks - 1;
        // dataBlockSize = data.length / dataBlocks;
        dataBlockSize = DEFAULT_CHUNK_SIZE;
        nodes = new MerkleTreeNode[numberOfNodes];

        int startLength = 0;
        int destLength = 0;

        for (int i = startDataNode; i < numberOfNodes; i++) {

            destLength = (i == numberOfNodes - 1) ? (data.length - startLength) : dataBlockSize;
            nodes[i] = new MerkleTreeNode(destLength);

            System.out.println("i: " + i + " StartLength: " + startLength + " " + "Length: " + destLength);

            System.arraycopy(data, startLength, nodes[i].data, 0, destLength);
            startLength += dataBlockSize;

        }
    }

    public byte[] hash(int i) {
        byte[] hash = null;
        MessageDigest digest = null;

        try {
            digest = MessageDigest.getInstance(algorthim);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (i >= startDataNode) {
            hash = digest.digest(nodes[i].data);
        } else {
            byte[] leftHash = hash(2 * i + 1);
            byte[] rightHash = hash(2 * i + 2);
            byte[] resultHash = new byte[leftHash.length + rightHash.length];
            System.arraycopy(leftHash, 0, resultHash, 0, leftHash.length);
            System.arraycopy(rightHash, 0, resultHash, leftHash.length, rightHash.length);
            hash = digest.digest(resultHash);
        }
        return hash;
    }

    public boolean diff(MerkleTree tree, int i) {
        boolean b = true;
        if (this.height != tree.height)
            b = false;
        else {
            if (i < numberOfNodes) {
                byte[] hash1 = this.hash(i);
                byte[] hash2 = tree.hash(i);
                b = b && compareArrays(hash1, hash2);
                if (i < startDataNode) {
                    b = b && diff(tree, 2 * i + 1);
                    b = b && diff(tree, 2 * i + 2);
                }
            }
        }
        return b;
    }

    public boolean diff(MerkleTree tree, int i, List<Integer> diffList) {
        boolean b = true;
        if (this.height != tree.height) {
            b = false;
            System.out.println("i: " + i + " " + b);

        } else {
            if (i < numberOfNodes) {
                byte[] hash1 = this.hash(i);
                byte[] hash2 = tree.hash(i);
                b = compareArrays(hash1, hash2);
                if (!b && i >= startDataNode) {
                    diffList.add(i);
                }
                if (i < startDataNode) {
                    b = diff(tree, 2 * i + 1, diffList);
                    b = diff(tree, 2 * i + 2, diffList);
                }
            }
        }

        return b;
    }

    /**
     * Inorder Traversal
     * 
     * @param i
     */
    public void printTree(int i) {

        if (i < numberOfNodes) {

            System.out.println("i: " + i + "  " + Arrays.toString(hash(i)));

            if (i < startDataNode) {
                printTree(2 * i + 1);
            }
            if (i < startDataNode) {
                printTree(2 * i + 2);
            }

        }
    }

    public static boolean compareArrays(byte[] hash1, byte[] hash2) {
        return Arrays.equals(hash1, hash2);
    }

    public static int getHeightOfMerkleTree(String path, int chunkSize) {
        File file = new File(path);
        double chunks = file.length() / chunkSize;
        int height = (int) Math.ceil(Math.log(chunks));
        return (height < DEFAULT_HEIGHT) ? DEFAULT_HEIGHT : height;
    }

}