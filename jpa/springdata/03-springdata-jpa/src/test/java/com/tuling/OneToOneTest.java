package com.tuling;

import com.tuling.config.SpringDataJPAConfig;
import com.tuling.pojo.Account;
import com.tuling.pojo.Customer;
import com.tuling.repositories.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
@ContextConfiguration(classes = SpringDataJPAConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class OneToOneTest {
    @Autowired
    CustomerRepository repository;

    // 插入
    @Test
    public void testC(){
        // 初始化数据
        Account account = new Account();
        account.setUsername("xushu");

        Customer customer = new Customer();
        customer.setCustName("徐庶");
        customer.setAccount(account);

        account.setCustomer(customer);

        repository.save(customer);
    }


    // 插入
    @Test
    // 为什么懒加载要配置事务 ：
    // 当通过repository调用完查询方法，session就会立即关闭， 一旦session你就不能查询，
    // 加了事务后， 就能让session直到事务方法执行完毕后才会关闭
    @Transactional(readOnly = true)
    public void testR(){
        Optional<Customer> customer = repository.findById(3L);  // 只查询出客户， session关闭
        System.out.println("=================");
        System.out.println(customer.get());  // toString
    }


    @Test
    public void testD(){
        repository.deleteById(1L);
    }



    @Test
    public void testU(){

        Customer customer = new Customer();
        customer.setCustId(16L);
        customer.setCustName("徐庶");
        customer.setAccount(null);
        repository.save(customer);
    }


}
