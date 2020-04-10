package com.yonyou.ucf.mdf;

import com.yonyou.diwork.config.DiworkEnv;
import com.yonyou.diwork.filter.DiworkRequestListener;
import com.yonyou.iuap.ucf.log.filter.MDCLogFilter;
import com.yonyou.ucf.mdd.api.utils.DubboConfig;
import com.yonyou.ucf.mdd.common.constant.MddConstants;
import com.yonyou.ucf.mdd.common.filter.XssAndSqlFilter;
import com.yonyou.ucf.mdd.redis.core.MddRedisManager;
import com.yonyou.ucf.mdd.redis.load.ConfigLoader;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Arrays;


/**
 * spring-boot 入口类
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, RabbitAutoConfiguration.class})
@ComponentScan(basePackages = {"com.yonyou", "com.yonyoucloud"},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = {"com.yonyou.ucf.mdd.ext.*.*","com.yonyoucloud.uretail.*"})})
@ImportResource({DiworkEnv.DIWORK_CONFIG_XML})
@EnableAsync
public class MDFApplication {

    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(MDFApplication.class, args);
    }

    /**
     * 配置请求上下文拦截器
     */
    @Bean
    public FilterRegistrationBean RequestListener() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new DiworkRequestListener());
        registrationBean.addUrlPatterns("/*");
        registrationBean.addInitParameter("excludedPages", "/billstaterule,/pub/fileupload/download,/bpm/complete,/bpm/registerInterface,/bpm/,/upd/,/test");
        return registrationBean;
    }

    // 注册Filter
    @Bean
    public FilterRegistrationBean xssAndSqlFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new XssAndSqlFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

//    @Bean
//    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
//        StrictHttpFirewall firewall = new StrictHttpFirewall();
//        firewall.setAllowUrlEncodedSlash(true);
//        return firewall;
//    }

    /**
     * ucf-log 日志接入
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean LogFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new MDCLogFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

}
