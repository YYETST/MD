package com.yonyou.ucf.mdf.domain.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
@Slf4j
public final class ApplicationContextUtil implements ApplicationContextAware, InitializingBean {

    private static final String DEFAULT_ENCODING = "UTF-8";

    private static ApplicationContext applicationContext;

    private static ApplicationContextUtil instance;

    private Properties configuration = new Properties();

    private static PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();

    @Value("application.properties")
    private String configLocation;

    private static final ThreadLocal<Map<String, Object>> params = new ThreadLocal<>();

    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        applicationContext = ac;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Properties getAppConfig() {
        return instance.configuration;
    }

    /**
     * applicationContext.getbean
     *
     * @param beanName
     * @return
     */
    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    /**
     * applicationContext.getbean
     *
     * @param beanName
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String beanName, Class<T> clazz) {
        return applicationContext.getBean(beanName, clazz);
    }

    /**
     * applicationContext.getbean
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T getThreadContext(String key) {

        Map<String, Object> map = params.get();
        if (map == null) {
            return null;
        }
        Object obj = map.get(key);
        if (obj == null) {
            return null;
        }
        return (T) obj;
    }

    /**
     * @param key
     * @param value
     */
    public static void setThreadContext(String key, Object value) {
        Map<String, Object> map = params.get();
        if (map == null) {
            map = new HashMap<>();
            params.set(map);
        }
        map.put(key, value);
    }

    /**
     * 删除上下文中的key
     *
     * @param key
     */
    public static void delContext(String key) {
        Map<String, Object> map = params.get();
        if (null != map && map.containsKey(key)) {
            map.remove(key);
        }
    }

    /**
     * 清除线程级缓存
     */
    public static void clearThreadLocal() {
        params.remove();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        instance = this;
        loadConfig();
    }

    private void loadConfig() throws IOException {
        String[] configs = configLocation.split(",");
        for (String config : configs) {
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource resource = resolver.getResource(config);
            //configuration
            //this.configuration = PropertiesLoaderUtils.loadProperties(resource);
            InputStream is = null;
            try {
                is = resource.getInputStream();
                propertiesPersister.load(this.configuration, new InputStreamReader(is, DEFAULT_ENCODING));
            } catch (IOException ex) {
                log.info("Could not load properties from classpath:" + config + ": " + ex.getMessage());
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        }
    }
}