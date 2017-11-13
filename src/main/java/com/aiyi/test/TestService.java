package com.aiyi.test;

import com.aiyi.smartframework.annotation.Service;
import com.aiyi.smartframework.bean.Data;

@Service
public class TestService {

    public Data test() {
        Data data = new Data("Hello, World");
        return data;
    }
}
