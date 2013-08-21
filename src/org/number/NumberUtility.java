package org.number;

import java.util.Arrays;

public class NumberUtility {

    /**
     * Find the next largest number using the same digits input - output 1234
     * 1243 1230 1302
     * 
     * @param number
     */
    public static void nextLargerInteger(int number) {
        char[] arr = String.valueOf(number).toCharArray();
        int beginIndex = -1;

        for (int i = arr.length - 1; i >= 0; i--) {
            int np = (int) arr[i] - 48;
            for (int j = i - 1; j >= 0; j--) {
                // System.out.println( arr[i] + " > " + arr[j]);
                int temp = (int) arr[j] - 48;
                if (np > temp) {
                    arr[j] = (char) (np + 48);
                    arr[i] = (char) (temp + 48);
                    beginIndex = j;
                    //endIndex = i;
                    break;
                }
            }
            if (beginIndex != -1 )
                break;
        }       
        if (beginIndex != -1 ) {
            char[] firstpart = Arrays.copyOfRange(arr, 0, beginIndex+1);
            char[] secondPart = Arrays.copyOfRange(arr, beginIndex+1, arr.length);
            Arrays.sort(secondPart);
            System.out.print(number + " - ");
            System.out.println(new String(firstpart) + new String(secondPart));
        } else {
            System.out.println("No Bigger Number than using same digits" + number);
        }

    }

    public static void main(String[] args) {
        nextLargerInteger(1234);
        nextLargerInteger(1230);
        nextLargerInteger(958962432);
        
        // 1230 - 1302
        //nextLargerInteger(4321);
    }
}
