package com.tuling.springdata.service;

import com.tuling.springdata.pojo.Customer;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
public interface CustomerService {
    Iterable<Customer> getAll();
}
