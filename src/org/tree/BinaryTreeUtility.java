package org.tree;

public class BinaryTreeUtility {

    public static int heightofTree(BinaryTreeNode<Integer> root, int level) {

        if (root == null)
            return level - 1;
        else if (root.getLeft() != null || root.getRight() != null) {
            int leftLevel = heightofTree(root.getLeft(), level + 1);
            int rightLevel = heightofTree(root.getRight(), level + 1);
            level = (leftLevel > rightLevel) ? leftLevel : rightLevel;
        }
        System.out.println("Node: " + root.getData() + ", Height: " + level);
        return level;
    }

    public static void printLevelOrder(BinaryTreeNode<Integer> root) {
        int h = heightofTree(root, 0);
        int i;
        for (i = 1; i <= h; i++)
            printGivenLevel(root, i);
    }

    /* Print nodes at a given level */
    public static void printGivenLevel(BinaryTreeNode<Integer> root, int level) {

        if (root == null)
            return;
        if (level == 1)
            System.out.print(root.getData() + "  ");
        else if (level > 1) {
            printGivenLevel(root.getLeft(), level - 1);
            printGivenLevel(root.getRight(), level - 1);
        }
    }

    public static boolean equals(BinaryTreeNode<Integer> root1, BinaryTreeNode<Integer> root2) {
        //Check Reference equality and handles null
        if (root1 == root2) {
            return true;
        }
        if (root1 == null || root2 == null) {
            return false;
        }
        return (root1.getData() == root2.getData()) && equals(root1.getLeft(), root2.getLeft())
                && equals(root1.getRight(), root2.getRight());
    }

    public static void main(String[] args) {
        BinaryTreeNode<Integer> root1 = new BinaryTreeNode<Integer>(10, new BinaryTreeNode<Integer>(8), new BinaryTreeNode<Integer>(6, new BinaryTreeNode<Integer>(4),
                null));
        
        BinaryTreeNode<Integer> root2 = new BinaryTreeNode<Integer>(10, new BinaryTreeNode<Integer>(8), new BinaryTreeNode<Integer>(6, new BinaryTreeNode<Integer>(4),
                null));
        
        System.out.println(equals(root1, root2));
        // root = new Node(10, null, null);
        // System.out.println(heightofTree(root, 1));
        //printLevelOrder(root);
    }

}
