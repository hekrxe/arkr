package com.arkr.service.config.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.config.spring.beans.factory.annotation.ReferenceAnnotationBeanPostProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.core.PriorityOrdered;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.util.Map;

import static org.springframework.core.BridgeMethodResolver.findBridgedMethod;
import static org.springframework.core.BridgeMethodResolver.isVisibilityBridgeMethodPair;
import static org.springframework.core.annotation.AnnotationUtils.findAnnotation;
import static org.springframework.core.annotation.AnnotationUtils.getAnnotation;


/**
 * 侵入源码操作,需注意 maven 版本的引用,目前使用的是 2.6.0
 *
 * @author hztanhuayou
 * @date 2018/2/11
 * @see ReferenceAnnotationBeanPostProcessor
 * @see ServicePostProcessor
 * @see Service
 * @see Reference
 */
class ReferenceBeforeProcessor extends InstantiationAwareBeanPostProcessorAdapter implements PriorityOrdered {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${dubbo.optimize.annotation:true}")
    private Boolean optimizeAnnotation;


    @Autowired
    private DubboVersionSupport support;

    @Override
    public PropertyValues postProcessPropertyValues(
            PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
        if (null != optimizeAnnotation && optimizeAnnotation && null != bean) {
            doFieldReferenceMetadata(bean.getClass());
            doMethodReferenceMetadata(bean.getClass());
        }
        return pvs;
    }

    private void doFieldReferenceMetadata(final Class<?> beanClass) {
        ReflectionUtils.doWithFields(beanClass, field -> {
            Reference reference = getAnnotation(field, Reference.class);
            if (reference != null) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    modifyVersion(field, reference);
                }
            }
        });
    }

    /**
     * 检测方法级别有没有被注解过
     * 方法不支持
     *
     * @param beanClass bean
     */
    private void doMethodReferenceMetadata(final Class<?> beanClass) {
        ReflectionUtils.doWithMethods(beanClass, method -> {
            Method bridgedMethod = findBridgedMethod(method);
            if (!isVisibilityBridgeMethodPair(method, bridgedMethod)) {
                return;
            }
            Reference reference = findAnnotation(bridgedMethod, Reference.class);
            if (reference != null && method.equals(ClassUtils.getMostSpecificMethod(method, beanClass))) {
                logger.error("Method Not Support");
                throw new UnsupportedOperationException(method.getName() + " Not Support!");
            }
        });
    }


    /**
     * 修改version
     *
     * @param beanField 被 {@link Reference} 修饰的域
     * @param reference beanField上的 Reference 实例
     */
    private void modifyVersion(Field beanField, Reference reference) {
        Class<?> fieldClass = beanField.getType();
        InvocationHandler handler = Proxy.getInvocationHandler(reference);
        Field handlerField = ReflectionUtils.findField(handler.getClass(), "memberValues");
        ReflectionUtils.makeAccessible(handlerField);
        try {
            String fieldClassName = fieldClass.getName();
            String fetch = support.fetch(fieldClassName);
            logger.info(fieldClassName + ": origin= " + reference.version() + ", newest=" + fetch);
            ((Map) handlerField.get(handler)).put("version", fetch);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE - 1;
    }
}
