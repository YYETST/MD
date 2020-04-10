package com.yonyou.ucf.mdf;

import com.yonyou.ucf.mdd.core.meta.MetaDaoDataAccessProxy;
import com.yonyou.ucf.mdd.core.meta.crud.QuerySchemaAllHandlerAdapter;
import com.yonyou.ucf.mdd.core.service.proxy.MddESQuerySchemaService;
import com.yonyou.ucf.mdd.core.service.proxy.MddQuerySchemaServie;
import com.yonyoucloud.uretail.api.IBillQueryService;
import org.imeta.orm.schema.DataAccessProxy;
import org.imeta.orm.schema.QuerySchemaServiceProxy;
import org.imeta.spring.support.orm.QuerySchemaHandlerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
@ImportResource(locations = {"classpath*:/config/applicationContext*.xml"})
public class SpringConfig {

    @Bean
    public QuerySchemaHandlerAdapter serviceDataFetchHandler(@Qualifier("mddQuerySchemaServie") MddQuerySchemaServie mddQuerySchemaServie) {
        QuerySchemaHandlerAdapter serviceDataFetchHandler = new QuerySchemaHandlerAdapter();
        serviceDataFetchHandler.setProxy(mddQuerySchemaServie);
        return serviceDataFetchHandler;
    }

    @Bean
    public QuerySchemaHandlerAdapter esDataFetchHandler(@Qualifier("mddESQuerySchemaServie") MddESQuerySchemaService mddESQuerySchemaServie) {
        QuerySchemaHandlerAdapter esDataFetchHandler = new QuerySchemaHandlerAdapter();
        esDataFetchHandler.setProxy(mddESQuerySchemaServie);
        return esDataFetchHandler;
    }

    @Bean
    public QuerySchemaAllHandlerAdapter serviceDataFetchAllHandler(@Qualifier("mddQuerySchemaServie") MddQuerySchemaServie mddQuerySchemaServie) {
        QuerySchemaAllHandlerAdapter serviceDataFetchAllHandler = new QuerySchemaAllHandlerAdapter();
        serviceDataFetchAllHandler.setProxy(mddQuerySchemaServie);
        return serviceDataFetchAllHandler;
    }

    @Bean
    public QuerySchemaAllHandlerAdapter esDataFetchAllHandler(@Qualifier("mddESQuerySchemaServie") MddESQuerySchemaService mddESQuerySchemaServie) {
        QuerySchemaAllHandlerAdapter esDataFetchAllHandler = new QuerySchemaAllHandlerAdapter();
        esDataFetchAllHandler.setProxy(mddESQuerySchemaServie);
        return esDataFetchAllHandler;
    }

    @Bean
    public QuerySchemaServiceProxy esQuerySchemaServiceProxy(@Qualifier("mddESQuerySchemaServie") MddESQuerySchemaService mddESQuerySchemaServie) {
        return mddESQuerySchemaServie;
    }

    @Bean
    public QuerySchemaServiceProxy serviceQuerySchemaServiceProxy(@Qualifier("mddQuerySchemaServie") MddQuerySchemaServie mddQuerySchemaServie) {
        return mddQuerySchemaServie;
    }

    @Bean
    public DataAccessProxy localDataAccessProxy() {
        return new MetaDaoDataAccessProxy();
    }

    // 多语messageSource 交由Spring容器管理
    // 需要注入一个bean, name 为 messageSource， 实现是com.yonyou.iuap.ucf.multilang.runtime.UcfStaticMessageSource,
    // 例如Spring Boot项目可以按照如下设置:
    @Bean
    public ResourceBundleMessageSource messageSource() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ResourceBundleMessageSource messageSource = (ResourceBundleMessageSource) Class.forName("com.yonyou.iuap.ucf.multilang.runtime.UcfStaticMessageSource").newInstance();
        return messageSource;
    }

}
