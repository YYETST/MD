package com.yonyou.ucf.mdf.app.controller;

import com.yonyou.ucf.mdd.common.dto.BaseReqDto;
import com.yonyou.ucf.mdd.common.model.ref.RefInfo;
import com.yonyou.ucf.mdd.uimeta.api.UIMetaEngine;
import com.yonyou.ucf.mdd.uimeta.util.UIMetaUtils;
import com.yonyou.ucf.mdf.app.common.ResultMessage;
import com.yonyou.ucf.mdf.domain.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/pub/ref")//保持和 原前端请求路径一致
public class RefController extends BaseController {

    @RequestMapping("/getRefMeta")//保持和 原前端请求路径一致
    public <T> void getRefMeta(BaseReqDto refReqDto,  HttpServletRequest request, HttpServletResponse response) {
        try {
            // 传入TenantId，进行数据隔离
            if (null == refReqDto.getTenantId() || StringUtils.isBlank(refReqDto.getTenantId().toString())) {
                refReqDto.setTenantId(CommonUtil.getTenantId());
            }
            //TODO 对于元素可见控制需要实现封装 ViewControlParams
            RefInfo refInfo= UIMetaEngine.getInstance().getRefMeta(refReqDto);
            String protocolType = StringUtils.isBlank(request.getParameter("protocolType")) ? "0" : request.getParameter("protocolType");
            refInfo = (RefInfo) UIMetaUtils.processRefMetaBeforeReturn(refInfo,protocolType);
            renderJson(response, ResultMessage.data(refInfo));
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(response, ResultMessage.error(e.toString()));
        }
    }
}
