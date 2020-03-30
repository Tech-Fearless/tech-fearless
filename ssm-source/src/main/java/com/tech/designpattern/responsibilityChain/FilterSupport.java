package com.tech.designpattern.responsibilityChain;

import com.tech.annotion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.util.*;

@Component
public class FilterSupport{

    private static List<AbstractFilter> filterList = new LinkedList<>();

    @Autowired
    private Map<String, AbstractFilter> filterMap;

    private AbstractFilter filterStart = null;

    static {
        filterList.add(new FilterA());
        filterList.add(new FilterB());
        filterList.add(new FilterC());
    }

    public FilterSupport() {
    }

    public void initOne() {
        AbstractFilter nextFilter = null;
        for (int i = filterList.size() - 1;i >= 0 ;i++){
            AbstractFilter currentFilter = filterList.get(i);
            AbstractFilter lastFilter = currentFilter;
            currentFilter.setNextFilter(nextFilter);
            nextFilter = lastFilter;
        }
        filterStart = nextFilter;
    }

    public void initTwo(){
        AbstractFilter nextFilter = null;
        for (Map.Entry<String, AbstractFilter> filterEntry:filterMap.entrySet()){
            AbstractFilter filter = filterEntry.getValue();
            Class filterClass = filter.getClass();
            if (!filterClass.isAnnotation()){
                continue;
            }
            Order order = (Order)filterClass.getAnnotation(Order.class);
            if (null == order){
                continue;
            }
            int orderValue = order.value();
        }
    }

    public AbstractFilter getFilterStart() {
        return filterStart;
    }

    public void setFilterStart(AbstractFilter filterStart) {
        this.filterStart = filterStart;
    }
}
