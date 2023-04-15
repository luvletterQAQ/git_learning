package com.tuling;

import com.tuling.config.SpringDataJPAConfig;
import com.tuling.pojo.Customer;
import com.tuling.repositories.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.endsWith;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.startsWith;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
@ContextConfiguration(classes = SpringDataJPAConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class JpqlTest {

    // jdk动态代理的实例
    @Autowired
    CustomerRepository repository;

    @Test
    public  void testR(){
        List<Customer> customer = repository.findCustomerByCustName("李四");
        System.out.println(customer);
    }

    @Test
    public  void testU(){
        int result = repository.updateCustomer("王五", 7L);
        System.out.println(result);
    }

    @Test
    public  void testD(){
        int result = repository.deleteCustomer(10L);
        System.out.println(result);
    }

    @Test
    public  void testC(){
        int result = repository.insertCustomerBySelect(7L);
        System.out.println(result);
    }

    @Test
    public  void testR_sql(){
        List<Customer> customer = repository.findCustomerByCustNameBySql("徐庶");
        System.out.println(customer);

    }


}
