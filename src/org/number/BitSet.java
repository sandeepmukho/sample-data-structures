package org.number;

public class BitSet {

    int[] bitset;

    public BitSet(int size) {
        bitset = new int[size >> 5];
    }

    public boolean get(int pos) {
        int wordNumber = (pos >> 5);
        int bitNumber = (pos & 0x1F);
        return (bitset[wordNumber] & (1 << bitNumber)) != 0;
    }

    public void set(int pos) {
        int wordNumber = (pos >> 5);
        int bitNumber = (pos & 0x1F);
        //System.out.println("WordNumber: " + wordNumber + " bitNumber" + bitNumber);
        bitset[wordNumber] |= 1 << bitNumber;
        //System.out.println(bitset[wordNumber]);
    }

    public static void main(String[] args) {
        BitSet bs = new BitSet(1000);
        bs.set(6);
        bs.set(12);
        bs.set(11);
        bs.set(100);
    }

}
