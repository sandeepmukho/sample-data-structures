package org.trie;

public class Trie {

    private TrieNode root;
    private int count;

    public Trie() {
        root = new TrieNode();
        this.count = 0;
    }

    public TrieNode getRoot() {
        return root;
    }

    public int getCount() {
        return count;
    }

    public void setRoot(TrieNode root) {
        this.root = root;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void insert(char[] key) {
        this.count++;
        TrieNode current = root;
        int index = -1;

        // reach to word
        for (int i = 0; i < key.length; i++) {
            index = (int) key[i] - (int) 'a';
            // create TrieNode if not already created
            if (current.getChildren()[index] == null) {
                current.getChildren()[index] = new TrieNode();
            }
            current = current.getChildren()[index];
        }
        // mark as word , can also set as count;
        current.setValue(1);
    }

    public boolean search(char[] key) {
        boolean flag = false;
        int index = -1;
        TrieNode current = root;
        for (int i = 0; i < key.length; i++) {
            index = (int) key[i] - (int) 'a';
            if (current.getChildren()[index] == null) {
                flag = false;
                return flag;
            }
            current = current.getChildren()[index];
        }
        flag = (current.getValue() != 0);
        return flag;
    }

    public static void main(String[] args) {
        String[] words = { "the", "a", "there", "answer", "any", "by", "bye", "their" };

        Trie trie = new Trie();

        for (int i = 0; i < words.length; i++) {
            trie.insert(words[i].toCharArray());
        }

        System.out.println(trie.search(words[3].toCharArray()));
        System.out.println(trie.search("b".toCharArray()));
        System.out.println(trie.search("byebye".toCharArray()));
        System.out.println(trie.search(words[0].toCharArray()));

    }
}
