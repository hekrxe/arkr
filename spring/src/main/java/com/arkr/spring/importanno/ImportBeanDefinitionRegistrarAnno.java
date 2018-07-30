package com.arkr.spring.importanno;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * User: tanhuayou
 * Date: 2018/7/30
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ImportBeanDefinitionRegistrarImpl.class)
public @interface ImportBeanDefinitionRegistrarAnno {
    Class<?> value();
}
