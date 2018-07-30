package com.arkr.spring.importanno;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader#registerBeanDefinitionForImportedConfigurationClass(org.springframework.context.annotation.ConfigurationClass)
 * ConfigurationClassBeanDefinitionReader
 * 导入的普通类将被实例化后注入到IOC
 * User: tanhuayou
 * Date: 2018/7/30
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RegularComponent.class)
public @interface ImportRegular {
}
