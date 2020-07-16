package com.tech.fearless.boot;

import com.tech.fearless.boot.algorithm.Palindrome;
import com.tech.fearless.boot.algorithm.Reverse;
import com.tech.fearless.boot.algorithm.Sum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BootApplicationTests {

    @Test
    public void sunTest() {
        Sum sum =  new Sum();
        int[] nums = new int[]{3,2,4};
        int[] result = new int[2];
        result = sum.twoSumMethodTwo(nums, 6);
        System.out.println("print ok,result:[" + result[0]+result[1]+"]");

    }

    @Test
    public void reverseTest(){
        Reverse reverse = new Reverse();
        int num = reverse.reverseMethodOne(10086);

        int fu = -12;
        int m = fu % 10;
        System.out.println("reverseNum:" + num);

    }

    @Test
    public void isPalindrome(){
        Palindrome palindrome = new Palindrome();

        boolean result = palindrome.isPalindrome(1234321);
        System.out.println("result:" + result);
    }

}
