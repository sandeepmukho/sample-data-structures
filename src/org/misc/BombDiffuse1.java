package org.misc;
/**
 *  * There are n bombs in a road going from left to right,and each bomb has a
 * value and a 'effect range'.If you detonated a bomb,you will get this bomb's
 * value,but a bomb can have effect on the neighbors which the
 * distance(difference between index) between them is smaller than the 'effect
 * range'.It's say that the neighbor bomb will be destoryed and you could not
 * get their values. You will given each bomb's value and the 'effect range',and
 * you need calculate the max value you can get. eg. n=3 index 0's bomb' v is
 * 2,range is 0(will not effect others).and v[1]=1,r[1]=1,v[2]=3,r[2]=1; this
 * case's max value is 5.(detonate the 0's and than the 2's).
 * <p>
 * Solution is brute force approach, can also be solved using dynamic programming
 * </p>
 * 
 * @author sandy
 *
 */

public class BombDiffuse1 {

    public static boolean inRange(int[] radius, int j, int i) {
        if (j < radius.length && i < radius.length && Math.abs(j - i) <= radius[i])
            return true;
        else
            return false;
    }

    public static int maxBomb(int[] values, int[] radius, boolean[] destroy) {
        int n = values.length;
        int max = 0;
        boolean[] flip = new boolean[n]; // for recovery
        
        for (int i = 0; i < n; i++) {
            if (!destroy[i]) {
                destroy[i] = true;

                for (int j = 0; j < n; j++) {
                    if (inRange(radius, j, i) && !destroy[j]) {
                        destroy[j] = true;
                        flip[j] = true;
                        //System.out.println(values[i] + ",  " + values[j]);
                    }
                }
                int cur = values[i] + maxBomb(values, radius, destroy);
                max = cur > max ? cur : max;
                // recover
                destroy[i] = false;
                for (int j = 0; j < n; j++) {
                    if (flip[j] == true) {
                        destroy[j] = false;
                    }
                }                
            }            
        }
        return max;
    }

    public static void main(String[] args) {
        int[] values = { 2, 1, 3 , 3};
        int[] radius = { 0, 1, 1 , 1};

        boolean[] destroy = new boolean[values.length]; // for recovery

        System.out.println(maxBomb(values, radius, destroy));

    }

}
