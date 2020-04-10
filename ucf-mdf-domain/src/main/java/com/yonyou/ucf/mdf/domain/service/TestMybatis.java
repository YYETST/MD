package com.yonyou.ucf.mdf.domain.service;

import com.yonyou.ucf.mdd.common.interfaces.dao.IBizDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class TestMybatis {

    @Autowired
    private IBizDataDao bizDataDao;

    /**
     * 无参查询
     * @throws Exception
     * @return
     */
    public List<Object> testSelect() throws Exception {
        List<Object> list = bizDataDao.selectList("com.yonyou.ucf.mdf.dao.biz.TestBizObjMapper.selectMenuList",null);
        return list;
    }

    /**
     * 有参查询
     */
}
