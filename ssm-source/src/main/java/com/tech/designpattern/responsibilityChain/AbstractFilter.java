package com.tech.designpattern.responsibilityChain;

public abstract class AbstractFilter implements Filter{








    protected Filter nextFilter;

    public Filter getNextFilter() {
        return nextFilter;
    }

    public void setNextFilter(Filter nextFilter) {
        this.nextFilter = nextFilter;
    }
}
