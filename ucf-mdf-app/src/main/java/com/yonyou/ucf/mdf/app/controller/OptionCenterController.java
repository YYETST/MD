package com.yonyou.ucf.mdf.app.controller;

import com.yonyou.ucf.mdd.option.model.vo.GroupPageVO;
import com.yonyou.ucf.mdd.option.model.vo.OptionDataVO;
import com.yonyou.ucf.mdd.option.model.vo.ResponseVO;
import com.yonyou.ucf.mdd.option.model.vo.UpdateOptionDataVO;
import com.yonyou.ucf.mdd.option.service.IOptionCenterService;
import com.yonyou.ucf.mdf.domain.util.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/optionCenter")
//@RestController 非必要，暂时未抽取完成。
public class OptionCenterController {

    private static final Logger logger = LoggerFactory.getLogger(OptionCenterController.class);

    private IOptionCenterService service;

    @Autowired
    public void setService(IOptionCenterService service) {
        this.service = service;
    }

    @GetMapping("/getOptionGroupMeta")
    public ResponseVO<GroupPageVO> getOptionGroupMeta(@RequestParam(required = false) String systemCode,
                                                      @RequestParam(required = false) String optionId) {
        systemCode = cleanNullParameter(systemCode);
        optionId = cleanNullParameter(optionId);
        return ResponseVO.success(service.getGroupMeta(systemCode, optionId, CommonUtil.getTenantId()));
    }

    @GetMapping("/getOptions")
    public ResponseVO<List<OptionDataVO>> getOptionData(@RequestParam(required = false) String systemCode,
                                                        @RequestParam(required = false) String optionId,
                                                        @RequestParam(required = false) String orgId) {
        systemCode = cleanNullParameter(systemCode);
        optionId = cleanNullParameter(optionId);
        orgId = cleanNullParameter(orgId);
        return ResponseVO.success(service.getOptionDataList(systemCode, optionId, orgId,CommonUtil.getTenantId()));
    }

    @PostMapping("/saveOptions")
    public ResponseVO<Void> updateOptionData(@RequestBody UpdateOptionDataVO updateOptionDataVO) {
        String voorgid = updateOptionDataVO.getOrgId();
        service.setOptionDataValue(updateOptionDataVO,null==voorgid?CommonUtil.getOrgId():voorgid,CommonUtil.getTenantId());
        return ResponseVO.success();
    }

    @ExceptionHandler
    public ResponseVO<Void> exceptionHandler(Exception e) {
        logger.error("OptionCenter 操作失败: " + e.toString(), e);
        return ResponseVO.fail(e.getMessage());
    }

    private String cleanNullParameter(String parameter) {
        return "undefined".equals(parameter)
                || "null".equals(parameter)
                || StringUtils.isBlank(parameter)
                ? null : parameter;
    }
}
