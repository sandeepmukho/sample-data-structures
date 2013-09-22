package org.misc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * You as head of the EU need to appoint co-heads of the EU Commission. From the
 * candidates, you must select 2 people, and your pairing can not involve 2
 * people from the same country.
 * 
 * There are N candidates who are numbered from 0 to N-1. However, you don't
 * directly know the citizenship of each candidate. Instead you are given some
 * particular pairs of candidates that belong to the same country.
 * 
 * You must compute the number of ways you can pick candidates that satisfy the
 * criteria of no two members from tame country. Assume you are provided enough
 * pairs so you can identify the groups of candidates even though you may not
 * know the citizenship directly. For example if candidates 4,5,6 are from the
 * same country it is suffient to mention that (4,5) and (5,6) are pairs of
 * candidates from the same country without providing the pair (4,6) as
 * disallowed.
 * 
 * Input Format The first line contains two integers, N and I separated by a
 * single space. I lines follow. each line contains 2 integers separated by a
 * single space A and B such that
 * 
 * 0 ≤ A, B ≤ N-1
 * 
 * and A and B are candidates from the same country.
 * 
 * Output Format An integer containing the number of permissible ways in which a
 * pair of astronauts can be sent to the moon.
 * 
 * Constraints 1<=N<=10^5 1<=I<=10^6
 * 
 * Sample Input 4 2 0 1 2 3
 * 
 * Sample Output 4
 * 
 * Explanation
 * 
 * As persons numbered 0 and 1 belong to same country and 2 and 3 belong to same
 * country. So you can choose one person of 0,1 and one out of 2,3. So number of
 * ways of choosing pair is 4.
 */

public class AsternautCombinatations {

    /**
     * read input and split and parses to two output
     * 
     * @return
     */
    public static int[] readInput() {
        int[] arr = new int[2];
        try {
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            String lineRead = bufferRead.readLine();
            arr[0] = Integer.parseInt(lineRead.split(" ")[0]);
            arr[1] = Integer.parseInt(lineRead.split(" ")[1]);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }

    public static void readInputAndGetSolution() {
        // read first line of input
        int[] arr = readInput();
        int numberOfAster = arr[0];
        int numberOfInput = arr[1];
        // initialize the array
        int[] asterArr = intialize(numberOfAster);
        for (int i = 0; i < numberOfInput; i++) {
            int[] connectedAster = readInput();
            connectXY(asterArr, connectedAster[0], connectedAster[1]);
        }
        // Now applying the formula
        int nFact = getFactorial(numberOfAster);
        Integer[] freqOfElements = getFrequencyOfUniqueElments(asterArr);
        int result = nFact;

        // formula pending here
        for (int i = 0; i < freqOfElements.length; i++) {
            for (int j = i + 1; j < freqOfElements.length; j++) {

            }
        }
        for (int i = 0; i < freqOfElements.length; i++) {
            result = result / getFactorial(freqOfElements[i]);
        }
        // formula ends here
        System.out.println(result);

    }

    /**
     * return factorial of any number
     * 
     * @param n
     * @return
     */
    public static int getFactorial(int n) {
        int fact = 1;
        for (int i = n; i > 1; i--) {
            fact *= i;
        }
        return fact;
    }

    /**
     * find frequency of elements in array
     * 
     * @param arr
     * @return
     */
    public static Integer[] getFrequencyOfUniqueElments(int[] arr) {
        Map<Integer, Integer> freqMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < arr.length; i++) {
            int freqCount = 1;
            if (freqMap.containsKey(arr[i])) {
                freqCount = freqMap.get(arr[i]);
                freqCount++;
            }
            freqMap.put(arr[i], freqCount);
        }
        return freqMap.values().toArray(new Integer[1]);
    }

    /**
     * Connect two Astronauts countries
     * 
     * @param asterArr
     * @param x
     * @param y
     */
    public static void connectXY(int[] asterArr, int x, int y) {
        int xId = asterArr[x];
        int yId = asterArr[y];
        for (int i = 0; i < asterArr.length; i++) {
            if (asterArr[i] == xId) {
                asterArr[i] = yId;
            }
        }
    }

    /**
     * initialize a N array with incrmenetal value starting from 0
     * 
     * @param N
     * @return
     */
    public static int[] intialize(int N) {
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = i;
        }
        return arr;
    }

    // main function
    public static void main(String[] args) {
        readInputAndGetSolution();
    }

    // pint array function - used while testing
    public static void printArray(Integer[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

}
