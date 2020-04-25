package com.tech.designpattern.responsibilityChain;

import com.tech.annotion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.util.*;

@Component
public class FilterSupport{

    @Autowired
    public List<AbstractFilter> filterList;

    private AbstractFilter filterStart = null;

    public FilterSupport() {
    }


    public void initFilterChain(){
        if (CollectionUtils.isEmpty(filterList)) {
            return;
        }
        Collections.sort(filterList, new Comparator<AbstractFilter>() {
            @Override
            public int compare(AbstractFilter o1, AbstractFilter o2) {
                Class<?> class1 = o1.getClass();
                Class<?> class2 = o2.getClass();

                Annotation annotation1 = class1.getAnnotation(Order.class);
                Annotation annotation2 = class2.getAnnotation(Order.class);
                Integer orderValue1 = ((Order) annotation1).value();
                Integer orderValue2 = ((Order) annotation2).value();

                return orderValue2 - orderValue1;
            }
        });

        AbstractFilter nextFilter = null;
        for (AbstractFilter filter:filterList) {
            if (null == filter) {
                continue;
            }
            if (nextFilter != null) {
                filter.nextFilter = nextFilter;
            }
            nextFilter = filter;
        }
        filterStart = filterList.get(filterList.size() - 1 );
    }

    public AbstractFilter getFilterStart() {
        return filterStart;
    }

    public void setFilterStart(AbstractFilter filterStart) {
        this.filterStart = filterStart;
    }
}
