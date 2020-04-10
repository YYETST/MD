package com.yonyou.ucf.mdf.app.service.impl;

import com.yonyou.ucf.mdd.api.interfaces.rpc.IComQueryApi;
import com.yonyou.ucf.mdd.common.context.MddBaseContext;
import com.yonyou.ucf.mdd.common.interfaces.uimeta.IMddMetaDao;
import com.yonyou.ucf.mdd.common.model.uimeta.UIMetaBaseInfo;
import com.yonyou.ucf.mdd.common.report.model.StaticReport;
import org.imeta.orm.base.BizObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Title:
 * Description: TODO
 *
 * @author fuxhh
 * @Date: 2020/2/14 14:47
 * @Version 1.0
 */
@Service("mddComQueryApiService")
public class MddComQueryApiServiceImpl implements IComQueryApi {

//    @Autowired
//    private IRefService refService;
    @Autowired
    private IMddMetaDao mddMetaDao;

    @Override
    public <E> BizObject findById(String fullname, E id) throws Exception {
        return mddMetaDao.findById(fullname, id, 2);
    }

    @Override
    public List<Map<String, Object>> queryById(Long id, String selectFieldString, String fullname) throws Exception {
        return mddMetaDao.queryById(fullname, selectFieldString, id);
    }

    @Override
    public List<Map<String, Object>> queryByIds(Long[] ids, String selectFieldString, String fullname) throws Exception {
        return mddMetaDao.queryByIds(fullname, selectFieldString, ids);
    }

    @Override
    public <E> List<E> selectList(String statement, Object parameter) throws Exception {
        return MddBaseContext.bizDataDao().selectList(statement, parameter);
    }

    @Override
    public <E> List<E> selectList(String datasource, String statement, Object parameter) throws Exception {
        if("uimeta".equals(datasource)){
            return MddBaseContext.uimetaDao().selectList(statement,parameter);
        }else{
            return MddBaseContext.bizDataDao().selectList(statement, parameter);
        }
    }

    @Override
    public <E> List<E> selectSql(String statement, Object parameter) throws Exception {
        return mddMetaDao.selectList(statement, parameter);
    }

    @Override
    public <T extends Map<String, Object>> List<T> query(String fullname, String json) throws Exception {
        return mddMetaDao.query(fullname, json);
    }

    @Override
    public <T extends Map<String, Object>> List<T> query(UIMetaBaseInfo uiMetaBaseInfo, String json) throws Exception {
        return mddMetaDao.query(uiMetaBaseInfo, json);
    }

    @Override
    public String referenceCheck(String fullname, String id, String group) throws Exception {
        return null;
    }

    @Override
    public Object getOptionValueByName(String optionName, String orgId) throws Exception {
        return null;
    }

    @Override
    public List<Object> getOptionValueByNames(List<String> optionNames, String orgId) throws Exception {
        return null;
    }

    @Override
    public StaticReport getStaticReport(Long var1) throws Exception {
        return null;
    }

    @Override
    public Map<String, String> getBillRelationInfo(Map<String, String> map) throws Exception {
        return null;
    }

    @Override
    public boolean isItf(String fullname, String itfName) throws Exception {
        return false;
    }

    @Override
    public Object getEntityAttr(String fullname, String attr) throws Exception {
        return null;
    }
}
