package com.arkr.spring.register.anno;

import com.arkr.spring.register.AnnoA;
import com.arkr.spring.register.AnnoB;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import static org.springframework.beans.factory.support.BeanDefinitionBuilder.rootBeanDefinition;

/**
 * @author hztanhuayou
 * @date 2018/2/10
 */
public class RegisterImpl implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes(EnableAnno.class.getName()));
        String value = attributes.getString("value");
        System.out.println(value);
        if ("all".equals(value)) {
            BeanDefinitionBuilder builder = rootBeanDefinition(AnnoA.class);
            AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
            registry.registerBeanDefinition("annoA", beanDefinition);

            builder = rootBeanDefinition(AnnoB.class);
            registry.registerBeanDefinition("annoB", builder.getBeanDefinition());
        }

    }
}
