package com.tuling;

import com.tuling.config.SpringDataJPAConfig;
import com.tuling.pojo.Customer;
import com.tuling.repositories.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
@ContextConfiguration(classes = SpringDataJPAConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringDataJpaPagingAndSortTest
{
    // jdk动态代理的实例
    @Autowired
    CustomerRepository repository;

    @Test
    public  void testPaging(){
        Page<Customer> all = repository.findAll(PageRequest.of(0, 2));
        System.out.println(all.getTotalPages());
        System.out.println(all.getTotalElements());
        System.out.println(all.getContent());

    }

    @Test
    public  void testSort(){

        Sort sort = Sort.by("custId").descending();

        Iterable<Customer> all = repository.findAll(sort);

        System.out.println(all);

    }


    @Test
    public  void testSortTypeSafe(){

        Sort.TypedSort<Customer> sortType = Sort.sort(Customer.class);

        Sort sort = sortType.by(Customer::getCustId).descending();


        Iterable<Customer> all = repository.findAll(sort);

        System.out.println(all);

    }
}
