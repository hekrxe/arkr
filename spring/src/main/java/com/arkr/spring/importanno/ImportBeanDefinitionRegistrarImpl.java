package com.arkr.spring.importanno;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * User: tanhuayou
 * Date: 2018/7/30
 */
public class ImportBeanDefinitionRegistrarImpl implements ImportBeanDefinitionRegistrar {
    /**
     * Register bean definitions as necessary based on the given annotation metadata of
     * the importing {@code @Configuration} class.
     * registered here, due to lifecycle constraints related to {@code @Configuration}
     * class processing.
     *
     * @param importingClassMetadata annotation metadata of the importing class
     * @param registry               current bean definition registry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes fromMap = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(ImportBeanDefinitionRegistrarAnno.class.getName()));
        Class<?> clazz = fromMap.getClass("value");
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(clazz);
        builder.setRole(BeanDefinition.ROLE_APPLICATION);

        registry.registerBeanDefinition(clazz.getSimpleName(), builder.getBeanDefinition());
        // or
//        BeanDefinitionReaderUtils.registerWithGeneratedName(builder.getBeanDefinition(), registry);
    }
}
