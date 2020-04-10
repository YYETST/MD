package com.yonyou.ucf.mdf.app.controller;

import com.alibaba.fastjson.JSON;
import com.yonyou.ucf.mdd.redis.core.MddRedisManager;
import com.yonyou.ucf.mdd.uimeta.api.UIMetaEngine;
import com.yonyou.ucf.mdd.uimeta.poi.model.POIDto;
import com.yonyou.ucf.mdf.app.common.ResultMessage;
import com.yonyou.ucf.mdf.domain.util.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/billtemp/")
public class BillPOIController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(BaseController.class);

    @RequestMapping("export")
    public void export(@RequestBody POIDto poiDto, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        try {
            poiDto.setTenantId(CommonUtil.getTenantId());
            poiDto.setUserId(CommonUtil.getUserId());
            UIMetaEngine.getInstance().exportTemplate(poiDto, response);
        } catch (Exception e) {
            logger.error(e.getMessage());
            renderJson(response, ResultMessage.error(e.getMessage()));
        }
    }


    @RequestMapping("getImportProcess")
    public void  getImportProcess(@RequestParam(value = "asyncKey", required = true) String asyncKey, HttpServletRequest request, HttpServletResponse response)throws Exception{
        try {
            String value= MddRedisManager.getValue(asyncKey);
            if (StringUtils.isBlank(value)) {
                logger.error("#######BillPOIContoller::getImportProcess，获取导入进度信息异常");
                MddRedisManager.del(asyncKey);
                renderJson(response, ResultMessage.error("获取导入进度信息异常"));
            }
            Map<String, Object> processMap = (Map<String, Object>) JSON.parse(value);
            if (null != processMap.get("code") && 999 == (Integer) processMap.get("code")) {
                MddRedisManager.del(asyncKey);
                renderJson(response, ResultMessage.error((String) processMap.get("message")));
            } else {
                renderJson(response, ResultMessage.data(value));
            }
        } catch (Exception e) {
            logger.error("获取导入进程失败" + e.getMessage(), e);
            MddRedisManager.del(asyncKey);
            renderJson(response, ResultMessage.error(e.getMessage()));
        }
    }

}
