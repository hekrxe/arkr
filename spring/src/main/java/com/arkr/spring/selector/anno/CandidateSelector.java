package com.arkr.spring.selector.anno;

import com.arkr.spring.selector.candidate.CandidateA;
import com.arkr.spring.selector.candidate.CandidateB;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author hztanhuayou
 * @date 2018/2/10
 */
public class CandidateSelector implements ImportSelector {
    /**
     * @param importingClassMetadata String[] importClassNames = selector.selectImports(currentSourceClass.getMetadata());
     *                               currentSourceClass 就是声明了 EnableCandidate 注解的类实例
     * @return 返回的类将被当作一个配置类(被实例化)
     */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes(EnableCandidate.class.getName()));
        String value = attributes.getString("value");
        System.out.println("value=" + value);
        if ("A".equals(value)) {
            return new String[]{CandidateA.class.getName()};
        }
        return new String[]{CandidateB.class.getName()};
    }
}
