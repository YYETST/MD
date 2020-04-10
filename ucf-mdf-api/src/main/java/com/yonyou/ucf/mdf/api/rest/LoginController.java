package com.yonyou.ucf.mdf.api.rest;

import com.yonyou.ucf.mdd.common.interfaces.login.ILoginService;
import com.yonyou.ucf.mdf.app.common.ResultMessage;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.Nullable;
import org.imeta.spring.web.http.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 登录相关Controller
 */
@RestController
@Slf4j
public class LoginController {

    private static final String YHT_ACCESS_TOKEN = "yht_access_token";
    private static final String TOEKN_KEY = "token";

    @Autowired
    private ILoginService loginService;

    @CrossOrigin
    @RequestMapping("/user/getOrgsAndStores")
    public String getLoginInfoMap(@RequestBody @Nullable Map<String, String> params, HttpServletRequest request, HttpServletResponse response) {
        String yhtAccessToken = null;
        if (null != params) {
            yhtAccessToken = params.get(YHT_ACCESS_TOKEN);
            if (null == yhtAccessToken || "null".equals(yhtAccessToken)) {
                yhtAccessToken = params.get(TOEKN_KEY);
            }
        }
        if (null == yhtAccessToken || "null".equals(yhtAccessToken)) {
            yhtAccessToken = CookieUtils.getValue(request, YHT_ACCESS_TOKEN);
        }
        if (null == yhtAccessToken) {
            return ResultMessage.error("yhtAccessToken校验失败");
        }
        try {
            Map<String, Object> loginInfo = loginService.getLoginInfoByYhtToken(yhtAccessToken);
            return ResultMessage.data(loginInfo);
        } catch (Exception e) {
            return ResultMessage.error(e.getMessage());
        }
    }

}
