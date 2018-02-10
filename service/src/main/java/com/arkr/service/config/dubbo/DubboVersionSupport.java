package com.arkr.service.config.dubbo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * @author hztanhuayou
 * @date 2018/2/11
 */
class DubboVersionSupport {
    private Properties properties;

    DubboVersionSupport(String source) throws IOException {
        try {
            DefaultResourceLoader loader = new DefaultResourceLoader();
            properties = PropertiesLoaderUtils.loadProperties(loader.getResource(source));
        } catch (IOException e) {
            Logger logger = LoggerFactory.getLogger(getClass());
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    public String fetch(String interfaceName) {
        return properties.getProperty(interfaceName);
    }
}
