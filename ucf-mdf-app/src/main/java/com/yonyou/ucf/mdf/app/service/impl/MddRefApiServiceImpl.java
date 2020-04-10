package com.yonyou.ucf.mdf.app.service.impl;

import com.alibaba.dubbo.rpc.RpcContext;
import com.yonyou.ucf.mdd.api.dto.RefDataParam;
import com.yonyou.ucf.mdd.api.interfaces.rpc.IRefApi;
import com.yonyou.ucf.mdd.common.constant.MddConstants;
import com.yonyou.ucf.mdd.common.context.MddBaseContext;
import com.yonyou.ucf.mdd.common.interfaces.ref.IRefService;
import com.yonyou.ucf.mdd.common.model.Pager;
import com.yonyou.ucf.mdd.common.model.ref.RefEntity;
import com.yonyou.ucf.mdd.common.model.ref.RefInfo;
import com.yonyou.ucf.mdd.common.model.uimeta.UIMetaBaseInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Title:
 * Description: TODO
 *
 * @author fuxhh
 * @Date: 2020/2/14 14:53
 * @Version 1.0
 */
@Service("mddRefApiService")
public class MddRefApiServiceImpl implements IRefApi {
    @Autowired
    @Qualifier("mddRefService")
    private IRefService refService;

    @Override
    public Object getRefData(UIMetaBaseInfo uiMetaBaseInfo, Object refEntity, Map<String, Object> refParam) throws Exception {
        if (StringUtils.isNotBlank(refParam.get(MddConstants.PARAM_TENANT_ID).toString())) {
            MddBaseContext.setTenantId(refParam.get(MddConstants.PARAM_TENANT_ID));
            return refService.getRefData(uiMetaBaseInfo, (RefEntity) refEntity, refParam, refParam.get(MddConstants.PARAM_TENANT_ID));
        } else {
            MddBaseContext.setTenantId(refParam.get(MddConstants.PARAM_TENANT_ID));
            return refService.getRefData(uiMetaBaseInfo, (RefEntity) refEntity, refParam, RpcContext.getContext().getAttachment("tenantId"));
        }
    }

    @Override
    public List<? extends Map<String, Object>> getTreeData(RefDataParam refDataParam) {
        return null;
    }

    @Override
    public Pager getGridData(RefDataParam refDataParam) {
        return null;
    }
}
