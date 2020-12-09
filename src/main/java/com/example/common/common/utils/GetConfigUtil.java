package com.example.common.common.utils;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Properties;

/**
 * 获取配置信息类
 */
public class GetConfigUtil {
    /**
     *
     * @param configName   //配置名称
     * @return
     */
    public static Properties get(String configName) {
        Resource resource = new ClassPathResource(configName);
        Properties properties = null;
        try {
            YamlPropertiesFactoryBean yamlFactory = new YamlPropertiesFactoryBean();
            yamlFactory.setResources(resource);
            properties =  yamlFactory.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return properties;
    }
}
