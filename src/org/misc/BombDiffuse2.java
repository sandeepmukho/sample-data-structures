package org.misc;
import java.util.HashMap;

/**
 * 
 * There are n bombs in a road going from left to right,and each bomb has a
 * value and a 'effect range'.If you detonated a bomb,you will get this bomb's
 * value,but a bomb can have effect on the neighbors which the
 * distance(difference between index) between them is smaller than the 'effect
 * range'.It's say that the neighbor bomb will be destoryed and you could not
 * get their values. You will given each bomb's value and the 'effect range',and
 * you need calculate the max value you can get. eg. n=3 index 0's bomb' v is
 * 2,range is 0(will not effect others).and v[1]=1,r[1]=1,v[2]=3,r[2]=1; this
 * case's max value is 5.(detonate the 0's and than the 2's).
 * 
 * <p>
 * dynamic programming approach
 * </p>
 * 
 * @author sandy
 * 
 */
public class BombDiffuse2 {

    static HashMap<String, Integer> cache = new HashMap<String, Integer>();

    public static Integer getFromCache(int i, int j) {
        Integer cacheValue = null;
        if (i > j)
            cacheValue = cache.get(i + "_" + j);
        else
            cacheValue = cache.get(j + "_" + i);
        cacheValue = cache.get(i + "_" + j);
        return cacheValue;
    }

    public static int maxBomb(int[] values, int[] radius, int i, int j) {
        Integer max = 0;
        max = cache.get(i + "_" + j);
        if (max != null) {
            return max;
        }
        if (i > j)
            return 0;
        int currRadius = radius[i];
        int currValue = values[i];

        // Max without diffusing the ith bomb
        int maxWoIth = maxBomb(values, radius, i + 1, j);
        // Max with diffusing the ith bomb
        int maxWithIth = maxBomb(values, radius, i + 1 + currRadius, j) + currValue;

        // System.out.println("(" + i + "," + j + ") - " + maxWoIth + " " +
        // maxWithIth);

        max = maxWoIth > maxWithIth ? maxWoIth : maxWithIth;
        cache.put(i + "_" + j, max);

        return max;
    }

    public static void main(String[] args) {
        int[] values = { 4, 4 };
        int[] radius = { 0, 1 };

        System.out.println(maxBomb(values, radius, 0, radius.length - 1));
    }

}
