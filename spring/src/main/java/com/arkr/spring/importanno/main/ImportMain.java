package com.arkr.spring.importanno.main;

import com.arkr.spring.importanno.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * User: tanhuayou
 * Date: 2018/7/30
 */
@SpringBootApplication
@ImportRegular
@Import({ImportConfig.class, ImportSelectorImpl.class})
@ImportBeanDefinitionRegistrarAnno(BeanDefinitionBean.class)
public class ImportMain {

    public static void main(String[] args) {
        SpringApplication.run(ImportMain.class, args);
    }
}
