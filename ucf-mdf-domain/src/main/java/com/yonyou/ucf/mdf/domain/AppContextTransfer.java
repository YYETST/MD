package com.yonyou.ucf.mdf.domain;

import com.yonyou.ucf.mdd.common.interfaces.context.IAppContextTransfer;
import com.yonyou.ucf.mdd.common.interfaces.context.ISimpleUser;
import com.yonyou.ucf.mdf.domain.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AppContextTransfer implements IAppContextTransfer {
    @Override
    public <T> T getTenantId() {
        // return CommonUtil.getTenantId();
        T tenantId = null;
        try {
            tenantId = CommonUtil.getTenantId();
        } catch (Exception e) {
            log.info("上下文获取租户异常!");
        }
        try {
            if (null == CommonUtil.getTenantId()) {
                tenantId = CommonUtil.getTenantByToken(CommonUtil.getToken());
            }
            if (null != tenantId) {
                setTenantId(tenantId);
            }
        } catch (Exception e) {
            log.info("通过token获取友互通租户异常");
        }
        return tenantId;
    }

    @Override
    public <T> void setTenantId(T yhtTenantId) {
        CommonUtil.setTenantId(yhtTenantId);
    }

    @Override
    public ISimpleUser getCurrentUser() {
        return CommonUtil.getCurrentUser();
    }

    @Override
    public String getYhtAccessToken() {
        return CommonUtil.getToken();
    }

    @Override
    public void setYhtAccessToken(String yhtAccessToken) {
        CommonUtil.setToken(yhtAccessToken);
    }

    @Override
    public <T> T getTenantByToken(String token) {
        return CommonUtil.getTenantByToken(token);
    }

    @Override
    public Object getContext(String key) {
        return CommonUtil.getContext(key);
    }

    @Override
    public void setContext(String key, Object obj) {
        CommonUtil.setContext(key, obj);
    }

    @Override
    public void delContext(String key) {
        CommonUtil.delContext(key);
    }

    @Override
    public void clearThreadLocal() {
        CommonUtil.clearThreadLocal();
    }

}
