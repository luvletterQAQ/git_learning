package com.tuling;

import com.tuling.config.SpringDataJPAConfig;
import com.tuling.pojo.Customer;
import com.tuling.repositories.CustomerRepository;
import com.tuling.repositories.CustomerSpecificationsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
@ContextConfiguration(classes = SpringDataJPAConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SpecificationTest {

    // jdk动态代理的实例
    @Autowired
    CustomerSpecificationsRepository repository;

    @Test
    public  void testR(){

        List<Customer> customer = repository.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                // root from Customer  , 获取列
                // CriteriaBuilder where 设置各种条件  (> < in ..)
                // query  组合（order by , where)



                return null;
            }
        });

        System.out.println(customer);
    }


    /**
     * 查询客户范围 (in)
     * id  >大于
     * 地址  精确
     */
    @Test
    public  void testR2(){

        List<Customer> customer = repository.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                // root from Customer  , 获取列
                // CriteriaBuilder where 设置各种条件  (> < in ..)
                // query  组合（order by , where)
                Path<Object> custId = root.get("custId");
                Path<Object> custName = root.get("custName");
                Path<Object> custAddress = root.get("custAddress");

                // 参数1 ：为哪个字段设置条件   参数2：值
                Predicate predicate = cb.equal(custAddress, "BEIJING");

                return predicate;
            }
        });

        System.out.println(customer);
    }


    /**
     * 查询客户范围 (in)
     * id  >大于
     * 地址  精确
     */
    @Test
    public  void testR3(){

        List<Customer> customer = repository.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                // root from Customer  , 获取列
                // CriteriaBuilder where 设置各种条件  (> < in ..)
                // query  组合（order by , where)
                Path<Long> custId = root.get("custId");
                Path<String> custName = root.get("custName");
                Path<String> custAddress = root.get("custAddress");

                // 参数1 ：为哪个字段设置条件   参数2：值
                Predicate custAddressP = cb.equal(custAddress, "BEIJING");
                Predicate custIdP = cb.greaterThan(custId, 0L);
                CriteriaBuilder.In<String> in = cb.in(custName);
                in.value("徐庶").value("王五");


                Predicate and = cb.and(custAddressP, custIdP,in);



                return and;
            }
        });

        System.out.println(customer);
    }


    /**
     * 查询客户范围 (in)
     * id  >大于
     * 地址  精确
     */
    @Test
    public  void testR4(){

        Customer params=new Customer();
        //params.setCustAddress("BEIJING");
        params.setCustId(0L);
        params.setCustName("徐庶,王五");

        List<Customer> customer = repository.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {


                // root from Customer  , 获取列
                // CriteriaBuilder where 设置各种条件  (> < in ..)
                // query  组合（order by , where)

                // 1. 通过root拿到需要设置条件的字段
                Path<Long> custId = root.get("custId");
                Path<String> custName = root.get("custName");
                Path<String> custAddress = root.get("custAddress");

                // 2. 通过CriteriaBuilder设置不同类型条件
                List<Predicate> list=new ArrayList<>();
                if(!StringUtils.isEmpty(params.getCustAddress())) {
                    // 参数1 ：为哪个字段设置条件   参数2：值
                    list.add(cb.equal(custAddress, "BEIJING")) ;
                }
                if(params.getCustId()>-1){
                    list.add(cb.greaterThan(custId, 0L));
                }

                if(!StringUtils.isEmpty(params.getCustName())) {
                    CriteriaBuilder.In<String> in = cb.in(custName);
                    in.value("徐庶").value("王五");
                    list.add(in);
                }


                // 组合条件
                Predicate and = cb.and(list.toArray(new Predicate[list.size()]));

                return and;
            }
        });

        System.out.println(customer);
    }


    @Test
    public  void testR5(){

        Customer params=new Customer();
        //params.setCustAddress("BEIJING");
        params.setCustId(0L);
        params.setCustName("徐庶,王五");

        List<Customer> customer = repository.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                // root from Customer  , 获取列
                // CriteriaBuilder where 设置各种条件  (> < in ..)
                // query  组合（order by , where)
                Path<Long> custId = root.get("custId");
                Path<String> custName = root.get("custName");
                Path<String> custAddress = root.get("custAddress");

                // 参数1 ：为哪个字段设置条件   参数2：值
                List<Predicate> list=new ArrayList<>();
                if(!StringUtils.isEmpty(params.getCustAddress())) {
                    list.add(cb.equal(custAddress, "BEIJING")) ;
                }
                if(params.getCustId()>-1){
                    list.add(cb.greaterThan(custId, 0L));
                }

                if(!StringUtils.isEmpty(params.getCustName())) {
                    CriteriaBuilder.In<String> in = cb.in(custName);
                    in.value("徐庶").value("王五");
                    list.add(in);
                }


                Predicate and = cb.and(list.toArray(new Predicate[list.size()]));

                Order desc = cb.desc(custId);

                return query.where(and).orderBy(desc).getRestriction();
            }
        });

        System.out.println(customer);
    }
}
