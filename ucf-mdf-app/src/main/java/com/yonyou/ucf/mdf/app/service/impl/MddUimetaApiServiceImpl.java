package com.yonyou.ucf.mdf.app.service.impl;

import com.yonyou.ucf.mdd.api.interfaces.rpc.IUimetaApi;
import com.yonyou.ucf.mdd.common.model.rule.RuleContext;
import com.yonyou.ucf.mdd.common.model.uimeta.ui.Action;
import com.yonyou.ucf.mdd.common.model.uimeta.ui.View;
import com.yonyou.ucf.mdd.common.model.uimeta.ui.ViewModel;
import com.yonyou.ucf.mdd.uimeta.service.MetaService;
import org.imeta.orm.schema.QueryOrderby;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Title:
 * Description: TODO
 *
 * @author fuxhh
 * @Date: 2020/2/14 15:37
 * @Version 1.0
 */
@Service("mddUimetaApiService")
public class MddUimetaApiServiceImpl implements IUimetaApi {
    @Autowired
    private MetaService metaService;

    @Override
    public Map<String, Object> getMeta(Map<String, Object> params) throws Exception {
        return metaService.getMeta(params);
    }

    @Override
    public <T> Long getTemplateId(String billno, Long tplmode, T tenantId) {
        return metaService.getTemplateId(billno,tplmode,tenantId);
    }

    @Override
    public <T> List<View> getTemplateList(String billno, Integer mode, String terminalType, T tenantId) {
        return metaService.getTemplateList(billno,mode,terminalType,tenantId);
    }

    @Override
    public <T> List<Action> getCommands(String billno, T tenantId) {
        return metaService.getCommands(billno,tenantId);
    }

    @Override
    public <T, E> ViewModel getSimpleVM(String billNo, E tplid, T tenantId) {
        return metaService.getSimpleVM(billNo,tplid,tenantId);
    }

    @Override
    public <T, E> ViewModel getSimpleVM(String billNo, Boolean isTplExport, Boolean isMain, E tplid, T tenantId) {
        return metaService.getSimpleVM(billNo,isTplExport,isMain,tplid,tenantId);
    }

    @Override
    public Object getSimpleVM(String billNo, Long tplid, Boolean isTplExport, Boolean isMain) throws Exception {
        return metaService.getSimpleVM(billNo,tplid,isTplExport,isMain);
    }

    @Override
    public List<Map<String, Object>> getGroupControls(Map<String, Object> paras) {
        return metaService.getGroupControls(paras);
    }

    @Override
    public void saveUserItemsSet(Map<String, Object> params) {
        metaService.saveUserItemsSet(params);
    }

    @Override
    public void restoreUserSet(Object userId, String billno, Long tplid, String groupcode) throws Exception {
        metaService.restoreUserSet(userId,billno,tplid,groupcode);
    }

    @Override
    public <T> List<QueryOrderby> getListQueryOrderbys(String billnum, String listBillNum, T tenantId) {
        return metaService.getListQueryOrderbys(billnum,listBillNum,tenantId);
    }

    @Override
    public RuleContext getBillContext(String billnum) {
        return null;
    }

    @Override
    public Object getViewModel(String billnum, Map<String, Object> param) throws Exception {
        return null;
    }

    @Override
    public Map<String, Object> getBillMeta(boolean bIncludeViewModel, boolean bIncludeView, Map<String, Object> params, boolean bDesignMode, boolean isSum) throws Exception {
        return null;
    }

    @Override
    public String getFirstChildField(String fullname) throws Exception {
        return null;
    }

    @Override
    public Object getMetaFilters(int id) {
        return null;
    }

    @Override
    public Object getFiltersInfo(int filtersId) throws Exception {
        return null;
    }

    @Override
    public Object getSolutions(int userId, int filterId, String terminalType) {
        return null;
    }

    @Override
    public Object getSolutionInfo(String viewid, int solutionId) {
        return null;
    }

    @Override
    public Object getFilterQuick(int filterId, int userId) {
        return null;
    }
}
