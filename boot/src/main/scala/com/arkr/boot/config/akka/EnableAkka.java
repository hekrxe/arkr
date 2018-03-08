package com.arkr.boot.config.akka;

import com.arkr.boot.config.akka.akkaext.AkkaSystemRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User: tanhuayou
 * Date: 2018/3/8
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({WordCountExample.class, AkkaSystemRegistrar.class})
public @interface EnableAkka {
    String akkaSystemName() default "";

    /**
     * 要不要运行示例
     */
    boolean run() default true;
}
