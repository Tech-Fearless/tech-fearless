package com.tech.designpattern.responsibilityChain;

import com.tech.annotion.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class FilterC extends AbstractFilter{

    @Override
    public String doFilter(String param) {
        String outPut = "do filter:FilterC";
        System.out.println("before " + outPut + "\n");
        System.out.println("do filterC\n");
        System.out.println("after " + outPut + "\n");
        return outPut;
    }

}
