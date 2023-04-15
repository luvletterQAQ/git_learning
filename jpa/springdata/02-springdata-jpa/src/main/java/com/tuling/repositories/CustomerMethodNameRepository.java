package com.tuling.repositories;

import com.tuling.pojo.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
public interface CustomerMethodNameRepository extends PagingAndSortingRepository<Customer,Long> {

     List<Customer> findByCustName(String custName);


     boolean existsByCustName(String custName);


     @Transactional
     @Modifying
     int deleteByCustId(Long custName);



     List<Customer> findByCustNameLike(String custName);

}
