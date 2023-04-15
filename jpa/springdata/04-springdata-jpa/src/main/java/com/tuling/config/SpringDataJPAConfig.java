package com.tuling.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.tuling.repositories.CustomerRepository;
import com.tuling.xushu.MyJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
@Configuration          // 标记当前类为配置类   =xml配文件
@EnableJpaRepositories(basePackages="com.tuling.repositories")  // 启动jpa    <jpa:repositories
@EnableTransactionManagement    // 开启事务
//@ComponentScan("com.tuling")   //<coponent-scan>
public class SpringDataJPAConfig {

    /*
    *  <!--数据源-->
    <bean class="com.alibaba.druid.pool.DruidDataSource" name="dataSource">
        <property name="username" value="root"/>
        <property name="password" value="123456"/>
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306/springdata_jpa?characterEncoding=UTF-8"/>
    </bean>
    * */
    @Bean
    public DataSource dataSource() {

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/springdata_jpa?characterEncoding=UTF-8");

        return  dataSource;

    }

    /*
    *  <!--EntityManagerFactory-->
    <bean name="entityManagerFactory" class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
        <property name="jpaVendorAdapter">
            <!--Hibernate实现-->
            <bean class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter">
                <!--生成数据库表-->
                <property name="generateDdl" value="true"></property>
                <property name="showSql" value="true"></property>
            </bean>
        </property>
        <!--设置实体类的包-->
        <property name="packagesToScan" value="com.tuling.pojo"></property>
        <property name="dataSource" ref="dataSource" ></property>
    </bean>
    * */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        //factory.setPackagesToScan("com.tuling.pojo");
        factory.setDataSource(dataSource());
        factory.setPackagesToScan("com.tuling");
        return factory;
    }

    /*
    * <bean class="org.springframework.orm.jpa.JpaTransactionManager" name="transactionManager">
        <property name="entityManagerFactory" ref="entityManagerFactory"></property>
    </bean>
    * */
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {

        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }



//    @Bean
//    public CustomerRepository customerRepository(
//            LocalContainerEntityManagerFactoryBean entityManagerFactory) throws ClassNotFoundException {
//        // todo: 实例化过程
//
//        //  获得EntityManager
//         EntityManager em = entityManagerFactory.createNativeEntityManager(null);
//
//        //  获得当前接口的pojo类
//        // getGenericInterfaces() 拿到当前接口的父接口 = PagingAndSortingRepository
//        ParameterizedType parameterizedType =(ParameterizedType) CustomerRepository.class.getGenericInterfaces()[0];
//        // 能拿到接口的泛型 <Customer,Long>
//        Type type = parameterizedType.getActualTypeArguments()[0];
//        Class clazz=Class.forName(type.getTypeName());
//
//        CustomerRepository repository = (CustomerRepository) Proxy.newProxyInstance(
//                CustomerRepository.class.getClassLoader(),
//                new Class[]{CustomerRepository.class},
//                new MyJpaRepository(em,clazz)
//        );
//
//        return repository;
//    }
}
