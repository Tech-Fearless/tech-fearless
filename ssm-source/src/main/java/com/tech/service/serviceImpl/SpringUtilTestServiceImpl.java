package com.tech.service.serviceImpl;

import com.tech.service.SpringUtilTestService;
import org.springframework.stereotype.Service;

@Service(SpringUtilTestService.BEAN_NAME)
public class SpringUtilTestServiceImpl implements SpringUtilTestService {

    @Override
    public String callMethod() {
        return "call method success!";
    }
}
