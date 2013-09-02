package org.btree;

public class BTree {
    private BTreeNode root;
    private int t; // Minimum degree

    public BTree(int t) {
        root = null;
        this.t = t;
    }

    /**
     * Traverse the tree using root node
     */
    void traverse() {
        if (root != null)
            root.traverse();
    }

    public BTreeNode search(int k) {
        return (root == null) ? null : root.search(k);
    }

    // The main function that inserts a new key in this B-Tree
    public void insert(int k) {
        // If tree is empty
        if (root == null) {
            root = new BTreeNode(t, true);
            root.keys[0] = k;
            root.numberOfKeys = 1; 
        } else // If tree is not empty
        {
            // If root is full, then tree grows in height
            if (root.numberOfKeys == 2 * t - 1) {
                // Allocate memory for new root
                BTreeNode s = new BTreeNode(t, false);

                // Make old root as child of new root
                s.childNodes[0] = root;

                // Split the old root and move 1 key to the new root
                s.splitChild(0, root);

                // New root has two children now. Decide which of the
                // two children is going to have new key
                int i = 0;
                if (s.keys[0] < k)
                    i++;
                s.childNodes[i].insertNonFull(k);

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