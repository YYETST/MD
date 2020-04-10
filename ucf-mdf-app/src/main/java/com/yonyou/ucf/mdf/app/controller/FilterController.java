package com.yonyou.ucf.mdf.app.controller;

import com.yonyou.ucf.mdd.common.exceptions.MddBaseException;
import com.yonyou.ucf.mdd.uimeta.api.UIMetaEngine;
import com.yonyou.ucf.mdf.app.common.ResultMessage;
import com.yonyou.ucf.mdf.domain.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 查询方案
 *
 * @author yantao
 */
@Controller(value = "FilterController")
@RequestMapping("/filter/")
public class FilterController extends BaseController {

    @RequestMapping(value = "/{solutionId}/solutionFilters",method = RequestMethod.GET)
    public void getAllCommonFilters(@PathVariable int solutionId, HttpServletRequest request, HttpServletResponse response){
        try {
            String viewid=request.getParameter("viewid");
            Map<String, Object> allCommonFilters = UIMetaEngine.getInstance().getAllCommonFilters(solutionId, viewid, CommonUtil.getUserId(), CommonUtil.getTenantId(), null);
            renderJson(response, ResultMessage.data(allCommonFilters));
        }catch (Exception e){
            String msg = e.getMessage();
            if(e instanceof MddBaseException && StringUtils.isNotBlank(((MddBaseException) e).getMsg())){
                msg = ((MddBaseException) e).getMsg();
            }
            renderJson(response, ResultMessage.error(msg));
        }
    }

}
