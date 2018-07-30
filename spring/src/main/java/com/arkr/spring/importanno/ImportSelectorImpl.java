package com.arkr.spring.importanno;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * User: tanhuayou
 * Date: 2018/7/30
 */
public class ImportSelectorImpl implements ImportSelector {
    /**
     */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{ImportSelectorBean.class.getName()};
    }

    private static class ImportSelectorBean {
        public ImportSelectorBean() {
            System.out.println("ImportSelectorBean");
        }
    }
}
