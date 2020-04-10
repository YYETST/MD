package com.yonyou.ucf.mdf.app.controller;

import com.alibaba.fastjson.JSON;
import com.yonyou.ucf.mdd.api.utils.DubboUtils;
import com.yonyou.ucf.mdd.option.model.OptionData;
import com.yonyou.ucf.mdd.option.service.OptionService;
import com.yonyou.ucf.mdf.app.common.ResultMessage;
import com.yonyou.ucf.mdf.domain.util.CommonUtil;
import com.yonyoucloud.uretail.api.IBillQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 选项
 *
 */

//@RestController 非必要，暂时未抽取完成。 抽取注意营销的是增加了getOrgAuth 方法
//@Authentication(true)
@RequestMapping("/option/")
public class OptionController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(OptionController.class);
    @Autowired
	private OptionService optionService;

	/**
	 * 获取选项元数据
	 * @param optionId
	 * @param request
	 * @param response
	 */
	@RequestMapping("getOptionMeta")
	public void getOptionMeta(String optionId, HttpServletRequest request, HttpServletResponse response){
		try {
			Map<String, Object> metaMap=optionService.getOptionGroup(optionId, CommonUtil.getTenantId(),CommonUtil.getTenantId());
			renderJson(response, ResultMessage.data(metaMap));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取选项元数据失败", e);
			renderJson(response, ResultMessage.error(e.getMessage()));
		}
	}

	/**
	 * 获取选项数据
	 * @param params
	 * @param request
	 * @param response
	 */
	@RequestMapping("getOptionData")
	public void getOptionData(@RequestBody Map<String, Object>params, HttpServletRequest request, HttpServletResponse response){
		try {
			Map<String, Object> map=optionService.getOptionDatas(params,CommonUtil.getTenantId(),CommonUtil.getOrgId());
			renderJson(response, ResultMessage.data(map));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取选项数据失败", e);
			renderJson(response, ResultMessage.error(e.getMessage()));
		}
	}
	/**
	 * 更新选项数据
	 * @param optionDatas 差异数据
	 * @param request
	 * @param response
	 */
	@RequestMapping("updateOption")
	public void updateOption(@RequestBody Map<String, Object> optionDatas, HttpServletRequest request, HttpServletResponse response){
		try {
			logger.info("updateOption {}", JSON.toJSONString(optionDatas));
			optionService.updateOption(optionDatas,CommonUtil.getOrgId(),CommonUtil.getTenantId());
			renderJson(response, ResultMessage.success());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("更新选项数据失败", e);
			renderJson(response, ResultMessage.error(e.getMessage()));
		}

	}
	/**
	 * 恢复默认设置
	 * @param request
	 * @param response
	 */
	@RequestMapping("resetOptions")
	public void resetOptions(@RequestBody Map<String, Object> params, HttpServletRequest request, HttpServletResponse response){
		try {
			optionService.resetOptions(params);
			renderJson(response, ResultMessage.success());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("恢复默认设置失败", e);
			renderJson(response, ResultMessage.error(e.getMessage()));
		}
	}

/**
 * 根据参数获取选项
 * @param params
 * @param request
 * @param response
 */
@RequestMapping("getOptionsByParams")
public void getOptionsByParams(@RequestBody Map<String,Object> params, HttpServletRequest request, HttpServletResponse response){
	try {
		List<OptionData> optionDatas=optionService.getOptionsByParams(params,CommonUtil.getTenantId());
		renderJson(response,ResultMessage.data(optionDatas));
	} catch (Exception e) {
		e.printStackTrace();
		logger.error("根据参数获取选项失败", e);
		renderJson(response, ResultMessage.error(e.getMessage()));
	}
	}

/**
 * 根据组code获取对应的选项值
 * @param params
 * @param request
 * @param response
 */
@RequestMapping("getOptionsByGroupCode")
public void getOptionsByGroupCode(@RequestBody Map<String,Object> params, HttpServletRequest request, HttpServletResponse response){
	try {
		List<OptionData> optionDatas=optionService.getOptionsByGroupCode(params,CommonUtil.getTenantId());
		renderJson(response,ResultMessage.data(optionDatas));
	} catch (Exception e) {
		e.printStackTrace();
		logger.error("获取选项值失败", e);
		renderJson(response, ResultMessage.error(e.getMessage()));
	}
}
@RequestMapping("insertOrgOptionDatas")
public void insertOrgOptionDatas(@RequestBody Map<String,Object> params, HttpServletRequest request, HttpServletResponse response){
	try {
		optionService.insertOrgOptionDatas(params,CommonUtil.getTenantId());
		renderJson(response,ResultMessage.success());
	} catch (Exception e) {
		e.printStackTrace();
		logger.error("组织参数新增失败", e);
		renderJson(response, ResultMessage.error(e.getMessage()));
	}
}
@RequestMapping("delOrgOptionDatas")
public void delOrgOptionDatas(@RequestBody Map<String,Object> params, HttpServletRequest request, HttpServletResponse response){
	try {
		optionService.delOrgOptionDatas(params,CommonUtil.getTenantId());
		renderJson(response,ResultMessage.success());
	} catch (Exception e) {
		e.printStackTrace();
		logger.error("组织参数新增失败", e);
		renderJson(response, ResultMessage.error(e.getMessage()));
	}
}

/**
 * 根据获取对应的选项值
 * @param params
 * @param request
 * @param response
 */
@RequestMapping("getOptionValueByName")
public void getOptionValueByName(@RequestBody Map<String,Object> params, HttpServletRequest request, HttpServletResponse response){
	try {
		logger.info("getOptionValueByName {}", JSON.toJSONString(params));
		String group=params.get("group")==null?null:params.get("group").toString();
		if(params.get("optionName")==null) throw new Exception("参数名称不能为空");
		String optionName=params.get("optionName").toString();
		IBillQueryService billQueryService= DubboUtils.getDubboService(IBillQueryService.class,group, null);
		String orgId=String.valueOf(params.get("orgId"));
		Object value=billQueryService.getOptionValueByName(optionName,orgId);
		renderJson(response,ResultMessage.data(value));
	} catch (Exception e) {
		e.printStackTrace();
		logger.error("获取选项值失败 ,getOptionValueByName ", e);
		renderJson(response, ResultMessage.error(e.getMessage()));
	}
}

/**
 * 根据获取对应的选项值
 * @param params
 * @param request
 * @param response
 */
@SuppressWarnings("unchecked")
@RequestMapping("getOptionValueByNames")
public void getOptionValueByNames(@RequestBody Map<String,Object> params, HttpServletRequest request, HttpServletResponse response){
	try {
		logger.info("getOptionValueByName {}", JSON.toJSONString(params));
		String group=params.get("group")==null?null:params.get("group").toString();
		if(params.get("optionNames")==null) throw new Exception("参数名称不能为空");
		List<String> optionNames=(List<String>) params.get("optionNames");
		String orgId=String.valueOf(params.get("orgId"));
		IBillQueryService billQueryService=DubboUtils.getDubboService(IBillQueryService.class,group, null);
		List<Object> value=billQueryService.getOptionValueByNames(optionNames,orgId);
		renderJson(response,ResultMessage.data(value));
	} catch (Exception e) {
		e.printStackTrace();
		logger.error("获取选项值失败:getOptionValueByNames ", e);
		renderJson(response, ResultMessage.error(e.getMessage()));
	}
}

}
