package com.tuling;

import com.tuling.config.SpringDataJPAConfig;
import com.tuling.pojo.Account;
import com.tuling.pojo.Customer;
import com.tuling.pojo.Message;
import com.tuling.repositories.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
@ContextConfiguration(classes = SpringDataJPAConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class OneToManyTest {
    @Autowired
    CustomerRepository repository;

    // 插入
    @Test
    public void testC(){

        List<Message> messageList=new ArrayList<>();
        messageList.add(new Message("您好"));
        messageList.add(new Message("在吗?"));

        Customer customer = new Customer();
        customer.setCustName("徐庶帅哥");
        customer.setMessages(messageList);

        repository.save(customer);

    }


    // 插入
    @Test
    @Transactional(readOnly = true)
    public void testR(){
        // 懒加载过程：
        // 1.findById  只会查询Customer 和其他关联的立即加载
        Optional<Customer> customer = repository.findById(1L);
        System.out.println("=====================");
        // 由于输出， 会自动调用customer.toString()
        System.out.println(customer);


    }


    // 插入
    @Test
    public void testD(){

         repository.deleteById(2L);

    }



    @Test
    @Transactional
    @Commit
    public void testU(){

        Optional<Customer> customer = repository.findById(19L);
        customer.get().setCustName("xxx");

    }
}
