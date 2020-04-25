package com.tech.service.serviceImpl;

import com.tech.designpattern.responsibilityChain.Filter;
import com.tech.designpattern.responsibilityChain.FilterSupport;
import com.tech.service.DoFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DoFilterServiceImpl implements DoFilterService {

    @Autowired
    private FilterSupport filterSupport;


    @Override
    public String doBusiness(Map<String, String> param) {
        if (filterSupport.getFilterStart() == null){
            filterSupport.initFilterChain();
        }
        Filter start = filterSupport.getFilterStart();
        start.doFilter("start");
        System.out.println("finish dofilter chain.");
        return "finish";
    }
}
