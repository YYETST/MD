package com.yonyou.ucf.mdf.domain.redis;

import com.yonyou.ucf.mdd.redis.core.MddRedisManager;
import org.springframework.stereotype.Repository;

/**
 * redis缓存测试
 */
@Repository
public class RedisManagerTest {

    public void test(){
        MddRedisManager.setString("nsc","111",60);
        System.out.println(MddRedisManager.getValue("nsc"));
    }
}
