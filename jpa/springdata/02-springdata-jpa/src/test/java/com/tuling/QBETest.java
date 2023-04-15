package com.tuling;

import com.tuling.config.SpringDataJPAConfig;
import com.tuling.pojo.Customer;
import com.tuling.repositories.CustomerQBERepository;
import com.tuling.repositories.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
@ContextConfiguration(classes = SpringDataJPAConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class QBETest {

    // jdk动态代理的实例
    @Autowired
    CustomerQBERepository repository;

    /**
     * 简单实例  客户名称  客户地址动态查询
     */
    @Test
    public  void test01(){

        // 查询条件
        Customer customer=new Customer();
        customer.setCustName("徐庶");
        customer.setCustAddress("BEIJING");

        // 通过Example构建查询条件
        Example<Customer> example = Example.of(customer);

        List<Customer> list = (List<Customer>) repository.findAll(example);
        System.out.println(list);
    }


    /**
     *  通过匹配器 进行条件的限制
     * 简单实例  客户名称  客户地址动态查询
     */
    @Test
    public  void test02(){

        // 查询条件
        Customer customer=new Customer();
        customer.setCustName("庶");
        customer.setCustAddress("JING");

        // 通过匹配器 对条件行为进行设置
        ExampleMatcher matcher = ExampleMatcher.matching()
                //.withIgnorePaths("custName")       // 设置忽略的属性
                //.withIgnoreCase("custAddress")      // 设置忽略大小写
                //.withStringMatcher(ExampleMatcher.StringMatcher.ENDING);    // 对所有条件字符串进行了结尾匹配
                .withMatcher("custAddress",m -> m.endsWith().ignoreCase());      // 针对单个条件进行限制, 会使withIgnoreCase失效，需要单独设置
                //.withMatcher("custAddress", ExampleMatcher.GenericPropertyMatchers.endsWith().ignoreCase());

        // 通过Example构建查询条件
        Example<Customer> example = Example.of(customer,matcher);

        List<Customer> list = (List<Customer>) repository.findAll(example);
        System.out.println(list);
    }
}
