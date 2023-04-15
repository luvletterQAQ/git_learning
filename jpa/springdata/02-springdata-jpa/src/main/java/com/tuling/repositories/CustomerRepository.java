package com.tuling.repositories;

import com.tuling.pojo.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
public interface CustomerRepository extends PagingAndSortingRepository<Customer,Long>{

    // 增删查改

    // 查询
    @Query("FROM Customer where custName=:custName ")
    List<Customer> findCustomerByCustName(@Param("custName") String custName);

    // 修改
    @Transactional
    @Modifying   // 通知springdatajpa 是增删改的操作
    @Query("UPDATE Customer c set c.custName=:custName where c.custId=:id")
    int updateCustomer(@Param("custName") String custName,@Param("id")Long id);


    @Transactional
    @Modifying   // 通知springdatajpa 是增删改的操作
    @Query("DELETE FROM Customer c where c.custId=?1")
    int deleteCustomer(Long id);

    // 新增  JPQL
    @Transactional
    @Modifying   // 通知springdatajpa 是增删改的操作
    @Query("INSERT INTO Customer (custName) SELECT c.custName FROM Customer c where c.custId=?1")
    int insertCustomerBySelect(Long id);


    @Query(value="select * FROM tb_customer where cust_name=:custName "
    ,nativeQuery = true)
    List<Customer> findCustomerByCustNameBySql(@Param("custName") String custName);

}
