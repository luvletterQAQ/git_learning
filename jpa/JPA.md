# JPA

https://www.bilibili.com/video/BV13Y411x7n9?p=9&vd_source=37d44c19b90722aba78120f5ce91449b

## 一、JPA的介绍及JDBC的关系

### JPA

JPA（Java Persistence API）用于对象持久化的 API，是 Java EE 5.0 平台标准的 ORM 规范，使得应用程序以统一的方式访问持久层。不同数据库的SQL语句是不同的，JPA将这些步骤封装成接口，程序员只需要调用接口而不用写SQL语句。而JDBC需要写SQL语句，如果数据库改变了，SQL语句也要变化。

### JDBC

JDBC 也是一种规范和接口，不过 JDBC 是面向 SQL 的，使用起来比较繁琐。所以就有了 ORM 框架，建立了 Java 对象与数据库表之间的映射关系，可以通过直接操作对象来实现持久化，简化了操作的繁杂度。而 JPA 就是 ORM 框架的规范，值得一提的是 Hibernate 是符合 JPA 规范的，而 MyBatis 却不符合，因为 MyBatis 还是需要写 SQL 的。Spring data jpa 也符合jpa规划。                                                           

JDBC是我们最熟悉的用来操作数据库的技术，但是随之而来带来了一些问题:                                                                                                            1.需要面向SQL语句来操作数据库，开发人员学习成本更高。 																													 2.数据库的移至性不高，不同数据库的SQL语句无法通用。																														  3. java对象和数据库类型的映射是个麻烦事。

### 区别

相同点：都是一组规范接口。                                                                                                                                                                        不同点：                                                                                                                                                                                                         1.JDBC是由各个关系型数据库(数据库厂商)实现的，JPA是由ORM框架(如Hibernate)实现。
2.JDBC使用SQL语句和数据库通信。JPA用面向对象方式，通过ORM框架来生成SQL，进行操作。                                                                     3.JPA在JDBC之上的，JPA也要依赖JDBC才能操作数据库。

JDBC 示意图：

![image-20230317201234516](D:\Program Files (x86)\王岩\Typora\imgs\image-20230317201234516.png)

JPA示意图 :

![image-20230317201251065](D:\Program Files (x86)\王岩\Typora\imgs\image-20230317201251065.png)

## 二、JPA、Hibernate、Mybatis的关系

### JPA本质

JPA仅仅是一种规范。也就是说JPA==仅仅定义了一些接口==，而需要ORM框架去实现才能工作。

该规范为我们提供了∶ 

1. ORM映射元数据:JPA支持XML和注解两种元数据的形式，元数据描述对象和表之间的映射关系，框架据此将实体对象持久化到数据库表中;如: ==@Entity== ==@Table== ==@Id== 与==@column== 等注解。
2. JPA的API:用来操作实体对象，执行CRUD操作，框架在后台替我们完成所有的事情，开发者从繁琐的JDBC和SQL代码中解脱出来。如: entityManager.merge(T t);
3. JPQL查询语言:通过面向对象而非面向数据库的查询语言查询数据，避免程序的SQL语句紧密耦合。如: from student s where s.name = ?

### Hibernate与JPA

JPA底层需要某种实现，而Hibernate就是实现了JPA接口的ORM框架。也就是说JPA是一套ORM规范，Hibernate实现了JPA规范!                  SpringBoot默认的JPA实现就是Hibernate，只要引入了spring-data-jpa，就用Hibernate作为底层实现。

![image-20230317203711064](D:\Program Files (x86)\王岩\Typora\imgs\image-20230317203711064.png)

### Mybatis和Hibernate

mybatis:小巧、方便?、高效、简单、直接、半自动																																		半自动的ORM框架
小巧: mybatis就是jdbc封装																																											在国内更流行
场景:在业务比较复杂系统进行使用

hibernate:强大、方便、高效、复杂、绕弯子、全自动																																	全自动的ORM框架
强大:根据ORM映射生成不同SQL																																									在国外更流行
场景:在业务相对简单的系统进行使用，随着微服务的流行

## 三、Hibernate&JPA快速搭建

### maven依赖

>```xml
><dependency>
><groupId>mysql</groupId>
><artifactId>mysql-connector-java</artifactId>
><version>5.1.22</version>
></dependency>
><dependency>
><groupId>org.hibernate</groupId>
><artifactId>hibernate-entitymanager</artifactId>
><version>5.4.32.Final</version>
></dependency>
>```

### JPA对应的依赖

![image-20230318111334024](D:\Program Files (x86)\王岩\Typora\imgs\image-20230318111334024.png)

### 实体类

```java
@Entity     // 作为hibernate 实体类
@Table(name = "tb_customer")       // 映射的表名
public class Customer {

    /**
     * @Id：声明主键的配置
     * @GeneratedValue:配置主键的生成策略
     *      strategy
     *          GenerationType.IDENTITY ：自增，mysql
     *                 * 底层数据库必须支持自动增长（底层数据库支持的自动增长方式，对id自增）
     *          GenerationType.SEQUENCE : 序列，oracle
     *                  * 底层数据库必须支持序列
     *          GenerationType.TABLE : jpa提供的一种机制，通过一张数据库表的形式帮助我们完成主键自增
     *          GenerationType.AUTO ： 由程序自动的帮助我们选择主键生成策略
     * @Column:配置属性和字段的映射关系
     *      name：数据库表中字段的名称
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long custId; //客户的主键

    @Column(name = "cust_name")
    private String custName;//客户名称

    @Column(name="cust_address")
    private String custAddress;//客户地址
}
```

### hibernate配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
    <session-factory>
        <!-- 配置数据库连接信息 -->
        <property name="connection.driver_class">com.mysql.jdbc.Driver</property>
        <property name="connection.url">jdbc:mysql://localhost:3306/springdata_jpa?characterEncoding=UTF-8</property>
        <property name="connection.username">root</property>
        <property name="connection.password">010802</property>

        <!-- 会在日志中记录sql 默认false-->
        <property name="show_sql">true</property>
        <!--是否格式化sql 默认false-->
        <property name="format_sql">true</property>
        <!--表生成策略
            默认none   不自动生成
            update    如果没有表会创建，有会检查更新
            create    创建-->
        <property name="hbm2ddl.auto">update</property>
        <!-- 配置方言：选择数据库类型 -->
        <property name="dialect">org.hibernate.dialect.MySQL57InnoDBDialect</property>

        <!--指定哪些pojo 需要进行ORM映射-->
        <mapping class="com.hfut.pojo.Customer"></mapping>
    </session-factory>
</hibernate-configuration>
```

## 四、基于Hibernate数据库持久化操作

```java 
public class HibernateTest {
    private SessionFactory sf;

    @Before
    public void init() {
        StandardServiceRegistry build =
                new StandardServiceRegistryBuilder().configure("/hibernate.cfg.xml").build();
        sf = new MetadataSources(build).buildMetadata().buildSessionFactory();
    }
    @Test
    public void testC() {
         // 创建Session
         Session sess = sf.openSession();
         // 开始事务
         Transaction tx = sess.beginTransaction();
         // 创建消息实例
         Customer customer = new Customer();
         customer.setCustName("张三");
         // 保存消息
         sess.save(customer);
         // 提交事务
         tx.commit();
         // 关闭Session
         sess.close();
         sf.close();
    }
    
    @Test //通过HQL方式操作
    public void testR_HQL_single() {
        // 创建Session
        Session sess = sf.openSession();
        // 开始事务
        Transaction tx = sess.beginTransaction();
    	//这里的Customer是类名而不是表名  id是类的属性而不是表的字段  全程用代码操作不涉及表
        Customer customer = sess.createQuery("SELECT c FROM Customer c where c.custId=:id", Customer.class)
                    .setParameter("id", 1L)
                        .getSingleResult();
    
        System.out.println(customer);
        // 提交事务
        tx.commit();
        // 关闭Session
        sess.close();
        sf.close();
    }
}
```

如果单单使用Hibernate的API和Spring整合，那就是SSH，比较老旧的框架组合。springbootData使用jpa来封装。

## 五、基于JPA数据库持久化操作

### 添加META-INF\persistence.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence xmlns="http://java.sun.com/xml/ns/persistence" version="2.0">
    <!--需要配置persistence‐unit节点  每个节点就是一个JPA的实现(ORM框架)
        持久化单元：
        name：持久化单元名称
        transaction‐type：事务管理的方式
        JTA：分布式事务管理
        RESOURCE_LOCAL：本地事务管理
    -->
     <persistence-unit name="hibernateJPA" transaction-type="RESOURCE_LOCAL">
         <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
         
         <class>com.hfut.pojo.Customer</class>
         <!--jpa的实现方式 -->

         <!--可选配置：配置jpa实现方的配置信息-->
         <properties>
             <!-- 数据库信息
                 用户名，javax.persistence.jdbc.user
                 密码， javax.persistence.jdbc.password
                 驱动， javax.persistence.jdbc.driver
                 数据库地址 javax.persistence.jdbc.url
             -->
             <property name="javax.persistence.jdbc.user" value="root"/>
             <property name="javax.persistence.jdbc.password" value="123456"/>
             <property name="javax.persistence.jdbc.driver" value="com.mysql.jdbc.Driver"/>
             <property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/springdata_jpa?characterEncoding=UTF-8"/>

             <!--配置jpa实现方(hibernate)的配置信息
                 显示sql ： false|true
                 自动创建数据库表 ： hibernate.hbm2ddl.auto
                 create : 程序运行时创建数据库表（如果有表，先删除表再创建）
                 update ：程序运行时创建表（如果有表，不会创建表）
                 none ：不会创建表
             -->
             <property name="hibernate.show_sql" value="true" />
             <property name="hibernate.hbm2ddl.auto" value="update" />
             <property name="hibernate.dialect" value="org.hibernate.dialect.MySQL57InnoDBDialect" />
         </properties>
     </persistence-unit>
</persistence>
```

### 测试

```java
public class JPATest {
    private EntityManagerFactory factory;//SessionFactory
    EntityManager em;//Session
    @Before
    public void init(){
        //1.加载配置文件，创建entityManagerFactory new Configuration.buildSessionFactory();
        factory = Persistence.createEntityManagerFactory("hibernateJPA");
        // 2. 获取entityManager对象 sessionFactory.openSession();
        em = factory.createEntityManager();
    }
    
   	//增
     @Test
     public void testW() {
         EntityTransaction tx = em.getTransaction();
         tx.begin();

         Customer c  = new Customer(null, "陈明", "辽宁省");
         em.persist(c);//插入时由于指定了ID自增 所以id设置为null， 如果id不为null 则报错
		//插入托管状态的对象什么都不会做	
         tx.commit();
         em.close();
     }
    
    //改
    @Test
     public void testW() {
         EntityTransaction tx = em.getTransaction();
         tx.begin();
		/*
         	如果指定了主键：
                 先查询  看是否有变化
                 如果有变化 更新     如果没有变化就不更新
           	如果没有指定主键：
                  插入
        */
         Customer c  = new Customer(1L, "王岩", "山西省");
         em.merge(c);

         tx.commit();
         em.close();
     }
     /**
      * 查询全部
      * jqpl：from cn.itcast.domain.Customer
      * sql：SELECT * FROM cst_customer
      */
     @Test
     public void testR_JPQL() {
         //2.开启事务
         EntityTransaction tx = em.getTransaction();
         tx.begin();
         //3.查询全部
         String jpql = "select c from Customer c";
         Query query = em.createQuery(jpql);//创建Query查询对象，query对象才是执行jqpl的对象

         //发送查询，并封装结果集
         List list = query.getResultList();

         for (Object obj : list) {
           System.out.print(obj);
         }

         //4.提交事务
         tx.commit();
         //5.释放资源
         em.close();
     }

    // 立即查询
    @Test
    public void testR(){
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Customer customer = em.find(Customer.class, 1L);
        System.out.println("========================");
        System.out.println(customer);

        tx.commit();
        em.close();
    }
    
    // 延迟查询
     @Test 
     public void testR() {
         //2.开启事务
         EntityTransaction tx = em.getTransaction();
         tx.begin();
		//延迟加载
         Customer customer = em.getReference(Customer.class, 1L);
         System.out.println("===========================");
         System.out.println(customer);
         //4.提交事务
         tx.commit();
         //5.释放资源
         em.close();
     }
    //refresh
    @Test
     public void testW() {
         EntityTransaction tx = em.getTransaction();
         tx.begin();
		//虽然托管状态的set方法会修改数据库，但这个修改是在事务提交时才修改的，因此如果修改了对象的值，在事务提交之前可以
         //通过refresh将对象更新成和数据库一样
         Customer customer = em.find(Customer.class, 1L);
         System.out.println(customer);//山西省
         customer.setCustAddress("日本省");
         System.out.println(customer);//日本省
         em.refresh(customer);//只有托管状态的实体对象才能refresh
         System.out.println(customer);//山西省

         tx.commit();
         em.close();
     }
}
```

## 六、JPA的四种状态和缓存

![image-20230320155003343](D:\Program Files (x86)\王岩\Typora\imgs\image-20230320155003343.png)

### 不同状态下的实体，各个方法操作结果会有不同(基于Hibernate)：

1. **persist** 

- ==新建状态==：实体状态迁移到托管状态，如果是自增主键则该对象不能指定主键值，否则会报错
- 托管状态：实体状态不发生改变，但会执行数据库的insert操作（不会有任何影响）
- 游离状态：方法的调用将会抛出异常信息
- ==删除状态==：实体将重返托管状态，persist时对象可以指定主键值，相当于没有删除(啥都没干，主键值都没变，相当于回滚)。

2. **merge** 操作(主要作用是将用户对一个detached状态实体的修改进行归档，归档后将产生一个新的managed状态对象)

- ==新建状态==：没有指定主键值则插入。指定了主键值则先查找，如果有变化则更新，没变化啥都不做。返回一个托管态对象，原来的对象不是托管态(而是新建态)。
- 托管状态：实体状态不发生改变
- ==游离状态==：系统将实体的修改保存到数据库，同时返会一个托管状态的实体
- 删除状态：方法调用将抛出异常

3. **refresh** 操作

- 新建状态：会抛出异常Entity not managed
- ==托管状态==：它的属性将会和数据库中的数据同步
- 游离状态：方法调用将抛出异常Entity not managed
- 删除状态：方法调用将抛出异常Entity not managed

4. **remove** 操作

- 新建状态：方法调用将抛出异常,无意义
- ==托管状态==：实体状态变成删除状态
- 游离状态：方法调用将抛出异常
- 删除状态：不发生任何操作

### 缓存     JPA的缓存是由ORM框架实现的

一级缓存：同一个EntityManager对象共享的缓存。

二级缓存：不同的EntityManager会共享。不常用。

## 七、SpringDataJpa

spirng data jpa是spring提供的一套==简化JPA开发的框架==，按照约定好的规则进行【方法命名】去写dao层接口，就可以

在不写接口实现的情况下，实现对数据库的访问和操作。同时提供了很多除了CRUD之外的功能，如分页、排序、复杂查

询等等。

![image-20230320171511879](D:\Program Files (x86)\王岩\Typora\imgs\image-20230320171511879.png)

SpringDataJPA通过动态代理实现了DAO层的接口。

## 八、SpringData搭建XML的方式

 ### maven依赖

```xml
<!--父maven-->	
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.data</groupId>
            <artifactId>spring-data-bom</artifactId>
            <version>2020.0.14</version>
            <scope>import</scope>
            <type>pom</type>
        </dependency>
    </dependencies>
</dependencyManagement>
<!--子maven-->
<dependencies>
    <dependency>
        <groupId>org.springframework.data</groupId>
        <artifactId>spring-data-jpa</artifactId>
    </dependency>

    <!-- junit4 -->
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.13</version>
        <scope>test</scope>
    </dependency>
    <!-- hibernate对jpa的支持包 -->
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-entitymanager</artifactId>
        <version>5.4.32.Final</version>
    </dependency>

    <!-- Mysql and MariaDB -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>5.1.22</version>
    </dependency>

    <!--连接池-->
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid</artifactId>
        <version>1.2.8</version>
    </dependency>

    <!--spring-test -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-test</artifactId>
        <version>5.3.10</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

### spring配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:jpa="http://www.springframework.org/schema/data/jpa" xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
    https://www.springframework.org/schema/beans/spring-beans.xsd
    http://www.springframework.org/schema/data/jpa
    https://www.springframework.org/schema/data/jpa/spring-jpa.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd">

    <!--用于整合jpa  @EnableJpaRepositories -->
    <jpa:repositories base-package="com.hfut.repositories"
                      entity-manager-factory-ref="entityManagerFactory"
                      transaction-manager-ref="transactionManager"
    />


    <!--EntityManagerFactory-->
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
        <property name="packagesToScan" value="com.hfut.pojo"></property>
        <property name="dataSource" ref="dataSource" ></property>
    </bean>

    <!--数据源-->
    <bean class="com.alibaba.druid.pool.DruidDataSource" name="dataSource">
        <property name="username" value="root"/>
        <property name="password" value="010802"/>
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306/springdata_jpa?characterEncoding=UTF-8"/>
    </bean>

    <!--声明式事务-->
    <bean class="org.springframework.orm.jpa.JpaTransactionManager" name="transactionManager">
        <property name="entityManagerFactory" ref="entityManagerFactory"/>
    </bean>

    <!--启动注解方式的声明式事务-->
    <!--基于注解方式的事务，开启事务的注解驱动
        如果基于注解的和xml的事务都配置了会以注解的优先
     -->
    <tx:annotation-driven transaction-manager="transactionManager"/>

</beans>
```

### DAO接口

```java
/**
 * 第一个泛型参数：需要持久化的实体类
 * 第二个泛型参数：主键ID的类型
 */
public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
```

### 测试类

```java
/**
 * 基于junit4的注解 和junit5不一样
 * ContextConfiguration指定spring配置
 *  如果是配置文件 则用locations或value指定
 *  如果是配置类   则用classes指定
 *  如果不写这两个注解 就不能用spring的功能 如依赖注入
 */
@ContextConfiguration(locations = "/spring.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringDataJpaTest {
    @Autowired
    CustomerRepository repository;

    @Test
    public void testR() {
        Optional<Customer> byId = repository.findById(1L);
        System.out.println(byId.get());
    }

    /**
     * save相当于merge方法  没有指定主键则插入
     *                     指定主键则先查询后修改 如果没有变动则不用修改
     */
    @Test
    public void testC() {
        Customer customer = new Customer();
        customer.setCustName("超哥");
        customer.setCustAddress("四川省");
        repository.save(customer);
    }

    /**
     * 先查询后修改
     * Hibernate: select customer0_.id as id1_0_0_, customer0_.cust_address as cust_add2_0_0_, customer0_.cust_name as cust_nam3_0_0_ from tb_customer customer0_ where customer0_.id=?
     * Hibernate: update tb_customer set cust_address=?, cust_name=? where id=?
     * 如果在修改前不想查询 则可用JPQL
     */
    @Test
    public void testU() {
        Customer customer = new Customer();
        customer.setCustId(9L);
        customer.setCustName("超歌");
        customer.setCustAddress("四川省");
        repository.save(customer);
    }

    /**
     * 在JPA底层调用remove时，如果实体对象是游离状态则会报错
     * 但用springDataJpa的delete方法后没有报错 原因是：
     * Hibernate: select customer0_.id as id1_0_0_, customer0_.cust_address as cust_add2_0_0_, customer0_.cust_name as cust_nam3_0_0_ from tb_customer customer0_ where customer0_.id=?
     * Hibernate: delete from tb_customer where id=?
     * 底层先查询 将实体对象转为托管状态再进行删除的
     */
    @Test
    public void testD() {
        Customer customer = new Customer();
        customer.setCustId(9L);
        repository.delete(customer);
//        repository.deleteById(10L);
    }
}
```

## 九、SpringDataJpa的JavaConfig的配置方式

### 配置类

```java
@Configuration          // 标记当前类为配置类   =xml配文件
@EnableJpaRepositories(basePackages="com.hfut.repositories")  // 启动jpa    <jpa:repositories
@EnableTransactionManagement    // 开启事务 <tx:annotation-driven transaction-manager="transactionManager" />
public class SpringDataJpaConfig {

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
        dataSource.setPassword("010802");
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
        factory.setPackagesToScan("com.hfut.pojo");
        factory.setDataSource(dataSource());
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

}
```

### 测试

```java
@ContextConfiguration(classes = SpringDataJpaConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
```

## 十、SpringDataRepositories

### CrudRepository

```java
//注意：有的操作会返回对象  对这个对象操作不会持久化到数据库

// 用来插入和修改 有主键就是修改 没有就是新增
 // 获得插入后自增id， 获得返回值
 <S extends T> S save(S entity);

 // 通过集合保存多个实体
 <S extends T> Iterable<S> saveAll(Iterable<S> entities);
 // 通过主键查询实体
 Optional<T> findById(ID id);
 // 通过主键查询是否存在 返回boolean
 boolean existsById(ID id);
 // 查询所有
 Iterable<T> findAll();
 // 通过集合的主键 查询多个实体，， 返回集合
 Iterable<T> findAllById(Iterable<ID> ids);
 // 查询总数量
 long count();
 // 根据id进行删除
 void deleteById(ID id);
 // 根据实体进行删除
 void delete(T entity);
 // 删除多个
 void deleteAllById(Iterable<? extends ID> ids);
 // 删除多个传入集合实体
 void deleteAll(Iterable<? extends T> entities);
 // 删除所有
 void deleteAll();
```

### PagingAndSortingRepository

```java
public interface PagingAndSortingRepository<T, ID> extends CrudRepository<T, ID> {
    Iterable<T> findAll(Sort sort); //排序

    Page<T> findAll(Pageable pageable); //分页
}
```

### 测试

```java
@ContextConfiguration(classes = SpringDataJpaConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringDataJpaPagingAndSortTest {
    @Autowired
    CustomerRepository repository;

    /**
     * Page 和 PageRequest是spring-data-commons里的接口
     * 是所有的data共享的
     * Sort也是如此
     * springPage的分页和mysql的limit不一样  limit 5, 3 表示从第6个元素开始接下来的3个元素
     * PageRequest.of(5, 3) 表示每页3个元素，从第5页开始
     */
    @Test
    public void testPaging(){
        Page<Customer> all = repository.findAll(PageRequest.of(2, 2));
        System.out.println(all.getContent());
        System.out.println(all.getTotalPages());
        System.out.println(all.getTotalElements());
    }

    /**
     * 硬编码 custId写死在代码里了
     */
    @Test
    public  void testSort(){

        Sort sort = Sort.by("custId").descending();

        Iterable<Customer> all = repository.findAll(sort);

        System.out.println(all);

    }

    /**
     * 相对于硬编码更好的方式
     */
    @Test
    public  void testSortTypeSafe(){

        Sort.TypedSort<Customer> sortType = Sort.sort(Customer.class);

        Sort sort = sortType.by(Customer::getCustId).descending();

        Iterable<Customer> all = repository.findAll(sort);

        System.out.println(all);

    }
}

```

## 十一、自定义操作-JPQL和SQL

### JPQL

1. 查询如果返回单个实体 就用pojo接收 ， 如果是多个需要通过集合

2. 参数设置方式

   * 索引 ： ?数字

     ```java
     @Query("FROM Customer where custName=?1") //1表示custName在方法参数的位置
     List<Customer> findCustomerByCustName(String custName);
     ```

   * 别名 ： :参数名 结合@Param注解指定参数名字

     ```java
     @Query("FROM Customer where custName=:custName ") //select *  可以省略
     List<Customer> findCustomerByCustName(@Param("custName") String custName);
     ```


3. 增删改

   * 要加上事务的支持

     ```java
     @Transactional // 通常会放在业务逻辑层上面去声明
     @Modifying // 通知springdatajpa 是增删改的操作 @Query 与 @Modifying 这两个注解一起声明，可定义个性化更新操作
     ```

   * 如果是插入方法：一定只能在hibernate下才支持（Insert into..select)，JPQL不支持insert，如果要插入可以用原生的sql

     ```java
     @Transactional
     @Modifying
     @Query("INSERT INTO Customer (custName) SELECT c.custName FROM Customer c where c.custId=?1")
     int insertCustomerBySelect(Long id); //忘记吧！！
     ```

### 原生sql

```java
@Query(value="select * FROM tb_customer where cust_name=:custName "
    ,nativeQuery = true) //加一个属性nativeQuery = true
List<Customer> findCustomerByCustNameBySql(@Param("custName") String custName);

@Transactional
@Modifying   
@Query(value = "INSERT INTO tb_customer(cust_name, cust_address) values(:custName, :custAddress)", nativeQuery = true)
int insertCustomer(@Param("custName") String custName, @Param("custAddress") String custAddress);//原生sql可以插入
```

## 十二、自定义操作-规定方法名

1. 支持的查询方法  **主题关键字（前缀）**
   * 决定当前方法作用
   * 只支持==查询和删除==

![image-20230322140924927](D:\Program Files (x86)\王岩\Typora\imgs\image-20230322140924927.png)

2. 支持的查询方法**谓词关键字和修饰符**
   * 决定查询条件

![image-20230322141131703](D:\Program Files (x86)\王岩\Typora\imgs\image-20230322141131703.png)

![image-20230322141222941](D:\Program Files (x86)\王岩\Typora\imgs\image-20230322141222941.png)

![image-20230322141232524](D:\Program Files (x86)\王岩\Typora\imgs\image-20230322141232524.png)
