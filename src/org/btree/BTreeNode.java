package org.btree;


public class BTreeNode {
    private int[] keys; // An array of keys
    private int t; // Minimum degree - number of keys and child nodes are
                   // dependent on
    // the same
    private BTreeNode[] childNodes; // An array of child pointers
    private int numberOfKeys;
    private boolean leaf; // Is true when node is leaf. Otherwise false

    public int[] getKeys() {
        return keys;
    }

    public int getT() {
        return t;
    }

    public BTreeNode[] getChildNodes() {
        return childNodes;
    }

    public int getNumberOfKeys() {
        return numberOfKeys;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setKeys(int[] keys) {
        this.keys = keys;
    }

    public void setT(int t) {
        this.t = t;
    }

    public void setChildNodes(BTreeNode[] childNodes) {
        this.childNodes = childNodes;
    }

    public void setNumberOfKeys(int numberOfKeys) {
        this.numberOfKeys = numberOfKeys;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public BTreeNode(int t, boolean leaf) {
        this.t = t;
        this.leaf = leaf;

        // stores 2t -1 keys , condition t >= 2
        keys = new int[2 * t - 1];
        childNodes = new BTreeNode[2 * t];

        // currently number of keys as 0
        numberOfKeys = 0;
    }

    /**
     * traverse all nodes in a subtree rooted with this node
     */
    public void traverse() {
        // There are n keys and n+1 children, traverse through n keys
        // and first n children
        int i;
        for (i = 0; i < numberOfKeys; i++) {
            // If this is not leaf, then before printing key[i],
            // traverse the subtree rooted with child C[i].

            if (leaf == false)
                childNodes[i].traverse();
            System.out.print(keys[i] + " ");
        }

        // Print the subtree rooted with last child
        if (leaf == false) {
            childNodes[i].traverse();
        }
    }

    
    /**
     * function to search a key in subtree rooted with this node
     * 
     * @param k
     * @return
     */
    public BTreeNode search(int k) {
        // Find the first key greater than or equal to k
        int i = 0;
        while (i < numberOfKeys && k > keys[i])
            i++;

        // If the found key is equal to k, return this node
        if (keys[i] == k)
            return this;

        // If key is not found here and this is a leaf node
        if (leaf == true)
            return null;

        // Go to the appropriate child
        return childNodes[i].search(k);
    }

    /**
     * insert a new key in this node, assumption is, the node must be non-full
     * when this function is called
     * 
     * @param k
     */
    public void insertNonFull(int k) {
        // Initialize index as index of rightmost element
        int i = numberOfKeys - 1;

        // If this is a leaf node
        if (leaf == true) {
            // The following loop does two things
            // a) Finds the location of new key to be inserted
            // b) Moves all greater keys to one place ahead
            while (i >= 0 && keys[i] > k) {
                keys[i + 1] = keys[i];
                i--;
            }

            // Insert the new key at found location
            keys[i + 1] = k;
            numberOfKeys = numberOfKeys + 1;
        } else // If this node is not leaf
        {
            // Find the child which is going to have the new key
            while (i >= 0 && keys[i] > k)
                i--;

            // See if the found child is full
            if (childNodes[i + 1].numberOfKeys == 2 * t - 1) {
                // If the child is full, then split it
                splitChild(i + 1, childNodes[i + 1]);

                // After split, the middle key of C[i] goes up and
                // C[i] is splitted into two. See which of the two
                // is going to have the new key
                if (keys[i + 1] < k)
                    i++;
            }
            childNodes[i + 1].insertNonFull(k);
        }
    }

    /**
     * function to split the child y of this node ; Assumption- y must be full
     * when this function is called
     * 
     * @param i
     * @param y
     */
    public void splitChild(int i, BTreeNode y) {
        // Create a new node which is going to store (t-1) keys
        // of y
        BTreeNode z = new BTreeNode(y.t, y.leaf);
        z.numberOfKeys = t - 1;

        // Copy the last (t-1) keys of y to z
        for (int j = 0; j < t - 1; j++)
            z.keys[j] = y.keys[j + t];

        // Copy the last t children of y to z
        if (y.leaf == false) {
            for (int j = 0; j < t; j++)
                z.childNodes[j] = y.childNodes[j + t];
        }

        // Reduce the number of keys in y
        y.numberOfKeys = t - 1;

        // Since this node is going to have a new child,
        // create space of new child
        for (int j = numberOfKeys; j >= i + 1; j--)
            childNodes[j + 1] = childNodes[j];

        // Link the new child to this node
        childNodes[i + 1] = z;

        // A key of y will move to this node. Find location of
        // new key and move all greater keys one space ahead
        for (int j = numberOfKeys - 1; j >= i; j--)
            keys[j + 1] = keys[j];

        // Copy the middle key of y to this node
        keys[i] = y.keys[t - 1];

        // Increment count of keys in this node
        numberOfKeys = numberOfKeys + 1;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("NumberOfKeys: " + numberOfKeys);
        sb.append(" Keys: ");
        for (int i = 0; i < numberOfKeys; i++) {
            sb.append(" " + keys[i]);
        }
        return sb.toString();
    }

}