package com.arkr.service.config.dubbo;

import com.alibaba.dubbo.config.ServiceConfig;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.config.spring.beans.factory.annotation.ServiceAnnotationBeanPostProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * dubbo 2.6.0 的 @Service {@link Service} @Reference {@link Reference} 注解
 * 不支持SpEL表达式,不利于Dubbo导出的服务版本号的控制.
 * 因此,我们使用 {@link ServicePostProcessor#postProcessAfterInitialization)}
 * 来实现版本号的配置,此操作对源代码进行了侵入.
 * 将来如果 dubbo 支持后将不再使用此方法.
 * <p>
 * 基于以上,所以,在我们的应用程序里
 * 将会直接忽略 version{@link Service#version()}{@link Reference#version()}字段的值.
 * 这里直接重写这个(version)值.
 * </p>
 * <p>
 * 如果你不明白为什么要这么做,那么请不要动这个代码文件!
 * </p>
 *
 * @author hztanhuayou
 * @date 2018/2/10
 * @see Service
 * @see Reference
 * @see ServiceAnnotationBeanPostProcessor
 * @see ReferenceBeforeProcessor
 */
class ServicePostProcessor implements BeanPostProcessor {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${dubbo.optimize.annotation:true}")
    private Boolean optimizeAnnotation;

    @Autowired
    private DubboVersionSupport support;


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (null != optimizeAnnotation && optimizeAnnotation && bean instanceof ServiceConfig) {
            ServiceConfig config = (ServiceConfig) bean;
            Class exportClass = config.getInterfaceClass();
            String fieldClassName = exportClass.getName();
            String fetch = support.fetch(fieldClassName);
            logger.info(fieldClassName + ": origin= " + config.getVersion() + ", newest=" + fetch);
            config.setVersion(fetch);
        }
        return bean;
    }
}
