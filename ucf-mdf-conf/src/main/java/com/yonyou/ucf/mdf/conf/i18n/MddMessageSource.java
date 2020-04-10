package com.yonyou.ucf.mdf.conf.i18n;

import com.yonyou.iuap.international.MultiLangText;
import com.yonyou.iuap.international.exception.BusinessException;
import com.yonyou.iuap.international.rpc.IMultiLangService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
//@Service
//@Primary
public class MddMessageSource implements MessageSource {

    @Autowired
    private IMultiLangService service;

    @Override
    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        if(service != null){
           String msg = getMsgByCode(code,args,locale);
           if(StringUtils.isBlank(msg))
               return defaultMessage;
           return msg;
        }else {
            return defaultMessage;
        }
    }

    @Override
    public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
        if(service != null){
            return getMsgByCode(code,args,locale);
        }else {
            return null;
        }
    }

    @Override
    public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
        String code = resolvable.getCodes()[0];
        String defaultMsg = resolvable.getDefaultMessage();
        Object[] args = resolvable.getArguments();
        return getMessage(code,args,defaultMsg,locale);
    }

    private String getMsgByCode(String code, Object[] args, Locale locale){
        try {
            MultiLangText multiLangText = null;
            multiLangText = service.getMultiLangResource(code);
            if(multiLangText == null){
                return null;
            }
            String msgStr = multiLangText.getText(locale.toString());
            if(args != null && args.length>0)
                msgStr = replaceMatchedArgs(msgStr,args);
            return msgStr;
        } catch (BusinessException e) {
            log.error(e.getMessage(),e);
            return null;
        }
    }

    public static String replaceMatchedArgs(String data, Object[] args){
        Pattern pattern = Pattern.compile("\\{(.+?)\\}");
        Matcher matcher = pattern.matcher(data);

        StringBuffer sb = new StringBuffer();
        int i=0;
        while(matcher.find()) {
            if(args.length>i){
                matcher.appendReplacement(sb, args[i].toString());
                i++;
            }
        }
        matcher.appendTail(sb);

        return sb.toString();
    }
}
