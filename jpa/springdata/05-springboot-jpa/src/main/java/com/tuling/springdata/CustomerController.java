package com.tuling.springdata;

import com.tuling.springdata.pojo.Customer;
import com.tuling.springdata.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @RequestMapping("/customer/all")
    public Iterable<Customer> getAll(){
        return customerService.getAll();
    }
}
