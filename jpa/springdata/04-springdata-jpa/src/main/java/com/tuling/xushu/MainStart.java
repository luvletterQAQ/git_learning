package com.tuling.xushu;

import com.tuling.config.SpringDataJPAConfig;
import com.tuling.pojo.Customer;
import com.tuling.repositories.CustomerRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Optional;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
public class MainStart {

    public static void main(String[] args) throws ClassNotFoundException {
        // spring上下文   spring容器   --->  ioc加载过程：创建所有的bean  包括Repository的Bean
        AnnotationConfigApplicationContext ioc = new AnnotationConfigApplicationContext(SpringDataJPAConfig.class);

//        CustomerRepository repository = ioc.getBean(CustomerRepository.class);
//
//        System.out.println(repository.getClass());   //jdk动态
//        Optional<Customer> byId = repository.findById(20L);
//
//        System.out.println(byId.get());


        //  获得EntityManager
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = ioc.getBean(LocalContainerEntityManagerFactoryBean.class);
        EntityManager em = entityManagerFactoryBean.createNativeEntityManager(null);

        //  获得当前接口的pojo类
        // getGenericInterfaces() 拿到当前接口的父接口 = PagingAndSortingRepository
        ParameterizedType parameterizedType =(ParameterizedType) CustomerRepository.class.getGenericInterfaces()[0];
        // 能拿到接口的泛型 <Customer,Long>
        Type type = parameterizedType.getActualTypeArguments()[0];
        Class clazz=Class.forName(type.getTypeName());

        CustomerRepository repository = (CustomerRepository) Proxy.newProxyInstance(
                CustomerRepository.class.getClassLoader(),
                new Class[]{CustomerRepository.class},
                new MyJpaRepository(em,clazz)
        );

        System.out.println(repository.findById(20L));

    }
}
