package com.tuling;

import com.tuling.config.SpringDataJPAConfig;
import com.tuling.pojo.Customer;
import com.tuling.pojo.Message;
import com.tuling.repositories.CustomerRepository;
import com.tuling.repositories.MessageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ManyToOneTest {
    @Autowired
    MessageRepository repository;

    // 多对一 插入
    // 得出： 当插入"多"的数据的时候，使用多对一的关联关系是更加合理
    @Test
    public void testC(){
        // 一
        Customer customer = new Customer();
        customer.setCustName("司马懿");

        // 多
        List<Message> list=new ArrayList<>();
        list.add(new Message("你好",customer));
        list.add(new Message("在吗？",customer));


        repository.saveAll(list);
    }


    // 多对一：根据客户id查询对应的所有信息
    // 通过“一”进行条件查询， 在一对多中实现是更合理的
    @Test
    public void testR(){
        Customer customer = new Customer();
        customer.setCustId(1L);
        customer.setCustName("xxx");

        List<Message> messages = repository.findByCustomer(customer);
        // 隐式调用toString()
        System.out.println(messages);
    }


    @Test
    public void testD(){
        Customer customer = new Customer();
        customer.setCustId(1L);

        List<Message> messages = repository.findByCustomer(customer);


        repository.deleteAll(messages);
    }
}
