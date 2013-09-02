package org.url;

public class Shortner {

    static int counter = 0;

    private static final char[] allowedChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z' };

    public static String shorten() {
        StringBuilder hexBuilder = new StringBuilder();
        int rem = 0;
        int number = counter;
        if (number == 0) {
            hexBuilder.append(allowedChars[number]);
        }
        while (number > 0) {
            rem = number % allowedChars.length;
            number = number / allowedChars.length;
            hexBuilder.append(allowedChars[rem]);
        }
        counter++;
        return hexBuilder.reverse().toString();
    }

    public static void main(String[] args) {

        for (int i = 0; i < 3844; i++) {
            System.out.println(shorten());
        }

    }
}
