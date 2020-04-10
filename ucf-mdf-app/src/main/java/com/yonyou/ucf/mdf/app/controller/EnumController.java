package com.yonyou.ucf.mdf.app.controller;

import com.yonyou.ucf.mdd.common.exceptions.MddBaseException;
import com.yonyou.ucf.mdd.common.interfaces.i18n.IMddMultilingualService;
import com.yonyou.ucf.mdd.common.model.enums.EnumBase;
import com.yonyou.ucf.mdd.enums.service.EnumService;
import com.yonyou.ucf.mdd.enums.util.EnumUtil;
import com.yonyou.ucf.mdf.app.common.ResultMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/enum/")
public class EnumController extends BaseController {

	@Autowired
	private IMddMultilingualService iMddMultilingualService;
	private static Logger logger = LoggerFactory.getLogger(EnumController.class);

	@Autowired
    private EnumService enumService;

    // private static final String INDUSTRY="aa_tradetype";
	/*public EnumService getEnumService() {
		return enumService;
	}*/
	/*public void setEnumService(EnumService enumService) {
		this.enumService = enumService;
	}*/
	@RequestMapping("getEnumMap")
	public void getEnumMap(String enumtype, HttpServletRequest request, HttpServletResponse response){
		try {
			LinkedHashMap<String, String> enumMap = iMddMultilingualService.getEnumMap(enumtype);//EnumUtil.getEnumMap(enumtype,true);
			renderJson(response, ResultMessage.data(enumMap));
		} catch (Exception e) {
		    String msg = e.getMessage();
            if(e instanceof MddBaseException && StringUtils.isNotBlank(((MddBaseException) e).getMsg())){
                msg = ((MddBaseException) e).getMsg();
            }
            logger.error("获取数据异常: " + msg, e);
			renderJson(response, ResultMessage.error(msg));
		}
	}

	@RequestMapping("getEnumArray")
	public void getEnumArray(String enumtype, HttpServletRequest request, HttpServletResponse response){
		try {
			List<Map<String, String>> enumMap = iMddMultilingualService.getEnumArray(enumtype);//EnumUtil.getEnumArray(enumtype,true);
			renderJson(response, ResultMessage.data(enumMap));
		} catch (Exception e) {
			logger.error("获取数据异常", e);
			renderJson(response, ResultMessage.error(e.getMessage()));
		}
	}

	@RequestMapping("getEnumStr")
	public void getEnumStr(String enumtype, HttpServletRequest request, HttpServletResponse response){
		try {
			String enumStr = iMddMultilingualService.getEnumStr(enumtype,false);//EnumUtil.getEnumStr(enumtype,false,true);
			renderJson(response, ResultMessage.data(enumStr));
		} catch (Exception e) {
			logger.error("获取数据异常", e);
			renderJson(response, ResultMessage.error(e.getMessage()));
		}
	}


    @RequestMapping("getEnumStrFetch")
    public void getEnumStrFetch(String enumtype, boolean isFetchArray, HttpServletRequest request, HttpServletResponse response) {
        try {
            String enumStr = iMddMultilingualService.getEnumStr(enumtype,true);//EnumUtil.getEnumStr(enumtype, true);
            renderJson(response, ResultMessage.data(enumStr));
        } catch (Exception e) {
            logger.error("获取数据异常", e);
            renderJson(response, ResultMessage.error(e.getMessage()));
        }
    }

    @RequestMapping("updateEunmByType")
    public void updateEunmByType(String enumtype, HttpServletRequest request, HttpServletResponse response) {
        try {
            EnumUtil.updateEunmByType(enumtype);
            renderJson(response, ResultMessage.success());
        } catch (Exception e) {
            logger.error("更新数据异常", e);
            renderJson(response, ResultMessage.error(e.getMessage()));
        }
    }

    @RequestMapping("insertEnum")
    public void insertEnum(@RequestBody EnumBase enumBase, HttpServletRequest request, HttpServletResponse response) {
        try {
            enumService.insertEnum(enumBase);
            renderJson(response, ResultMessage.success());
        } catch (Exception e) {
            logger.error("插入数据异常", e);
            renderJson(response, ResultMessage.error(e.getMessage()));
        }
    }

    @RequestMapping("updateEnum")
    public void updateEnum(@RequestBody EnumBase enumBase, HttpServletRequest request, HttpServletResponse response) {
        try {
            enumService.updateEnum(enumBase);
            renderJson(response, ResultMessage.success());
        } catch (Exception e) {
            logger.error("更新数据异常", e);
            renderJson(response, ResultMessage.error(e.getMessage()));
        }
    }

    @RequestMapping("deleteEnum")
    public void deleteEnum(@RequestBody EnumBase enumBase, HttpServletRequest request, HttpServletResponse response) {
        try {
            enumService.deleteEnum(enumBase);
            renderJson(response, ResultMessage.success());
        } catch (Exception e) {
            logger.error("删除数据异常", e);
            renderJson(response, ResultMessage.error(e.getMessage()));
        }
    }

}
