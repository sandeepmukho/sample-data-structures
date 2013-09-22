package org.trie;

public class TrieNode {

    public static final int ALPHABET_SIZE = 26;

    private int value;
    private TrieNode children[];

    public TrieNode() {
        this.value = 0;
        this.children = new TrieNode[ALPHABET_SIZE];
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            this.children[i] = null;
        }
    }

    public int getValue() {
        return value;
    }

    public TrieNode[] getChildren() {
        return children;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setChildren(TrieNode[] children) {
        this.children = children;
    }

}
