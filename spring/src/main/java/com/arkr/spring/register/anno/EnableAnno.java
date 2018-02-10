package com.arkr.spring.register.anno;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hztanhuayou
 * @date 2018/2/10
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({RegisterImpl.class})
public @interface EnableAnno {
    String value() default "";
}
