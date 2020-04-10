package com.yonyou.ucf.mdf.conf;

import com.yonyou.ucf.mdd.conf.DataSourceFactory;
import org.imeta.spring.support.db.DefaultJdbcTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class IMetaDefaultJdbcTemplateConfig {

    @Bean
    public DefaultJdbcTemplate defaultJdbcTemplate() {
        DataSource dataSource = DataSourceFactory.get(DataSourceFactory.dsPerfix+DataSourceFactory.bizDS);
        DefaultJdbcTemplate defaultJdbcTemplate = new DefaultJdbcTemplate();
        defaultJdbcTemplate.setDataSource(dataSource);
        return defaultJdbcTemplate;
    }


}
