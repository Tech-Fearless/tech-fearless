package com.tech.fearless.boot.algorithm;

public class Palindrome {

    public boolean isPalindrome(int x) {
        if (x < 0 ) {
            return false;
        }
        int length = String.valueOf(x).length();
        int [] array = new int[length];
        int i = 0;
        while (x != 0) {
            array[i] = x % 10;
            x /= 10;
            i++;
        }

        for (int j = 0; j < array.length / 2; j++){
            if (array[j] != array[array.length - 1 -j]){
                return false;
            }
        }
        return true;
    }
}
