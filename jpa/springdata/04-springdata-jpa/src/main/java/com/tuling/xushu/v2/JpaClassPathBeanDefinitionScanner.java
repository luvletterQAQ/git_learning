package com.tuling.xushu.v2;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;

import java.io.IOException;
import java.util.Set;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 *
 *
 * 自定义的扫描器
 *
 * 原有的不变，
 *
 * 自己通过这个扫描器进行动态注册
 */
public class JpaClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

    public JpaClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    // 如果它是一个接口就视为有效组件 , 必须实现Repository
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        AnnotationMetadata metadata = beanDefinition.getMetadata();
        return  metadata.isInterface();
    }


    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);

        // com.tuling.repositories.CustomerRepository
        // com.tuling.repositories.XXXRepository
        for (BeanDefinitionHolder definitionHolder : beanDefinitionHolders) {



            ScannedGenericBeanDefinition beanDefinition = (ScannedGenericBeanDefinition)definitionHolder.getBeanDefinition();

            String beanClass = beanDefinition.getBeanClassName();

            // 设置构造函数的值
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(beanClass);

            // 偷天换日  mybatis
            beanDefinition.setBeanClass(JpaFacotryBean.class);


        }

        return beanDefinitionHolders;
    }
}
