package com.yonyou.test;

import com.yonyou.ucf.mdf.MDFApplication;
import com.yonyou.ucf.mdf.domain.service.TestMybatis;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MDFApplication.class)
public class MybatisTest {

    @Autowired
    com.yonyou.ucf.mdf.domain.service.TestMybatis testMybatis;

    @Test
    public void test() throws Exception {
        List<Object> list = testMybatis.testSelect();
        System.out.println(list.toString());
    }
}
