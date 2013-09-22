package org.misc;
/**
 * (K, L) leftmost bottom and (M, N) rightmost upper are diagonal of first
 * rectangle <br/>
 * (P, Q) leftmost bottom and (R, S) rightmost upper are diagonal of second
 * rectangle <br/>
 * 
 * @author sandy
 * 
 */
public class Rectangle {

    public int area(int height, int width) {
        return height * width;
    }

    /**
     * get total area of both the rectangles combined
     * 
     * @param K
     *            - leftmost bottom X value of Upper Rectangle
     * @param L
     *            - leftmost bottom Y value of Upper Rectangle
     * @param M
     *            - rightmost upper X value of Upper Rectangle
     * @param N
     *            - rightmost upper Y value of Upper Rectangle
     * @param P
     *            - leftmost bottom X value of lower Rectangle
     * @param Q
     *            - leftmost bottom y value of lower Rectangle
     * @param R
     *            - rightmost upper X value of lower Rectangle
     * @param S
     *            - leftmost upper Y value of lower Rectangle
     * @return total area occupied by both rectangles
     */

    public int getTotalArea(int K, int L, int M, int N, int P, int Q, int R, int S) {
        int area = -1;

        // area of first rect
        int area1 = area(M - K, N - L);
        // area of second rect
        int area2 = area(R - P, S - Q);

        // finding X1, Y1 of intersection
        int newX1 = M;
        int newY1 = L;

        int newX2 = P;
        int newY2 = S;

        // finding Area of intersection
        int intersectArea = area(newY2 - newY1, newX1 - newX2);

        // Union formula
        area = area1 + area2 - intersectArea;
        return (area < 0) ? -1 : area;
    }

    public static void main(String[] args) {
        Rectangle s = new Rectangle();
        System.out.println(s.getTotalArea(-4, 1, 2, 6, 0, -1, 4, 3));
    }

}
