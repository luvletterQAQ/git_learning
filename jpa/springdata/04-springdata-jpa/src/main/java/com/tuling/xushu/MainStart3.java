package com.tuling.xushu;

import com.tuling.config.SpringDataJPAConfig;
import com.tuling.pojo.Customer;
import com.tuling.repositories.CustomerRepository;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import java.util.Optional;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
public class MainStart3 {

    public static void main(String[] args) throws ClassNotFoundException {
        // spring上下文   spring容器   --->  ioc加载过程：创建所有的bean  包括Repository的Bean
        AnnotationConfigApplicationContext ioc = new AnnotationConfigApplicationContext(SpringDataJPAConfig.class);

        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) ioc.getBeanFactory();
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanFactory);
        scanner.scan("com.tuling");


        for (String beanDefinitionName : ioc.getBeanDefinitionNames()) {

            System.out.println(beanDefinitionName);
        }

    }
}
