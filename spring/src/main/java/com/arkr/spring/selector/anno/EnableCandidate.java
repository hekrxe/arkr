package com.arkr.spring.selector.anno;

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
@Import(CandidateSelector.class)
public @interface EnableCandidate {
    String value() default "";
}
