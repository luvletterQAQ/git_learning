package com.tuling.test;

import com.tuling.pojo.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
public class HibernateTest {

    // Session工厂  Session:数据库会话  代码和数据库的一个桥梁
    private SessionFactory sf;

    @Before
    public void init() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("/hibernate.cfg.xml").build();

        //2. 根据服务注册类创建一个元数据资源集，同时构建元数据并生成应用一般唯一的的session工厂

        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    @Test
    public void testC(){
        // session进行持久化操作
        try(Session session = sf.openSession()){
            Transaction tx = session.beginTransaction();

            Customer customer = new Customer();
            customer.setCustName("徐庶");

            session.save(customer);

            tx.commit();
        }

    }


    @Test
    public void testR(){
        // session进行持久化操作
        try(Session session = sf.openSession()){
            Transaction tx = session.beginTransaction();


            Customer customer = session.find(Customer.class, 1L);
            System.out.println("=====================");
            System.out.println(customer);

            tx.commit();
        }

    }


    @Test
    public void testR_lazy(){
        // session进行持久化操作
        try(Session session = sf.openSession()){
            Transaction tx = session.beginTransaction();


            Customer customer = session.load(Customer.class, 1L);
            System.out.println("=====================");
            System.out.println(customer);

            tx.commit();
        }

    }


    @Test
    public void testU(){
        // session进行持久化操作
        try(Session session = sf.openSession()){
            Transaction tx = session.beginTransaction();

            Customer customer = new Customer();
            //customer.setCustId(1L);
            customer.setCustName("徐庶");

            // 插入session.save()
            // 更新session.update();
            session.saveOrUpdate(customer);

            tx.commit();
        }

    }


    @Test
    public void testD(){
        // session进行持久化操作
        try(Session session = sf.openSession()){
            Transaction tx = session.beginTransaction();

            Customer customer = new Customer();
            customer.setCustId(2L);

            session.remove(customer);

            tx.commit();
        }
    }


    @Test
    public void testR_HQL(){
        // session进行持久化操作
        try(Session session = sf.openSession()){
            Transaction tx = session.beginTransaction();

            String hql=" FROM Customer where custId=:id";

            List<Customer> resultList = session.createQuery(hql, Customer.class)
                    .setParameter("id",1L)
                    .getResultList();
            System.out.println(resultList);


            tx.commit();
        }
    }
}
