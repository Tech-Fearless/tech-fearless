package com.tech.fearless.boot.algorithm;

import org.springframework.util.StringUtils;

public class Reverse {

    public int reverseMethodOne(int x){
        String symbol = "";
        if (x < 0) {
            symbol = "-";
        }
        StringBuilder sb = new StringBuilder(String.valueOf(x));
        String reverseString = "";
        String compareString = "";
        if ("-".equals(symbol)){
            reverseString = symbol + new StringBuilder(sb.substring(1)).reverse().toString();
            compareString = String.valueOf(Integer.MIN_VALUE);
        }else {
            reverseString = sb.reverse().toString();
            compareString = String.valueOf(Integer.MAX_VALUE);
        }
        if (reverseString.length() == compareString.length()
                && reverseString.compareTo(compareString) > 0) {
            return 0;
        }
        return Integer.valueOf(reverseString);
    }



    public int reverseMethodTwo(int x){
        int newNum = 0;
        while ( x != 0) {
            int lastNum = x % 10;
            x /= 10;

            if (x > Integer.MAX_VALUE/10 || (x == Integer.MAX_VALUE/10 && lastNum > 7)) {return 0;}
            if (x < Integer.MIN_VALUE/10 || (x == Integer.MIN_VALUE/10 && lastNum < -8)) {return 0;}
            newNum = newNum * 10 + lastNum;
        }
        return newNum;
    }

}
