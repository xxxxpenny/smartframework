package com.aiyi.test;

import com.aiyi.smartframework.annotation.Action;
import com.aiyi.smartframework.annotation.Controller;
import com.aiyi.smartframework.annotation.Inject;
import com.aiyi.smartframework.bean.Data;

@Controller
public class TestController {

    @Inject
    private TestService testService;

    @Action(method = "get", value = "/test")
    public Data test() {
        return testService.test();
    }
}
