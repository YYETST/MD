package com.yonyou.ucf.mdf.app.interceptor;

import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.metadata.unified.context.UnifiedMDContextHolder;
import com.yonyou.ucf.mdd.common.constant.MddConstants;
import com.yonyou.ucf.mdd.common.context.MddBaseContext;
import com.yonyou.ucf.mdd.common.interfaces.login.ILoginService;
import com.yonyou.ucf.mdd.common.model.SimpleUser;
import com.yonyou.ucf.mdf.domain.util.ApplicationContextUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SnWebAppInterceptor implements HandlerInterceptor {

    private final ILoginService loginService = MddBaseContext.getBean(ILoginService.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ApplicationContextUtil.clearThreadLocal();// 删除线程缓存数据
        ApplicationContextUtil.setThreadContext("userId", InvocationInfoProxy.getUserid());
        ApplicationContextUtil.setThreadContext("tenantId", InvocationInfoProxy.getTenantid());
        ApplicationContextUtil.setThreadContext("accessToken", String.valueOf(InvocationInfoProxy.getParameter("yht_access_token")));
        ApplicationContextUtil.setThreadContext(MddConstants.PARAM_TOKEN, String.valueOf(InvocationInfoProxy.getParameter("yht_access_token")));
        // 获取user对象放入上下文，作为一个全局变量,暂时不考虑缓存
        SimpleUser user = (SimpleUser) loginService.getUserByYhtToken(ApplicationContextUtil.getThreadContext(MddConstants.PARAM_TOKEN));
        ApplicationContextUtil.setThreadContext(MddConstants.PARAM_USER, user);

        UnifiedMDContextHolder.setTenantId(InvocationInfoProxy.getTenantid());

        //serviceCode
        String serviceCode = request.getParameter("serviceCode");
        InvocationInfoProxy.setExtendAttribute("serviceCode",serviceCode);
        ApplicationContextUtil.setThreadContext("serviceCode",serviceCode);
        MddBaseContext.setThreadContext("serviceCode",serviceCode);

        return true;
    }
}