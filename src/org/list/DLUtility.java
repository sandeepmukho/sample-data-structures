package org.list;

public class DLUtility {

    /**
     * Function to reverse a Doubly Linked List
     * 
     * @param root
     */
    public static DLNode<Integer> reverse(DLNode<Integer> root) {
        DLNode<Integer> temp = null;
        DLNode<Integer> current = root;

        // swap next and prev for all nodes of doubly linked list
        while (current != null) {
            temp = current.getPrev();
            current.setPrev(current.getNext());
            current.setNext(temp);
            current = current.getPrev();
        }

        // change the root
        if (temp != null) {
            root = temp.getPrev();
        }
        return root;
    }

    /**
     * print the list
     * 
     * @param root
     */
    public static void printList(DLNode<Integer> root) {
        while (root != null) {
            System.out.print(root.data);
            if (root.getNext() != null)
                System.out.print(" --> ");
            root = root.getNext();
        }
        System.out.println();
    }

    /**
     * Add to head of DL List
     * 
     * @param root
     * @param data
     * @return
     */
    public static DLNode<Integer> addToHead(DLNode<Integer> root, int data) {
        /* allocate node */
        DLNode<Integer> new_node = new DLNode<Integer>(data);
        // since we are adding at the begining, prev is always NULL
        new_node.setPrev(null);

        // link the old list off the new node
        new_node.setNext(root);

        // change prev of head node to new node
        if (root != null)
            root.setPrev(new_node);

        return new_node;
    }

    public static DLNode<Integer> addToTail(DLNode<Integer> root, int data) {
        DLNode<Integer> current = root;
        DLNode<Integer> temp = new DLNode<Integer>(data);

        if (root == null) {
            current = temp;
            root = current;
        } else {
            while (current.getNext() != null) {
                current = current.getNext();
            }
            temp.setPrev(current);
            current.setNext(temp);
        }

        return root;
    }

    public static void main(String[] args) {

        DLNode<Integer> root = null;

        // root = addToHead(root, 2);
        // root = addToHead(root, 4);
        // root = addToHead(root, 6);
        // root = addToHead(root, 10);
        root = addToTail(root, 2);
        root = addToTail(root, 4);
        root = addToTail(root, 8);
        root = addToHead(root, 1);
        root = addToTail(root, 10);

        System.out.println("Original Linked list ");
        printList(root);

        /* Reverse doubly linked list */
        root = reverse(root);

        System.out.println("Reversed Linked list ");
        printList(root);
    }

}
