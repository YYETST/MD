package com.yonyou.ucf.mdf.app.controller;

import com.yonyou.ucf.mdd.common.model.uimeta.filter.vo.FilterVO;
import com.yonyou.ucf.mdd.common.utils.json.GsonHelper;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BaseController {

    public void renderJson(HttpServletResponse response, String json){
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        try {
            response.getWriter().write(json);
        } catch (IOException var3) {
            var3.printStackTrace();
        }
    }

    public FilterVO parseFilterVO(String condition, FilterVO filterVO, HttpServletRequest request){
        if(request.getMethod().equals(RequestMethod.GET.name())){
            return (FilterVO) GsonHelper.FromJSon(condition,FilterVO.class);
        }else if(request.getMethod().equals(RequestMethod.POST.name())){
            return filterVO;
        }else {
            return new FilterVO();
        }
    }

}
