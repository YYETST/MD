package com.yonyou.ucf.mdf.refex;

import com.yonyou.ucf.mdd.api.dto.PageInfo;
import com.yonyou.ucf.mdd.api.dto.RefDataParam;
import com.yonyou.ucf.mdd.api.interfaces.rpc.IRefApi;
import com.yonyou.ucf.mdd.api.utils.DubboUtils;
import com.yonyou.ucf.mdd.common.constant.MddConstants;
import com.yonyou.ucf.mdd.common.exceptions.ExceptionSubCode;
import com.yonyou.ucf.mdd.common.exceptions.MddBaseException;
import com.yonyou.ucf.mdd.common.exceptions.MddExceptionType;
import com.yonyou.ucf.mdd.common.interfaces.ref.IRefDataHandler;
import com.yonyou.ucf.mdd.common.model.Pager;
import com.yonyou.ucf.mdd.common.model.ref.RefEntity;
import com.yonyou.ucf.mdd.common.model.uimeta.UIMetaBaseInfo;
import com.yonyou.ucf.mdd.common.model.uimeta.filter.vo.QueryPagerVo;
import com.yonyou.ucf.mdd.common.utils.json.GsonHelper;
import lombok.extern.slf4j.Slf4j;
import org.imeta.orm.schema.QueryPager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 测试ref 扩展
 */
@Slf4j
@Service("rpcRefDataHandler")
public class RpcRefDataHandler implements IRefDataHandler {
    @Override
    public <T> List<?> getTreeData(UIMetaBaseInfo uiMetaBaseInfo, RefEntity entity, Map<String, Object> refpara, T tenantId) throws Exception {
        log.debug(">>>>>>>GetTreeData");
        try {
            RefDataParam param = convertParam(uiMetaBaseInfo,entity,refpara,tenantId);
            String domain = entity.domain;
            IRefApi service = DubboUtils.getDubboService(IRefApi.class,domain,null);
            return service.getTreeData(param);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new MddBaseException(MddExceptionType.BASE_EXCEPTION,e.getMessage(),e, ExceptionSubCode.DEFAULT_EXCEPTION);
        }
    }

    @Override
    public <T> Pager getGridData(UIMetaBaseInfo uiMetaBaseInfo, RefEntity entity, Map<String, Object> refpara, T tenantId) throws Exception {
        log.debug(">>>>>>>GetGridData");
        try {
            RefDataParam param = convertParam(uiMetaBaseInfo,entity,refpara,tenantId);
            String domain = entity.domain;
            IRefApi service = DubboUtils.getDubboService(IRefApi.class,domain,null);
            return service.getGridData(param);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new MddBaseException(MddExceptionType.BASE_EXCEPTION,e.getMessage(),e, ExceptionSubCode.DEFAULT_EXCEPTION);
        }
    }

    private <T> RefDataParam convertParam(UIMetaBaseInfo uiMetaBaseInfo, RefEntity entity, Map<String, Object> refpara, T tenantId){

        RefDataParam param = new RefDataParam();
        //设置refentity
        param.setRefEntity(entity);
        //设置dataType
        String dataType = (String) refpara.get(MddConstants.PARAM_DATA_TYPE);
        param.setDataType(dataType);

        // 转化分页信息
        PageInfo pi = new PageInfo(0, 10);
        Object page = refpara.get(MddConstants.PARAM_PAGE);
        if (null != page) {
            QueryPager pager=null;
            if(page instanceof String){
                pager=(QueryPager) GsonHelper.FromJSon((String)page, QueryPager.class);
            }else{
                QueryPagerVo pager0=(QueryPagerVo)page;
                pager=pager0.toQueryPager();
            }
            if(pager!=null){
                pi = new PageInfo(pager.pageIndex(),pager.pageSize());
            }
        }
        param.setPage(pi);

        //设置likeValue
        if (null != refpara.get(MddConstants.PARAM_LIKE_VALUE)) {
            String likeValue = (String) refpara.get(MddConstants.PARAM_LIKE_VALUE);
            param.setLikeValue(likeValue);
        }

        //设置path
        if (null != refpara.get(MddConstants.PARAM_PATH)) {
            String path = String.valueOf(refpara.get(MddConstants.PARAM_PATH));
            param.setLikeValue(path);
        }

        //设置数据权限 是否实现待定
        //结合解决方案解析扩展条件 TODO
        return param;

    }
}
