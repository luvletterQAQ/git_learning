package com.tuling.springdata.service;

import com.tuling.springdata.pojo.Customer;
import com.tuling.springdata.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
@Service
public class CustomerServiceImple implements  CustomerService{

    @Autowired
    CustomerRepository repository;

    @Override
    public Iterable<Customer> getAll() {
        return repository.findAll();
    }
}
