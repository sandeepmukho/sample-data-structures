package org.number;

// import java.util.BitSet;
import java.util.Random;

public class FindDuplicates {

    /**
     * Prints all the Duplicate Numbers
     * 
     * @param arr
     */
    public static void checkDuplicates(int[] arr) {
        BitSet bs = new BitSet(32000);
        for (int i = 0; i < arr.length; i++) {
            int num = arr[i];
            int num0 = num - 1;
            if (bs.get(num0)) {
                System.out.println(num);
            } else {
                bs.set(num0);
            }

        }

    }

    public static void main(String[] args) {
        Random r = new Random();
        int[] arr = new int[100];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = r.nextInt(100) + 1;
        }
        checkDuplicates(arr);
    }

}
