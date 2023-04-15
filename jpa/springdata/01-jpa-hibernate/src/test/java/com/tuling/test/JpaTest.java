package com.tuling.test;

import com.tuling.pojo.Customer;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
public class JpaTest {
    EntityManagerFactory factory;

    @Before
    public void before(){
         factory= Persistence.createEntityManagerFactory("hibernateJPA");
    }

    @Test
    public void testC(){
        EntityManager em = factory.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Customer customer = new Customer();
        customer.setCustName("张三");

        em.persist(customer);

        tx.commit();
    }

    // 立即查询
    @Test
    public void testR(){
        EntityManager em = factory.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();


        Customer customer = em.find(Customer.class, 1L);
        System.out.println("========================");
        System.out.println(customer);

        tx.commit();
    }


    // 延迟查询
    @Test
    public void testR_lazy(){
        EntityManager em = factory.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();


        Customer customer = em.getReference(Customer.class, 1L);
        System.out.println("========================");
        System.out.println(customer);

        tx.commit();
    }


    @Test
    public void testU(){
        EntityManager em = factory.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Customer customer = new Customer();
        customer.setCustId(5L);
        customer.setCustName("王五");

        /*
        *
        // 如果指定了主键：
                  更新： 1.先查询  看是否有变化
                 如果有变化 更新     如果没有变化就不更新
        *   如果没有指定了主键：
        *          插入
        * */
        em.merge(customer);

        tx.commit();
    }

    @Test
    public void testU_JPQL(){
        EntityManager em = factory.createEntityManager();



        EntityTransaction tx = em.getTransaction();
        tx.begin();

        String jpql="UPDATE Customer set custName=:name where custId=:id";
        em.createQuery(jpql)
                .setParameter("name","李四")
                .setParameter("id",5L)
                        .executeUpdate();

        tx.commit();
    }


    @Test
    public void testU_SQL(){
        EntityManager em = factory.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        String sql="UPDATE tb_customer set cust_name=:name where id=:id";
        em.createNativeQuery(sql)
                .setParameter("name","王五")
                .setParameter("id",5L)
                .executeUpdate();

        tx.commit();
    }


    @Test
    public void testD(){
        EntityManager em = factory.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Customer customer = em.find(Customer.class,5L);


        em.remove(customer);

        tx.commit();
    }
}
