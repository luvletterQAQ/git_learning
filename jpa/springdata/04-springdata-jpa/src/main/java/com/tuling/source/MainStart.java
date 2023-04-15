package com.tuling.source;

import com.tuling.config.SpringDataJPAConfig;
import com.tuling.pojo.Customer;
import com.tuling.repositories.CustomerRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.repository.Repository;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

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

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext ioc = new AnnotationConfigApplicationContext(SpringDataJPAConfig.class);
        //CustomerRepository repository = ioc.getBean(CustomerRepository.class);

        //Optional<Customer> byId = repository.findById(20L);

        //System.out.println(byId.get());

       LocalContainerEntityManagerFactoryBean emFactory = ioc.getBean(LocalContainerEntityManagerFactoryBean.class);
        EntityManager em = emFactory.createNativeEntityManager(null);

        System.out.println(em);
        Class pojoClass = getPojoClass(CustomerRepository.class);
        CustomerRepository repository =(CustomerRepository) Proxy.newProxyInstance(
                CustomerRepository.class.getClassLoader(),
                new Class[]{CustomerRepository.class},
                new MyJdkDynamicProxy(new MySimpleJpaRepository(em, pojoClass))
        );

        System.out.println(repository.findById(20L));


    }

    // 根据Class获得第一个接口的第一个泛型
    public static Class getPojoClass(Class<? extends Repository> repository) throws Exception {
        System.out.println(repository.getGenericInterfaces()[0]);
        ParameterizedType parameterizedType = (ParameterizedType) CustomerRepository.class.getGenericInterfaces()[0];
        Type actualTypeArgument = parameterizedType.getActualTypeArguments()[0];
        return Class.forName(actualTypeArgument.getTypeName());
    }
}
