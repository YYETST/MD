package com.yonyou.ucf.mdf.app.service.impl;

import com.yonyou.ucf.mdd.api.interfaces.rpc.IComOperateApi;
import com.yonyou.ucf.mdd.common.interfaces.ref.IRefService;
import com.yonyou.ucf.mdd.common.interfaces.uimeta.IMddMetaDao;
import com.yonyou.ucf.mdd.common.model.SavePoint;
import org.imeta.orm.base.BizObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Title:
 * Description: TODO
 *
 * @author fuxhh
 * @Date: 2020/2/14 14:46
 * @Version 1.0
 */
@Service("mddComOperateApiService")
public class MddComOperateApiServiceImpl implements IComOperateApi {
//    @Autowired
//    private IRefService refService;
    @Autowired
    private IMddMetaDao mddMetaDao;

    @Override
    public <T extends BizObject> void insert(String fullname, T bill) throws Exception {
        mddMetaDao.insertNoPartition(fullname, bill);
    }

    @Override
    public <T extends BizObject> void insert(String fullname, List<T> bills) throws Exception {
        mddMetaDao.insert(fullname, bills);
    }

    @Override
    public boolean commit(List<SavePoint> savePoints) throws Exception {
        return false;
    }

    @Override
    public int executeSql(String statement, Object parameter) throws Exception {
        return 0;
    }

    @Override
    public <T extends BizObject> void update(String fullname, T bill) throws Exception {
        mddMetaDao.update(fullname, bill, true);
    }

    @Override
    public <T extends BizObject> void update(String fullname, List<T> bills) throws Exception {
        mddMetaDao.update(fullname, bills, true);
    }

    @Override
    public void delete(String fullname, Long id) throws Exception {
        mddMetaDao.delete(fullname, id, true);
    }

    @Override
    public <T extends BizObject> void delete(String fullname, List<T> bills) throws Exception {
        mddMetaDao.delete(fullname, bills, true);
    }

    @Override
    public boolean rollback(List<SavePoint> savePoints) throws Exception {
        return false;
    }
}
