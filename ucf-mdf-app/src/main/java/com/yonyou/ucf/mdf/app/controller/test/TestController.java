package com.yonyou.ucf.mdf.app.controller.test;

import com.yonyou.ucf.mdf.app.controller.BaseController;
import com.yonyou.ucf.mdf.domain.service.TestMybatis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
@RequestMapping("/mytest")
public class TestController extends BaseController {
    @Autowired
    TestMybatis testMybatis;

    @RequestMapping("/select")
    public void getBill(HttpServletRequest request, HttpServletResponse response){
        try {
            testMybatis.testSelect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ;
    }
}
