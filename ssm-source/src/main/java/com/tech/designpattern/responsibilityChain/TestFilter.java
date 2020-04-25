package com.tech.designpattern.responsibilityChain;

public class TestFilter {

    public static void main(String[] args) {
        FilterSupport filterSupport = new FilterSupport();

        if (filterSupport.getFilterStart() == null){
            filterSupport.initFilterChain();
        }
        Filter filter = filterSupport.getFilterStart();
        filter.doFilter("aaa");
    }

}
