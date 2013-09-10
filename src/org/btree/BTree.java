package org.btree;

import java.util.ArrayDeque;
import java.util.Queue;

public class BTree {
    private BTreeNode root;
    private int t; // Minimum degree

    public BTree(int t) {
        root = null;
        this.t = t;
    }

    public BTreeNode getRoot() {
        return root;
    }

    public int getT() {
        return t;
    }

    public void setRoot(BTreeNode root) {
        this.root = root;
    }

    public void setT(int t) {
        this.t = t;
    }

    /**
     * Traverse the tree using root node
     */
    void traverse() {
        if (root != null)
            root.traverse();
    }

    void printLevelWiseTraverse() {
        // Enqueue the root in the Queue.
        Queue<BTreeNode> queue = new ArrayDeque<BTreeNode>();
        queue.add(root);
        int nodesInCurrentLevel = 1;
        int nodesInNextLevel = 0;

        while (!queue.isEmpty()) {
            // Print the top element in the Queue and insert its children
            BTreeNode temp = queue.poll();
            nodesInCurrentLevel--;
            int i;
            for (i = 0; i < temp.getNumberOfKeys(); i++) {

                System.out.print(temp.getKeys()[i] + " ");

                if (temp.isLeaf() == false) {
                    queue.add(temp.getChildNodes()[i]);
                    nodesInNextLevel++;
                }                

            }
            // Print the subtree rooted with last child
            if (temp.isLeaf() == false) {
                queue.add(temp.getChildNodes()[i]);
                nodesInNextLevel++;
            }
            if (nodesInCurrentLevel == 0) {
                System.out.println();
                nodesInCurrentLevel = nodesInNextLevel;
                nodesInNextLevel = 0;
            }
        }

    }

    public BTreeNode search(int k) {
        return (root == null) ? null : root.search(k);
    }

    // The main function that inserts a new key in this B-Tree
    public void insert(int k) {
        // If tree is empty
        if (root == null) {
            root = new BTreeNode(t, true);
            root.getKeys()[0] = k;
            root.setNumberOfKeys(1);
        } else // If tree is not empty
        {
            // If root is full, then tree grows in height
            if (root.getNumberOfKeys() == 2 * t - 1) {
                // Allocate memory for new root
                BTreeNode s = new BTreeNode(t, false);

                // Make old root as child of new root
                s.getChildNodes()[0] = root;

                // Split the old root and move 1 key to the new root
                s.splitChild(0, root);

                // New root has two children now. Decide which of the
                // two children is going to have new key
                int i = 0;
                if (s.getKeys()[0] < k)
                    i++;
                s.getChildNodes()[i].insertNonFull(k);

                // Change root
                root = s;
            } else
                // If root is not full, call insertNonFull for root
                root.insertNonFull(k);
        }

    }

    @Override
    public String toString() {
        return root.toString();
    }

}