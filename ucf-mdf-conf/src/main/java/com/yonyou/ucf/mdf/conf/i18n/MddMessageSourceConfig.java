package com.yonyou.ucf.mdf.conf.i18n;

import com.yonyou.iuap.ucf.multilang.runtime.UcfStaticMessageSource;
import com.yonyou.ucf.mdd.common.context.MddBaseContext;
import com.yonyou.ucf.mdd.redis.factory.PoolFactory;
import com.yonyou.ucf.mdd.redis.load.ConfigLoader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.util.Pool;

@Component
@DependsOn("mddBaseContext")
public class MddMessageSourceConfig implements InitializingBean {

    /**
     * 当前服务激活的profile
     */
    @Value("${spring.profiles.active}")
    private String profiles;

    /**
     * 当前服务域名
     */
    @Value("${mdd.mullang.domain.url}")
    private String domainurl;

    @Override
    public void afterPropertiesSet() throws Exception {
        UcfStaticMessageSource messageSource = MddBaseContext.getBean("messageSource",UcfStaticMessageSource.class);
        if(!ConfigLoader.redisEnable()) return;
        int index = ConfigLoader.getDefaultDBIndex();
        //Pool pool = PoolFactory.getJedisPoolUseConfDb();
        Pool pool = PoolFactory.getPool(); //本地测试未设置密码使用
        if(pool instanceof JedisPool){
            messageSource.configRedis(profiles, (JedisPool) pool, domainurl, index);
        }
    }
}