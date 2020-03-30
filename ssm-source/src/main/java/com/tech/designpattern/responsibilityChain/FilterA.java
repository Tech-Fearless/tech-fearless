package com.tech.designpattern.responsibilityChain;

import com.tech.annotion.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class FilterA extends AbstractFilter{

    @Override
    public String doFilter(String param) {
        String outPut = "do filter:FilterA";
        System.out.println("before " + outPut + "\n");
        nextFilter.doFilter(param);
        System.out.println("after " + outPut + "\n");
        return outPut;
    }

}
