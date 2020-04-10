package com.yonyou.ucf.mdf.app.controller;

import com.yonyou.ucf.mdd.api.interfaces.rpc.IUimetaApi;
import com.yonyou.ucf.mdd.api.utils.DubboUtils;
import com.yonyou.ucf.mdd.common.constant.MddConstants;
import com.yonyou.ucf.mdd.common.interfaces.context.ISimpleUser;
import com.yonyou.ucf.mdd.common.model.uimeta.ui.Action;
import com.yonyou.ucf.mdd.common.model.uimeta.ui.View;
import com.yonyou.ucf.mdd.common.model.uimeta.ui.ViewModel;
import com.yonyou.ucf.mdd.ext.base.user.User;
import com.yonyou.ucf.mdd.ext.core.AppContext;
import com.yonyou.ucf.mdd.uimeta.api.UIMetaEngine;
import com.yonyou.ucf.mdd.uimeta.service.MetaService;
import com.yonyou.ucf.mdd.uimeta.util.UIMetaUtils;
import com.yonyou.ucf.mdf.app.common.ResultMessage;
import com.yonyou.ucf.mdf.domain.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/billmeta")
public class BillMetaController extends BaseController {

    @Autowired
    private MetaService metaService;

    @RequestMapping("/getbill")
    public void getBill(Long tplid, String billno, Long tplmode, String bIncludeViewModel, String bIncludeView, String bDesignMode, boolean isSum, Long groupSchemaId, Long reportId, String terminalType, HttpServletRequest request, HttpServletResponse response) {
        try {
            String groupCode = request.getParameter("groupcode");
            if (StringUtils.isEmpty(groupCode)) {
                groupCode=null;
            }
            String domain = request.getParameter("domain");
            if (StringUtils.isEmpty(domain)) {
                domain=null;
            }else{
                //TODO 其他的处理逻辑
            }
            Map<String, Object> application = UIMetaEngine.getInstance().getMeta(CommonUtil.getUserId(), CommonUtil.getTenantId(), tplid, billno, tplmode, bIncludeViewModel, bIncludeView, bDesignMode, isSum, groupSchemaId, terminalType, groupCode, domain);
            String protocolType = StringUtils.isBlank(request.getParameter("protocolType")) ? "0" : request.getParameter("protocolType");
            application = UIMetaUtils.processUIMetaBeforeReturn(application,protocolType);
            renderJson(response, ResultMessage.data(application));
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            renderJson(response, ResultMessage.error(e.getMessage()));
        }

    }

    @RequestMapping("/getbillstruct")
    public void getBillStruct(String billno, HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(MddConstants.PARAM_BILL_NUMB, billno);
            params.put(MddConstants.PARAM_B_INCLUDE_VIEWMODEL, true);
            params.put(MddConstants.PARAM_B_INCLUDE_VIEW, false);
            params.put(MddConstants.PARAM_B_DESIGN_MODE, false);
            params.put(MddConstants.PARAM_IS_SUM, false);

            Map<String, Object> result = metaService.getMeta(params);
            ViewModel viewmodel = (ViewModel) result.get(MddConstants.STR_VIEW_MODEL);
            renderJson(response, ResultMessage.data(viewmodel));
        } catch (Exception e) {
            renderJson(response, ResultMessage.error(e.getMessage()));
        }
    }

    /**
     * 更新billitem设置
     *
     * @param billitems 栏目集合
     * @param request
     * @param response
     */
    @RequestMapping("/groupset")
    public <T> void updateBillitems(@RequestBody List<Map<String, Object>> billitems, HttpServletRequest request, HttpServletResponse response) {
        try {
            T userId = CommonUtil.getUserId();
            UIMetaEngine.getInstance().updateBillitems(billitems, userId, null);
            renderJson(response, ResultMessage.success());
        } catch (Exception ex) {
            ex.printStackTrace();
            renderJson(response, ResultMessage.error(ex.getMessage()));
        }

    }

    @RequestMapping("/restoreUserSet")
    public void restoreUserSet(String billno, Long tplid, String groupcode,boolean isSum, HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = AppContext.getCurrentUser();
            Long userid = null;
            if (user != null)
                userid = user.getId();
            if (userid == null && "true".equalsIgnoreCase(request.getParameter("debug")))
                userid = 2L;
            Object controls=null;
            String domain=null;
            if(billno.indexOf(".")>0){
                String[] arr=billno.split("\\.");
                domain=arr[0];
                billno=arr[1];
            }
            if(null!=domain){
                IUimetaApi billService= DubboUtils.getDubboService(IUimetaApi.class,domain,null);
                if(null!=billService){
                    billService.restoreUserSet(userid, billno,tplid,groupcode);
                    controls = billService.getGroupControls(billno, tplid, groupcode, userid,isSum);
                }
            }else{
                metaService.restoreUserSet(userid, billno,tplid,groupcode);
                controls = metaService.getGroupControls(billno, tplid, groupcode, userid,isSum);
            }
            renderJson(response, com.yonyou.ucf.mdd.ext.util.ResultMessage.data(controls));
        } catch (Exception ex) {
            ex.printStackTrace();
            renderJson(response, com.yonyou.ucf.mdd.ext.util.ResultMessage.error(ex.getMessage()));
        }
    }

    @RequestMapping("/simplevm")
    public void getSimpleViewModel(String billno, Long tplid, HttpServletRequest request, HttpServletResponse response) {
//        try {
//            String tenantId = AppContext.getTenantId;
//            ViewModel vm = metaService.getSimpleVM(billno,tplid,tenantId);
//            renderJson(response, ResultMessage.permissions(vm));
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            renderJson(response, ResultMessage.error(ex.getMessage()));
//        } //TODO
    }


    @RequestMapping("/group")
    public <E,T> void getSimpleViewModel(String billno, Long tplid, String groupcode, HttpServletRequest request,boolean isSum,
                                         HttpServletResponse response) {
        try {
            ISimpleUser user = CommonUtil.getCurrentUser() ; // AppContext.getCurrentUser();
            E userid = null;
            T tenantId = CommonUtil.getTenantId();
            if (user != null) {
                userid = user.getUserId();
            }
            if (userid == null && "true".equalsIgnoreCase(request.getParameter("debug"))) {
                userid = (E)Long.valueOf(2L);
            }
            List<Map<String, Object>> controls = UIMetaEngine.getInstance().getGroupControls(billno, tplid, groupcode, false, userid, tenantId, null);
            renderJson(response, ResultMessage.data(controls));
        } catch (Exception ex) {
            log.error("getSimpleViewModel:{}", ex);
            renderJson(response, ResultMessage.error(ex.getMessage()));
        }
    }

    @RequestMapping("/tpllist")
    public void getTemplateList(String billno, Integer mode, String terminalType, HttpServletRequest request, HttpServletResponse response) {
        try {
            String domain = request.getParameter("domain");
            if (StringUtils.isEmpty(domain)) {
                domain=null;
            }else{
                //TODO 其他的处理逻辑
            }
            List<View> views = UIMetaEngine.getInstance().getTemplateList(billno, mode, terminalType, CommonUtil.getTenantId(), domain);
            renderJson(response, ResultMessage.data(views));
        } catch (Exception ex) {
            ex.printStackTrace();
            renderJson(response, ResultMessage.error(ex.getMessage()));
        }
    }

    @RequestMapping("/getbillcommands")
    public void getBillCommands(String billno, HttpServletRequest request, HttpServletResponse response) {
        try {
            String domain = request.getParameter("domain");
            if (StringUtils.isEmpty(domain)) {
                domain=null;
            }else{
                //TODO 其他的处理逻辑
            }
            List<Action> commands = UIMetaEngine.getInstance().getCommands(billno, CommonUtil.getTenantId(), domain);
            renderJson(response, ResultMessage.data(commands));
        } catch (Exception e) {
            renderJson(response, ResultMessage.error(e.getMessage()));
        }
    }
}

