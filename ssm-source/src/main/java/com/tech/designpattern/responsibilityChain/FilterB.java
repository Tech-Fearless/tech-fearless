package com.tech.designpattern.responsibilityChain;

import com.tech.annotion.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class FilterB extends AbstractFilter{

    @Override
    public String doFilter(String param) {
        String outPut = "do filter:FilterB";
        System.out.println("before " + outPut + "\n");
        nextFilter.doFilter(param);
        System.out.println("after " + outPut + "\n");
        return outPut;
    }

}
