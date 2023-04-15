>黑马郝强勇老师
>
>【黑马程序员新版Spring零基础入门到精通，一套搞定spring全套视频教程（含实战源码）】https://www.bilibili.com/video/BV1rt4y1u7q5?vd_source=37d44c19b90722aba78120f5ce91449b

>公众号开发框架FastBootWeixin开发者，《深入理解Spring MVC源代码：从原理分析到实战应用》作者https://my.oschina.net/guangshan

# 一、IOC容器

##1.传统Javaweb开发的困惑

![image-20230406171339638](D:\Program Files (x86)\vanyan\Typora\imgs\image-20230406171339638.png)

![image-20230406171442024](D:\Program Files (x86)\vanyan\Typora\imgs\image-20230406171442024.png)

##2.loC、DI和AOP思想提出

##3.Spring框架的诞生

### Spring Framework技术栈图示

![image-20230406172241511](D:\Program Files (x86)\vanyan\Typora\imgs\image-20230406172241511.png)

### BeanFactory 快速入门

>1. 导入Spring的jar包或Maven坐标; 
>
>   ```xml
>   <dependency>
>       <groupId>org.springframework</groupId>
>       <artifactId>spring-context</artifactId>
>       <version>5.3.7</version>
>   </dependency>
>   <!--其中包含了spring-aop、spring-beans、spring-core、spring-expression这些依赖
>   所以只用导入这个依赖即可-->
>   ```
>
>2. 定义UserService接口及其UserServicelmpl实现类;
>
>3. 创建beans.xml配置文件，将UserServicelmpl的信息配置到该xml中;
>
>4. 编写测试代码，创建BeanFactory，加载配置文件，获取UserService实例对象。
>
>   ```java
>   public static void main(String[] args) {
>       //创建工厂对象
>       DefaultListableBeanFactory beanFactory =
>           new DefaultListableBeanFactory();
>       //创建一个读取器(xml文件)
>       XmlBeanDefinitionReader reader =
>           new XmlBeanDefinitionReader(beanFactory);
>       //读取配置文件给工厂
>       reader.loadBeanDefinitions("bean.xml");
>       Object userService = beanFactory.getBean("userService");
>       System.out.println(userService);
>   }
>   ```
>
>依赖注入步骤：
>
>1）定义UserDao接口及其UserDaolmpl实现类;
>2）修改UserServicelmpl代码，添加一个setUserDao(UserDao userDao)用于接收注入的对象;
>3）修改beans.xml配置文件，在UserDaolmpl的<bean>中嵌入<property>配置注入;
>4）修改测试代码，获得UserService时，setUserService方法执行了注入操作。

### ApplicationContext快速入门

>ApplicationContext 称为Spring容器，是个接口，内部封装了BeanFactory，比BeanFactory功能更丰富更强大，使用ApplicationContext进行开发时，xml配置文件的名称习惯写成applicationContext.xml。
>
>```java
>public static void main(String[] args) {
>    ApplicationContext applicationContext = 
>        new ClassPathXmlApplicationContext("bean.xml");
>    Object userService = applicationContext.getBean("userService");
>}
>```
>
>![image-20230406180949417](D:\Program Files (x86)\vanyan\Typora\imgs\image-20230406180949417.png)

### BeanFactory与ApplicationContext的关系

>1. BeanFactory是Spring的早期接口，称为Spring的Bean工厂，ApplicationContext是后期更高级接口，称之为
>  Spring容器;
>
>2. ApplicationContext在BeanFactory基础上对功能进行了扩展，例如:监听功能、国际化功能等。BeanFactory的API更偏向底层，ApplicationContext的API大多数是对这些底层API的封装;
>
>3. Bean创建的主要逻辑和功能都被封装在BeanFactory中，ApplicationContext不仅继承了BeanFactory，而ApplicationContext内部还维护着BeanFactory的引用，所以，ApplicationContext与BeanFactory既有继承关系，又有融合关系。
>
>  ![image-20230406182518716](D:\Program Files (x86)\vanyan\Typora\imgs\image-20230406182518716.png)
>
>  ![image-20230406182730886](D:\Program Files (x86)\vanyan\Typora\imgs\image-20230406182730886.png)
>
>4. Bean的初始化时机不同，原始BeanFactory是在首次调用getBean时才进行Bean的创建，而ApplicationContext则是配置文件加载，容器一创建就将Bean都实例化并初始化好。
>
>5. ApplicationContext除了继承了BeanFactory外，还继承了ApplicationEventPublisher(事件发布器)、
>   ResouresPatternResolver(资源解析器)、MessageSource(消息资源)等。但是ApplicationContext的核心功能还是BeanFactory。
>
>![image-20230406182415054](D:\Program Files (x86)\vanyan\Typora\imgs\image-20230406182415054.png)<img src="D:\Program Files (x86)\vanyan\Typora\imgs\image-20230406183108006.png" alt="image-20230406183108006" style="zoom:50%;" />

###BeanFactory的继承体系

>BeanFactory是核心接口，项目运行过程中肯定有具体实现参与，这个具体实现就是DefaultListableBeanFactory而ApplicationContext内部维护的Beanfactory的实现类也是它。
>
>![image-20230406190237856](D:\Program Files (x86)\vanyan\Typora\imgs\image-20230406190237856.png)

###ApplicationContext的继承体系

>只在Spring基础环境下，即只导入spring-context坐标时，此时ApplicationContext的继承体系
>
>![image-20230406190551152](D:\Program Files (x86)\vanyan\Typora\imgs\image-20230406190551152.png)
>
>导入其他包会有新的继承：
>
>![image-20230406191055592](D:\Program Files (x86)\vanyan\Typora\imgs\image-20230406191055592.png)
>
>只在Spring环境下常用的ApplicationContext：
>
>| 实现类                             | 功能                    |
>| ---------------------------------- | ----------------------- |
>| ClassPathXmlApplicationContext     | 加载类路径下的xml文件   |
>| FileSystemXmlApplicationContext    | 加载磁盘路径下的xml文件 |
>| AnnotationConfigApplicationContext | 加载注解配置类          |

##4.基于xml的Spring应用

### SpringBean 的配置详解

>![image-20230406191640117](D:\Program Files (x86)\vanyan\Typora\imgs\image-20230406191640117.png)
>

> id相当于是容器map中的key，如果没有指定id则会用类的全限定名代替。name是别名，作用和id类似都可以通过getBean来获取bean。但map的key始终是id(如果既有id又有name，则name会作为另一个map中的key)，只有当未指定id且指定了name时才会将第一个name作为key。
>```xml
><bean id="userService" name="van yao" class="com.hfut.service.impl.UserServiceImpl" />
>```
>
>![image-20230406192806267](D:\Program Files (x86)\vanyan\Typora\imgs\image-20230406192806267.png)![image-20230406192819260](D:\Program Files (x86)\vanyan\Typora\imgs\image-20230406192819260.png)

>默认情况下，==单纯的Spring环境==Bean的作用范围有两个: Singleton和Prototype
>
>* singleton:单例，默认值，Spring容器创建的时候，就会进行Bean的实例化，并存储到容器内部的单例池中，每次getBean时都是从单例池中获取相同的Bean实例;
>* prototype:原型，Spring容器初始化时不会创建Bean实例，当调用getBean时才会实例化Bean，每次getBean都会创建一个新的Bean实例。

>当lazy-init设置为true时为延迟加载，也就是当Spring容器创建的时候，不会立即创建Bean实例，等待用到时在创建Bean实例并存储到单例池中去，后续在使用该Bean直接从单例池获取即可，本质上该Bean还是单例的

>init-method在构造方法之后执行。destroy-method只有在容器显式关闭后才会执行。
>```java
>applicationContext.close();
>```
>
>除此之外，我们还可以通过实现 InitializingBean 接口，完成一些Bean的初始化操作：
>
>```java
>public interface InitializingBean {
>    void afterPropertiesSet() throws Exception;
>}//实现这个接口 重写方法  这个方法和init-method的作用类似 但这个方法先执行
>```
>
>

>Spring的实例化方式主要如下两种:
>
>* 构造方式实例化:底层通过构造方法对Bean进行实例化
>
>  ```xml
>  <bean id="userService" class="com.hfut.service.impl.UserServiceImpl">
>      <constructor-arg name="" value=""></constructor-arg>
>  </bean>
>  ```
>
>* 工厂方式实例化:底层通过调用自定义的工厂方法对Bean进行实例化
>
>  工厂方式实例化Bean，又分为如下三种:
>
>  1. 静态工厂方法实例化Bean
>
>     ```java
>     public class UserServiceFactory {
>         public static UserService xxx(String username) {
>             //Bean创建之前之后可以进行一些其他业务逻辑操作
>             return new UserServiceImpl(age);
>         }//第三方jar包中的有些对象只能通过工厂中的静态类得到，如果要将他们交给容器管理就要用这种方法
>     }
>     ```
>
>     ```xml
>     <bean id="userService" class="com.hfut.factory.UserServiceFactory" factory-method="xxx">
>     	<constructor-arg name="username" value="123"></constructor-arg>
>     </bean>
>     <!--调用UserServiceFactory工厂中的xxx静态方法去创建userService对象-->    
>     <!--注意constructor-arg并不是只能用在构造方法处，只要是创建bean的方法，都可以用constructor-arg指定参数
>     这里xxx方法就是一个工厂创建bean的方法，可以用constructor-arg指定方法的参数-->
>     ```
>
>  2. 实例工厂方法实例化Bean
>
>     ```java
>     public class UserServiceFactory {
>         public UserService xxx() {
>             //Bean创建之前之后可以进行一些其他业务逻辑操作
>             return new UserServiceImpl();
>         }
>     }
>     ```
>
>     ```xml
>     <bean id="userServiceFactory" class="com.hfut.factory.UserServiceFactory" />
>     <bean id="userService" factory-bean="userServiceFactory" factory-method="xxx"></bean>
>     ```
>
>  3. 实现FactoryBean规范延迟实例化Bean
>
>     ```java
>     public class UserServiceFactory implements FactoryBean<UserService>{
>         //类似于实例工厂方法  但是FactoryBean这个接口已经将公共方法写好了 只需要去实现
>         //这样在xml文件中不需要指定factory-bean和factory-method
>         @Override
>         public UserService getobject () throws Exception {
>     	    return new UserServiceImpl ();
>         }
>         @Override
>         public Class<?> getobjectType () {
>         	return UserService.class;
>         }
>     }
>     ```
>
>     ```xml
>     <bean id="userService" class="com.hfut.factory.UserServiceFactory" />
>     <!--获取到的bean不是UserServiceFactory而是userService-->
>     <!--在容器map中key是userService 而value是UserServiceFactory 只有当第一次用到userService时
>     才会帮你创建userService对象并且缓存到另一个map中-->
>     ```

>自动装配
>
>如果被注入的属性类型是Bean引用的话，那么可以在<bean>标签中使用autowire属性去配置自动注入方式，属性值有两个:
>byName:通过属性名自动装配，即去匹配 setXxx 与id="xxx" (name= "xxx")是否一致;
>byType:通过Bean的类型从容器中匹配，匹配出多个相同Bean类型时，报错。
>
>```xml
><bean id="userService" class="xxx" autowire="byName" />
><!--如果容器中有一个bean，这个bean和userService中某个属性的set方法名字相同则会自动注入-->
>```

>Spring自定义命名空间的默认标签：
>
>![image-20230406204043419](D:\Program Files (x86)\vanyan\Typora\imgs\image-20230406204043419.png)
>
>```xml
><?xml version="1.0" encoding="UTF-8"?>
><beans xmlns="http://www.springframework.org/schema/beans"
>       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
>       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
>
>    <bean id="userService" name="van yao" class="com.hfut.service.impl.UserServiceImpl" >
>    </bean><!--默认环境 公共部分 无论什么环境都会生效-->
>
>    <beans profile="dev"><!--开发环境 只有在开发环境生效-->
>        <bean></bean>
>    </beans>
>
>    <beans profile="test"><!--测试环境-->
>        <bean></bean>
>    </beans>
></beans>
>```
>
>```java
>System.setProperty("spring.profiles.active", "dev");//设置环境
>```

### Spring容器的get方法

>| 方法定义                               | 返回值和参数                                                 |
>| -------------------------------------- | ------------------------------------------------------------ |
>| Object getBean (String beanName)       | 根据beanName从容器中获取Bean实例,要求容器中Bean唯一，返回值为Object，需要强转 |
>| T getBean (Class type)                 | 根据Class类型从容器中获取Bean实例，要求容器中Bean类型唯一，返回值为Class类型实例,无需强转 |
>| T getBean (String beanName,Class type) | 根据beanName从容器中获得Bean实例，返回值为Class类型实例，无需强转 |

### Spring 配置非自定义Bean

>配置非自定义的Bean需要考虑如下两个问题:
>
>* 被配置的Bean的实例化方式是什么?无参构造、有参构造、静态工厂方式还是实例工厂方式;
>* 被配置的Bean是否需要注入必要属性。
>
>```xml
><bean id="dataSource" class="com.alibaba. druid.pool.DruidDataSource">
>    <property name="driverClassName" value="com.mysql .jdbc.Driver"></property>
>    <property name="url" value="jdbc:mysql://localhost:3306/mybatis"></property>
>    <property name="username" value="root"></property>
>    <property name="password" value="root"></property>
></bean><!--通过无参构造+set方法创建这个bean-->
>```
>
>```xml
><bean class="java. lang.Class" factory-method="forName">
>    <constructor-arg name="className" value=" com.mysql.jdbc.Driver" />
></bean>
><bean id="connection" class="java.sql.DriverManager" factory-method="getConnection" scope="prototype">
>    <constructor-arg name="url" value="jdbc:mysql:///mybatis" />
>    <constructor-arg name="user" value="root" />
>    <constructor-arg name="password" value="root" />
></bean><!--通过静态工厂创建这两个bean-->
>```

### Bean 实例化的基本流程

>Spring容器在进行初始化时，会将xml配置的<bean>的信息封装成一个BeanDefinition对象，所有的BeanDefinition存储到一个名为beanDefinitionMap的Map集合中去，Spring框架在对该Map进行遍历，使用反射创建Bean实例对象，创建好的Bean对象存储在一个名为singletonObjects的Map集合中，当调用getBean方法时则最终从该Map集合中取出Bean实例对象返回。
>
>![image-20230408150846636](D:\Program Files (x86)\vanyan\Typora\imgs\image-20230408150846636.png)
>
>* 加载xml配置文件，解析获取配置中的每个<bean>的信息，封装成一个个的BeanDefinition对象;
>* 将BeanDefinition存储在一个名为beanDefinitionMap的Map<String,BeanDefinition>中;
>* ApplicationContext底层遍历beanDefinitionMap，创建Bean实例对象;
>* 创建好的Bean实例对象，被存储到一个名为singletonObjects的Map<String,Object>中;
>* 当执行applicationContext.getBean(beanName)时，从singletonObjects去匹配Bean实例返回。

### Spring的后处理器

>Spring的后处理器是Spring对外开发的重要扩展点，允许我们介入到Bean的整个实例化流程中来，以达到动态注册
>BeanDefinition，动态修改BeanDefinition，以及动态修改Bean的作用。
>Spring主要有两种后处理器:
>BeanFactoryPostProcessor: Bean工厂后处理器，在BeanDefinitionMap填充完毕，Bean实例化之前执行;
>BeanPostProcessor: Bean后处理器，一般在Bean实例化之后，填充到单例池singletonObjects之前执行。

> Bean工厂后处理器-BeanFactoryPostProcessor
> BeanFactoryPostProcessor是一个接口规范，实现了该接口的类只要交由Spring容器管理的话，那么Spring就会回调该接口的方法，用于对BeanDefinition注册和修改的功能。
>
> ```java
> @FunctionalInterface
> public interface BeanFactoryPostProcessor {
>     void postProcessBeanFactory(ConfigurableListableBeanFactory var1) throws BeansException;
> }
> ```
>
> ```java
> public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
>     @Override
>     void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
>         //前提是MyBeanFactoryPostProcessor要作为一个bean被容器管理
>         System.out.println("BeanDefinitionMap填充完毕后调用此方法");
>         //beanFactory就是bean工厂 可以在这个方法中修改内部数据
>         //获取userService这个bean标签对应的beanDefintion 将他的对应的类修改为userDaoImpl
>         BeanDefintion beanDefintion = beanFactory.getBeanDefinition("userService");
>         beanDefintion.setBeanClassName("userDaoImpl");
>         
>         //还可以动态添加bean标签
>         BeanDefintion beanDefintion = new RootBeanDefintion();
>         bean.setBeanClassName("com.hfut.dao.impl.PerSonDaoImpl");
>         //强转  DefaultListableBeanFactory是ConfigurableListableBeanFactory的子类 功能强大
>         //而ConfigurableListableBeanFactory没有注册BeanDefintion的方法
>         DefaultListableBeanFactory dlbf = (DefaultListableBeanFactory) beanFactory;
>         //而DefaultListableBeanFactory有注册BeanDefintion的方法
>         //第一个参数是beanName 第二个参数是你要注册的BeanDefintion对象
>         dlbf.registerBeanDefintion("personDao", beanDefintion);
>     }
> }
> ```
>
> ![image-20230408154159528](D:\Program Files (x86)\vanyan\Typora\imgs\image-20230408154159528.png)
>
> Spring提供了一个BeanFactoryPostProcessor的子接口BeanDefinitionRegistryPostProcessor专门用于注册BeanDefinition操作。
>
> ```java
> public interface BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor {
>     //这个接口自己也提供了一个后置方法 这个方法是专门用来注册beanDefition的
>     //BeanDefinitionRegistry也是个beanFactory，不过它有注册beanDefition的方法
>     void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry var1) throws BeansException;
> }
> ```

>Bean被实例化后，到最终缓存到名为singletonObjects单例池之前，中间会经过Bean的初始化过程，例如:属性的填充、初始方法init的执行等，其中有一个对外进行扩展的点BeanPostProcessor，我们称为Bean后处理。跟上面的Bean工厂后处理器相似，它也是一个接口，实现了该接口并被容器管理的BeanPostProcessor，会在流程节点上被Spring自动调用。
>
>```java
>public interface BeanPostProcessor {
>    @Nullable
>    default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
>        //这里可以对bean进行修改
>        return bean;
>    }
>
>    @Nullable
>    default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
>        //这里可以对bean进行修改
>        return bean;
>    }
>}
>```
>
>先调用构造方法创建bean，再执行postProcessBeforeInitialization，然后执行bean的init-method方法，最后执行postProcessAfterInitialization方法

### Spring Bean的生命周期

>Spring Bean的生命周期是从Bean 实例化之后，即通过反射创建出对象之后，到Bean成为一个完整对象，最终存储到单例池中，这个过程被称为Spring Bean的生命周期。Spring Bean的生命周期大体上分为三个阶段:


>Bean的实例化阶段: Spring框架会取出BeanDefinition的信息进行判断当前Bean的范围是否是singleton的,是否不是延迟加载的，是否不是FactoryBean等，最终将一个普通的singleton的Bean通过反射进行实例化;


>Bean的初始化阶段:Bean创建之后还仅仅是个"半成品"，还需要对Bean实例的属性进行填充、执行一些Aware接口方法、执行BeanPostProcessor方法、执行InitializingBean接口的初始化方法、执行自定义初始化init方法等。该阶段是Spring最具技术含量和复杂度的阶段，Aop增强功能，后面要学习的Spring的注解功能等、spring高频面试题Bean的循环引用问题都是在这个阶段体现的;
>
>1. Bean实例的属性填充
>
>   >![image-20230408170819340](D:\Program Files (x86)\vanyan\Typora\imgs\image-20230408170819340.png)
>   >
>   >Spring在进行属性注入时，会分为如下几种情况:
>   >
>   >* 注入普通属性，String、int或存储基本类型的集合时，直接通过set方法的反射设置进去;
>   >* 注入单向对象引用属性时，从容器中getBean获取后通过set方法反射设置进去，如果容器中没有，则先创建被注入对象Bean实例(完成整个生命周期)后，在进行注入操作;
>   >* 注入双向对象引用属性时，就比较复杂了，涉及了循环引用（(循环依赖)问题，下面会详细阐述解决方案。
>
>2. Aware接口属性注入
>
>   >Aware接口是一种框架辅助属性注入的一种思想，其他框架中也可以看到类似的接口。框架具备高度封装性，我们接触到的一般都是业务代码，一个底层功能API不能轻易的获取到，但是这不意味着永远用不到这些对象，如果用到了，就可以使用框架提供的类似Aware的接口，让框架给我们注入该对象。
>   >
>   >```java
>   >public class UserServiceImpl implements UserService, ApplicationContextAware {
>   >
>   >    ApplicationContext applicationContext;
>   >
>   >    @Override
>   >    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException{
>   >        this.applicationContext = applicationContext;
>   >    }
>   >    //将UserServiceImpl交给容器管理 容器看到你实现了ApplicationContextAware  
>   >    //就会找setApplicationContext方法来注入applicationContext对象
>   >    //可以通过类似的方法获取底层的一些对象
>   >}
>   >```
>
>3. BeanPostProcessor的before()方法回调
>
>4. lnitializingBean接口的初始化方法回调
>
>5. 自定义初始化方法init回调
>
>6. BeanPostProcessor的after()方法回调

>Bean的完成阶段:经过初始化阶段，Bean就成为了一个完整的Spring Bean，被存储到单例池singletonObjects中去了，即完成了Spring Bean的整个生命周期

### 循环依赖问题

>Spring提供了三级缓存存储完整Bean实例和半成品Bean实例，用于解决循环引用问题
>在DefaultListableBeanFactory的上四级父类DefaultSingletonBeanRegistry中提供如下三个Map:
>
>```java
>public class DefaultSingletonBeanRegistry extends SimpleAliasRegistry implements SingletonBeanRegistry {
>    //一级缓存  存储成品对象bean
>    private final Map<String, Object> singletonObjects = new ConcurrentHashMap(256);
>	//二级缓存	存储半成品对象  但对象已经被其他对象依赖
>    private final Map<String, Object> earlySingletonObjects = new ConcurrentHashMap(16);
>    //三级缓存	存储半成品对象 并且对象未被其他对象依赖
>    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap(16);
>}
>```
>
>

###Spring loC整体流程总结

![image-20230408212051638](D:\Program Files (x86)\vanyan\Typora\imgs\image-20230408212051638.png)

### Mybatis原始操作代码

>```xml
><dependency>
>    <groupId>org.mybatis</groupId>
>    <artifactId>mybatis</artifactId>
>    <version>3.5.5</version>
></dependency>
><dependency>
>    <groupId>mysql</groupId>
>    <artifactId>mysql-connector-java</artifactId>
>    <version>5.1.49</version>
></dependency>
>```
>
>```xml
><!--mybaits-config.xml-->
><?xml version="1.0" encoding="UTF-8" ?>
><!DOCTYPE configuration
>  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
>  "https://mybatis.org/dtd/mybatis-3-config.dtd">
><configuration>
>  <environments default="development">
>    <environment id="development">
>      <transactionManager type="JDBC"/>
>      <dataSource type="POOLED">
>        <property name="driver" value="${driver}"/>
>        <property name="url" value="${url}"/>
>        <property name="username" value="${username}"/>
>        <property name="password" value="${password}"/>
>      </dataSource>
>    </environment>
>  </environments>
>  <mappers>
>      <package name="com.hfut.mapper"></package>
>    <!--<mapper resource="org/mybatis/example/BlogMapper.xml"/>-->
>  </mappers>
></configuration>
><!--spring整合mybatis后就不用这个配置了 而在spring配置文件中配置-->
>```
>
>```xml
><!--userMapper.xml  这个xml文件的包路径要和mapper类一样-->
><?xml version="1.0" encoding="UTF-8" ?>
><!DOCTYPE mapper
>  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
>  "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
><mapper namespace="com.hfut.mapper.UserMapper">
>  <select id="finAll" resultType="com.hfut.pojo.User">
>    select * from tb_user
>  </select>
></mapper>
>```
>
>```java
>InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
>SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
>SelSession sqlSession = sqlSessionFactory.openSession();
>UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
>//...
>//spring整合mybatis后将上述代码消除 这些对象都交给容器管理
>```

###Spring xml方式整合第三方框架

>MyBatis提供了mybatis-spring.jar专门用于两大框架的整合。Spring整合MyBatis的步骤如下:
>
>* 导入MyBatis整合Spring的相关坐标;(请见资料中的pom.xml)
>* 编写Mapper和Mapper.xml;
>* 配置SqlSessionFactoryBean和MapperScannerConfigurer;
>* 编写测试代码

>```xml
><dependency>
>    <groupId>mysql</groupId>
>    <artifactId>mysql-connector-java</artifactId>
>    <version>5.1.49</version>
></dependency>
><dependency>
>    <groupId>org.springframework</groupId>
>    <artifactId>spring-jdbc</artifactId>
>    <version>5.2.13.RELEASE</version>
></dependency>
><dependency>
>    <groupId>org.mybatis</groupId>
>    <artifactId>mybatis-spring</artifactId>
>    <version>2.0.5</version>
></dependency>
>```
>
>```xml
><bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
>    <property name="driverClassName" value="xx"></property>
>    <property name="url" value="xx"></property>
>    <property name="username" value="xx"></property>
>    <property name="password" value="xx"></property>
></bean>
><!--配置SqlSessionFactoryBean 将SqlSessionFactory存储到容器中-->
><bean class="org.mybatis.spring.SqlSessionFactoryBean">
>    <property name="dataSource" ref="dataSource"></property>
></bean>
><!--扫描指定包 产生mapper对象放到容器中-->
><bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
>    <property name="basePackage" value="com.hfut.mapper"></property>
></bean>
>```

>Spring整合MyBatis的原理剖析
>
>整合包里提供了一个SqlSessionFactoryBean和一个扫描Mapper的配置对象，SqlSessionFactoryBean一旦被实例化，就开始扫描Mapper并通过动态代理产生Mapper的实现类存储到Spring容器中。相关的有如下四个类:
>
>* SqlSessionFactoryBean:需要进行配置，用于提供SqlSessionFactory;
>
>  >```java
>  >public class SqlSessionFactoryBean implements FactoryBean<SqlSessionFactory>, InitializingBean{
>  >    //InitializingBean接口的方法  和init-method类似 在这个方法内部给sqlSessionFactory赋值
>  >    public void afterPropertiesSet() throws Exception {
>  >        //... 
>  >        this.sqlSessionFactory = this.buildSqlSessionFactory();
>  >    }
>  >    //FactoryBean接口的方法 是FactoryBean规范延迟实例化Bean  目的就是创建SqlSessionFactory
>  >    public SqlSessionFactory getObject() throws Exception {
>  >        if (this.sqlSessionFactory == null) {
>  >            this.afterPropertiesSet();
>  >        }
>  >        return this.sqlSessionFactory;
>  >    }
>  >}
>  >```
>  >
>  >
>
>* MapperScannerConfigurer:"需要进行配置，用于扫描指定mapper注册BeanDefinition;
>
>   > 视频讲解
> 
> * MapperFactoryBean: Mapper的FactoryBean，获得指定Mapper时调用getObject方法;
> 
> * ClassPathMapperScanner: definition.setAutowireMode(2)修改了自动注入状态，所以MapperFactoryBean中的setSqlSessionFactory会自动注入进去
> 

>Spring 整合其他组件时就不像MyBatis这么简单了，例如Dubbo框架在于Spring进行整合时，要使用Dubbo提供的命名空间的扩展方式，自定义了一些Dubbo的标签，这里用context举例。
>
>```xml
><?xml version="1.0" encoding="UTF-8"?>
><beans xmlns="http://www.springframework.org/schema/beans"
>  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
>  xmlns:context="http://www.springframework.org/schema/context"
>  xsi:schemaLocation="
>   http://www.springframework.org/schema/beans
>   http://www.springframework.org/schema/beans/spring-beans.xsd
>   http://www.springframework.org/schema/context
>   http://www.springframework.org/schema/context/spring-context.xsd">
>
><!--    加载properties文件-->
><context:property-placeholder location="classpath:jdbc.properties" />
><!--    配置数据源信息-->
><bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
>   <property name="driverClassName" value="${jdbc.driver}"></property>
>   <property name="url" value="${jdbc.url}"></property>
>   <property name="username" value="${jdbc.username}"></property>
>   <property name="password" value="${jdbc.password}"></property>
></bean>
></beans>
>```
>
>通过上述分析，我们清楚的了解了外部命名空间标签的执行流程(视频讲解)，如下：
>
>* 将自定义标签的约束与物理约束文件与网络约束名称的约束以键值对形式存储到一个spring.schemas文件里，该文件存储在类加载路径的META-INF里，Spring会自动加载到。这个东西是用来提示关键字的。
>* 将自定义命名空间的名称与自定义命名空间的处理器映射关系以键值对形式存在到一个叫spring.handlers文件里，该文件存储在类加载路径的META-INF里，Spring会自动加载到;
>* 准备好NamespaceHandler，如果命名空间只有一个标签，那么直接在parse方法中进行解析即可，一般解析结果就是注册该标签对应的BeanDefinition。如果命名空间里有多个标签，那么可以在init方法中为每个标签都注册一个BeanDefinitionParser，在执行NamespaceHandler的parse方法时在分流给不同的BeanDefinitionParser进行解析(重写doParse方法即可)。

### 自定义命名空间demo

> 视频讲解
>
> 设想自己是一名架构师，进行某一个框架与Spring的集成开发，效果是通过一个指示标签，向Spring容器中自动注入一个BeanPostProcessor。
> ```xml
> <?xml version="1.0" encoding="UTF-8"?>
> <beans xmlns="http://www.springframework.org/schema/beans"
>     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
>     xmlns:haohao="http://www.hfut.com/haohao"
>     xsi:schemaLocation="
> http://www.springframework.org/schema/beans
> http://www.springframework.org/schema/beans/spring-beans.xsd
> http://www.hfut.com/haohao
> http://www.hfut.com/haohao/haohao-annotation.xsd">
>     <haohao:annotation-driven />
> </beans>
> ```
>
> <img src="D:\Program Files (x86)\vanyan\Typora\imgs\image-20230409131326682.png" alt="image-20230409131326682" style="zoom:67%;" />
>
> ```properties
> #spring.handlers
> http\://www.hfut.com/haohao=com.hfut.handlers.HaohaoNamespaceHandler
> ```
>
> ```properties
> #spring.schemas
> http\://www.hfut.com/haohao/haohao-annotation.xsd=com/hfut/haohao/config/haohao-annotation.xsd
> #com/hfut/haohao/config/haohao-annotation.xsd这个文件里是自定义标签的约束(IDEA关键字提示)
> ```
>
> 步骤分析:
>
> 1. 确定命名空间名称、schema虚拟路径、标签名称;
> 2. 编写schema约束文件haohao-annotation.xsd
> 3. 在类加载路径下创建META目录，编写约束映射文件spring.schemas和处理器映射文件spring.handlers
> 4. 编写命名空间处理器HaohaoNamespaceHandler，在init方法中注册HaohaoBeanDefinitionParser
> 5. 编写标签的解析器HaohaoBeanDefinitionParser，在parse方法中注册HaohaoBeanPostProcessor
> 6. 编写HaohaoBeanPostProcessor
>
> ==以上五步是框架开发者写的，以下是框架使用者写的==
>
> 1. 在applicationContext.xml配置文件中引入命名空间
> 2. 在applicationContext.xml配置文件中使用自定义的标签

##5.基于注解的Spring应用

### 5.1 Bean基本注解开发

>使用@Component注解替代<bean>标签
>
>可以通过@Component注解的value属性指定当前Bean实例的beanName，也可以省略，不写的情况下为当前类名首字母小写
>
>使用注解对需要被Spring实例化的Bean进行标注，但是需要告诉Spring去哪找这些Bean，要配置组件扫描路径
>
>```xml
><!--告知spring框架去itheima包及其子包下去扫描使用了注解的类-->
><context:component-scan base-package="com.itheima"/>
><!--要加入context命名空间-->
>```
>
>```xml
><bean id="" name="" class="" scope="" lazy-init="" init-method="" destroy-method=""abstract="" autowire="" factory-bean="" factory-method=""></bean>
>```
>
>| xml配置                  | 注解           | 描述                                                         |
>| ------------------------ | -------------- | ------------------------------------------------------------ |
>| <bean scope="">          | @Scope         | 在类上或使用了@Bean标注的方法上，标注Bean的作用范围，取值为singleton或prototype |
>| <bean lazy-init="">      | @Lazy          | 在类上或使用了@Bean标注的方法上，标注Bean是否延迟加载，取值为true和false |
>| <bean init-method="">    | @PostConstruct | 在方法上使用，标注Bean的实例化后执行的方法                   |
>| <bean destroy-method=""> | @PreDestroy    | 在方法上使用，标注Bean的销毁前执行方法                       |

### 5.2 Bean依赖注入注解开发

>```xml
><bean id="" class="">
>   <property name="" value=""></property>
>   <property name="" ref=""></property>
></bean>
>```
>
>| 属性注入注解 | 描述                                                         |
>| ------------ | ------------------------------------------------------------ |
>| @Value       | 使用在字段或方法上，用于注入普通数据                         |
>| @Autowired   | 使用在字段或方法上，用于根据类型(byType)注入引用数据，类型相同匹配名字，使用在字段或方法上 |
>| @Qualifier   | 结合@Autowired，根据名称注入使用在字段或方法上               |
>| @Resource    | 根据类型或名称进行注入，未指定名称则先根据类型注入           |
>
>注解方式可以不用提供set方法，底层利用反射直接强制注入。但是这些注解也可以写到set方法上，效果一样。
>set方法的名字不一定叫setXxx，只要参数类型是你要注入的类型即可。
>
>```java
>@Value("van")//这种方式写死了 相当于 String username="van"
>private String username;
>
>//下面这种方式先要将properties文件引入<context:property-placeholder location="classpath:jdbc.properties" />
>@Value("${jdbc.username}")
>private String username;  
>```
>
>```java
>@Autowired
>public void xxx(UserDao userDao) {}//set方法的名字随便起  但参数要是你注入的类型
>
>@Autowired
>public void yyy(List<UserDao> userDaoList) {}//这个方法会将容器中所有的UserDao注入到这个集合中
>```

### 5.3 非自定义Bean注解开发

>非自定义Bean一般是第三方jar包提供的类，要将这些类对象加入到容器中使用，非自定义Bean不能像自定义Bean一样使用@Component进行管理，因为不能在源码上加注解，非自定义Bean要通过工厂的方式进行实例化,使用@Bean标注方法即可，@Bean的属性为beanName，如果不指定则为当前工厂方法名称。
>
>```java
>@Component
>public class OtherBean
>    @Bean ("dataSource")//如果不指定名字 则bean的名字为方法名
>    public DataSource datasource (){
>        DruidDataSource dataSource = new DruidDataSource();
>        return dataSource;
>    }
>
>	//注入属性可以在参数上加注解  这里的 @Qualifier可以单独使用  
>	//@Autowired可以省略 会自动根据类型注入
>	@Bean ("dataSource")
>    public DataSource datasource (@Value("${jdbc.username}")String username, @Autowired UserDao userdao,
>                                 @Qualifier UserDao userdao){
>        DruidDataSource dataSource = new DruidDataSource();
>        return dataSource;
>    }
>}
>```

### 5.4 Bean配置类的注解开发

>@Component等注解替代了<bean>标签，但是像<import>、<context.componentScan>等非<bean>标签怎样去使用注解替代呢?
>
>定义一个配置类替代原有的xml配置文件，<bean>标签以外的标签，一般都是在==配置类==上使用注解完成的
>
>```java
>@Configuration//标注当前类是一个配置类（替代配置文件）+ @Component
>//<context:component-scan base-package="com.itheima" />
>@ComponentScan("com.itheima")
>//<context:property-placeholder location="classpath: jdbc.properties" />
>@Propertysource ("classpath:jdbc.properties")
>//<import resource="" />
>@Import(otherBean.class)
>public class SpringConfig {}
>```
>
>base-package的配置方式:
>
>* 指定一个或多个包名：扫描指定包及其子包下使用注解的类
>* 不配置包名：扫描当前@componentScan注解配置类所在包及其子包下的类
>
>@Component和@Configuration的细微区别：https://blog.csdn.net/qq_41378597/article/details/88930118

### 5.5 Spring配置其他注解

#### 5.5.1 @Primary

>扩展:@Primary注解用于标注相同类型的Bean优先被使用权，@Primary是Spring3.0引入的，与@Component和@Bean一起使用，标注该Bean的优先级更高，则在通过类型获取Bean或通过@Autowired根据类型进行注入时，会选用优先级更高的。
>
>```java
>@Repository("userDao2")
>@Primary
>public class UserDaoImpl2 implements UserDao{}
>
>@Bean
>@Primary
>public UserDao userDao02(){ return new UserDaoImpl2();}
>```

#### 5.5.2 @Profile

>@Profile注解的作用同于xml配置时学习profile属性，是进行环境切换使用的。
>
>注解@Profile标注在类或方法上，标注当前产生的Bean从属于哪个环境，只有激活了当前环境，被标注的Bean才能被注册到Spring容器里，不指定环境的Bean，任何环境下都能注册到Spring容器里。
>
>```java
>@Repository("userDao2")
>@Profile("test")
>public class UserDaoImpl2 implements UserDao{}
>```
>
>可以使用以下两种方式指定被激活的环境:
>
>* 使用命令行动态参数，虚拟机参数位置加载 -Dspring.profiles.active=test
>
>* 使用代码的方式设置环境变量System.setProperty( "spring.profiles.active" ,"test");

#### 5.5.3 @Import

>1、@Import一个普通类 spring会将该类加载到spring容器中
>
>2、@Import一个类，该类实现了ImportBeanDefinitionRegistrar接口，在重写的registerBeanDefinitions方法里面，能拿到BeanDefinitionRegistry bd的注册器，能手工往beanDefinitionMap中注册 beanDefinition
>
>3、@Import一个类 该类实现了ImportSelector 重写selectImports方法该方法返回了String[]数组的对象，数组里面的类都会注入到spring容器当中
>
>https://blog.csdn.net/weixin_45453628/article/details/124234317
>
>https://www.51cto.com/article/747496.html

>```java
>//import的第二种用法   往容器中注definition
>public interface ImportBeanDefinitionRegistrar {
>    default void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, 
>                      BeanDefinitionRegistry registry, 
>                                         BeanNameGenerator importBeanNameGenerator) {
>        this.registerBeanDefinitions(importingClassMetadata, registry);
>    }
>
>    default void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, 
>                                         BeanDefinitionRegistry registry) {
>    }
>}
>
>public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
>    
>    @Override
>    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, 
>    						BeanDefinitionRegistry registry, 
>                                        BeanNameGenerator importBeanNameGenerator) {
>        //注册BeanDefinition
>        BeanDefinition beanDefinition = new RootBeanDefinition();
>        beanDefinition.setBeanClassName (otherBean2.class.getName());
>        registry.registerBeanDefinition("otherBean2", beanDefinition);
>    }
>}
>
>@Import("MyImportBeanDefinitionRegistrar.class")//import的第二种用法
>public class SpringConfig(){}//spring配置类
>```

>```java
>//import的第三种用法  往容器中批量增加bean
>public interface ImportSelector {
>    //返回的数组封装是需要被注册到Spring容器中的Bean的全限定名
>    String[] selectImports(AnnotationMetadata var1);
>}
>
>public class MyImportSelector implements ImportSelector {
>    @Override
>    public string[] selectImports(AnnotationMetadata annotationMetadata){
>        //annotationMetadata是注解媒体数组 封装了当前使用了@Import注解的类上的其他注解
>        Map<String, Object> map =//获取ComponentScan注解的属性和值
>            annotationMetadata.getAnnotationAttributes(ComponentScan.class.getName());
>        String b = map.get("basePackages");//获取ComponentScan的basePackages属性的值
>        
>        //返回的数组封装是需要被注册到Spring容器中的Bean的全限定名
>        return new string[] {otherBean2.class.getName()};
>    }
>}
>
>@Import("MyImportSelector.class")//import的第三种用法
>public class SpringConfig(){}//spring配置类

###5.6 Spring注解的解析原理

> 视频讲解

###5.7 Spring注解方式整合第三方框架

>```xml
><bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
>    <property name="driverClassName" value="xx"></property>
>    <property name="url" value="xx"></property>
>    <property name="username" value="xx"></property>
>    <property name="password" value="xx"></property>
></bean>
><!--配置SqlSessionFactoryBean 将SqlSessionFactory存储到容器中-->
><bean class="org.mybatis.spring.SqlSessionFactoryBean">
>	<property name="dataSource" ref="dataSource" />
></bean>
><!--扫描指定包 产生mapper对象放到容器中-->
><bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
>	<property name="basePackage" value="com.hfut.mapper">
></bean>
>```
>
>用注解代替上面三个bean
>
>```java
>@Configuration
>@ComponentScan("com.itheima")
>@Propertysource ("classpath:jdbc.properties")
>@MapperScan("com.hfut.mapper")//代替上面的MapperScannerConfigurer
>public class SpringConfig {
>    @Bean
>    public DataSource dataSource(
>        @Value("${jdbc.driver}")String driver, 
>        @Value("${jdbc.url}")String url, 
>        @Value("${jdbc.username}")String username, 
>        @Value("${jdbc.password}")String password
>    ) {
>        DruidDataSource dataSource = new DruidDataSource();
>        dataSource.setDriverClassName (driver) ;
>        dataSource.setUrl(url);
>        datasource.setUsername (username);
>        dataSource.setPassword(password);
>        return dataSource;
>    }
>    
>    @Bean
>    public sqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource){
>    	SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
>        sqlSessionFactoryBean.setDataSource (dataSource);
>        return sqlSessionFactoryBean;
>    }
>}
>```

>源码讲解：视频
>
>```java
>@Import({MapperScannerRegistrar.class})
>public @interface MapperScan {}//MapperScannerRegistrar就实现了ImportBeanDefinitionRegistrar接口
>
>public class MapperScannerRegistrar implements ImportBeanDefinitionRegistrar
>    
>public interface ImportBeanDefinitionRegistrar {
>    default void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
>        this.registerBeanDefinitions(importingClassMetadata, registry);
>    }
>
>    default void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
>    }
>}
>```
>
>@lmport可以导入如下三种类:
>
>* 普通的配置类
>* 实现lmportSelector接口的类
>* 实现==lmportBeanDefinitionRegistrar==接口的类
>
>@import注解作用：https://blog.csdn.net/weixin_45453628/article/details/124234317

# 二、AOP

##1. AOP简介

### 1.1 概念

>AOP，Aspect Oriented Programming，面向切面编程，是对面向对象编程OOP的升华。OOP是纵向对一个事物的抽象，一个对象包括静态的属性信息，包括动态的方法信息等。而AOP是横向的对不同事物的抽象，属性与属性、方法与方法、对象与对象都可以组成一个切面，而用这种思维去设计编程的方式叫做面向切面编程

### 1.1 AOP思想的实现方案

>动态代理技术，在运行期间，对目标对象的方法进行增强，代理对象同名方法内可以执行原有逻辑的同时嵌入执行其他增强逻辑或其他对象的方法

### 1.1 模拟AOP的基础代码

>```java
>@Component
>public class MyAdvice {
>    public void beforeAdvice() {
>        System.out.println("前置增强");
>    }
>    public void afterAdvice() {
>        System.out.println("后置增强");
>    }
>}
>```
>
>```java
>@Configuration
>@ComponentScan("com.hfut")
>public class SpringConfig {
>    public static void main(String[] args) {
>        AnnotationConfigApplicationContext context =
>                new AnnotationConfigApplicationContext(SpringConfig.class);
>        UserService bean = context.getBean(UserService.class);
>        bean.show();
>    }
>}
>```
>
>```java
>@Service
>public class UserServiceImpl implements UserService {
>    @Override
>    public String toString() {
>        return "UserServiceImpl{}";
>    }
>
>    public void show() {
>        System.out.println("show...");
>    }
>}
>```
>
>```java
>@Component
>public class MockAopBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {
>
>    private ApplicationContext applicationContext;
>    @Override
>    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
>        //对com.hfut.service.impl包下的对象方法增强
>        if (bean.getClass().getPackage().getName().equals("com.hfut.service.impl")) {
>            Object beanProxy = Proxy.newProxyInstance(//jdk动态代理
>                    bean.getClass().getClassLoader(),
>                    bean.getClass().getInterfaces(),
>                    new InvocationHandler() {
>                        @Override
>                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
>                            MyAdvice myAdvice = applicationContext.getBean(MyAdvice.class);
>                            myAdvice.beforeAdvice();
>                            Object invoke = method.invoke(bean, args);
>                            myAdvice.afterAdvice();
>                            return invoke;
>                        }
>                    }
>            );
>            return beanProxy;
>        }
>        return bean;
>    }
>	//为了从容器中获取MyAdvice
>    @Override
>    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
>        this.applicationContext = applicationContext;
>    }
>}
>```

### 1.4 AOP相关概念

>| 概念      | 单词      | 解释                                             |
>| --------- | --------- | ------------------------------------------------ |
>| 目标对象  | Target    | 被增强的方法所在的对象                           |
>| 代理对象  | Proxy     | 对目标对象进行增强后的对象，客户端实际调用的对象 |
>| 连接点    | JoinPoint | 目标对象中可以被增强的方法                       |
>| 切入点    | PointCut  | 目标对象中实际被增强的方法                       |
>| 通知\增强 | Advice    | 增强部分的代码逻辑                               |
>| 切面      | Aspect    | 增强和切入点的组合                               |
>| 织入      | Weaving   | 将通知和切入点组合动态组合的过程                 |

##2. 基于xml配置的AOP

###2.1 xml方式AOP快速入门

#### 2.1.1 导入AOP相关坐标;

>```xml
><dependency>
>    <groupId>org.aspectj</groupId>
>    <artifactId>aspectjweaver</artifactId>
>    <version>1.9.6</version>
></dependency>
><!--aspectjweaver和springaop的关系就是JPA和Hibernate的关系
>aspectjweaver是一种实现-->
>```

#### 2.1.2 准备目标类、准备增强类，并配置给Spring管理;

>```xml
><!--配置目标类-->
><bean id="userService" class="com.hfut.service.impl.UserServiceImpl"></bean>
><!--配置通知类-->
><bean id="myAdvice" class="com.hfut.advice.MyAdvice"></bean>
>
>```

#### 2.1.3 配置切点表达式(哪些方法被增强);

#### 2.1.4 配置织入(切点被哪些通知方法增强，是前置增强还是后置增强)。

>```xml
><!--aop配置-->
><aop:config>
>    <!--配置切点表达式-->
>    <aop:pointcut id="myPointCut" expression="execution(voidcom.hfut.service.impl.UserServiceImpl.show())"/>
>    <!--配置织入-->
>    <aop:aspect ref="myAdvice">
>        <aop:before method="beforeAdvice" pointcut-ref="myPointCut"/>
>    </aop:aspect> 
></aop:config>
>```

### 2.2 xml方式AOP配置详解

#### 2.2.1 切点表达式的配置方式

>```xml
><aop:config>
>    <aop:pointcut id="myPointCut" expression="execution(void com.hfut.service.impl.UserServiceImpl.show())"/>
>    <aop:pointcut id="myPointCut2" expression="execution(void com.hfut.service.impl.UserServiceImpl.show2())"/>
>  
>    <aop:aspect ref="myAdvice">
>        <aop:before method="beforeAdvice" pointcut-ref="myPointCut2"/>
>        <aop:before method="beforeAdvice" pointcut="execution(xxx)"/><!--第二种方式-->
>    </aop:aspect> 
></aop:config>
>```

#### 2.2.2 切点表达式的配置语法

>```java
>execution ([访问修饰符] 返回值类型 包名.类名.方法名(参数))
>```
>
>访问修饰符可以省略不写;
>返回值类型、某一级包名、类名、方法名可以使用*表示任意;
>包名与类名之间使用单点.表示该包下的类，使用双点..表示该包及其子包下的类;
>参数列表可以使用两个点..表示任意参数。

#### 2.2.3 通知的类型

>AspectJ的通知有以下五种类型
>
>| 通知名称 | 配置方式            | 执行时机                                                 |
>| -------- | ------------------- | -------------------------------------------------------- |
>| 前置通知 | aop:before          | 目标方法执行之前执行                                     |
>| 后置通知 | aop:after-returning | 目标方法执行之后执行，目标方法异常时，不再执行           |
>| 环绕通知 | aop:around          | 目标方法执行前后执行，目标方法异常时，环绕后方法不再执行 |
>| 异常通知 | aop:after-throwing  | 目标方法抛出异常时执行                                   |
>| 最终通知 | aop:after           | 不管目标方法是否有异常，最终都会执行                     |
>
>```java
>public void around (ProceedingJoinPoint joinPoint) throws Throwable {
>    //环绕前
>    system.out.println("环绕前通知");
>    //目标方法
>    joinPoint.proceed ();
>    //环绕后
>    system.out.println ( "环绕后通知");
>}
>
><aop:around method="around" pointcut-ref="myPointCut"/>
>```
>
>通知方法在被调用时，Spring可以为其传递一些必要的参数
>
>| 参数类型            | 作用                                                         |
>| ------------------- | ------------------------------------------------------------ |
>| JoinPoint           | 连接点对象，任何通知都可使用，可以获得当前目标对象、目标方法参数等信息 |
>| ProceedingJoinPoint | JoinPoint子类对象，主要是在环绕通知中执行proceed()，进而执行目标方法 |
>| Throwable           | 异常对象，使用在异常通知中，需要在配置文件中指出异常对象名称 |
>
>```xml
><aop:after-throwing method="afterThrowing" pointcut-ref="myPointcut" throwing="th"/>
><!--要指定throwing  th是异常对象参数的名称-->
>```
>
>```java
>public void afterThrowing (JoinPoint joinPoint,Throwable th){
>	//获得异常信息
>	System.out.println("异常对象是:" + th + "异常信息是: " + th.getMessage ());
>}
>```

#### 2.2.4 AOP的配置的两种方式

>AOP的xml有两种配置方式，如下:
>
>* 使用<advisor>配置切面
>* 使用<aspect>配置切面
>
>Spring定义了一个Advice接口，实现了该接口的类都可以作为通知类出现
>
>```java
>public interface Advice {
>}
>public class MyAdvice2 implements MethodBeforeAdvice, AfterReturningAdvice {
>  //MethodBeforeAdvice和AfterReturningAdvice都是Advice的子接口
>  @Override
>  public void before(Method method, Object[] objects, Object o) throws Throwable {
>      System.out.println("前置通知..........");
>  }
>
>  @Override
>  public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
>      System.out.println("后置通知...........");
>  }
>}
>
>//环绕通知
>public class MyAdvice3 implements MethodInterceptor {
>
>  @Override
>  public Object invoke(MethodInvocation methodInvocation) throws Throwable {
>      System.out.println("环绕前******");
>      //执行目标方法
>      Object res = methodInvocation.getMethod().
>          invoke(methodInvocation.getThis(), methodInvocation.getArguments());
>      System.out.println("环绕后******");
>      return res;
>  }
>}
>```
>
>```xml
><aop:config>
>  <aop:pointcut id="myPointCut" expression="execution(void com.hfut.service.impl.UserServiceImpl.show())"/>
>
>  <!--不需要指定前置后置通知，因为通知类myAdvice2实现了Advice接口 实现其中的前置后置方法即可-->
>  <aop:advisor advice-ref="myAdvice2" pointcut-ref="myPointCut" />
></aop:config>
>```
>
>AOP配置的两种语法形式不同点：
>
>语法形式不同:
>
>* advisor是通过实现接口来确认通知的类型
>* aspect是通过配置确认通知的类型，更加灵活
>
>可配置的切面数量不同：
>
>* 一个advisor只能配置一个固定通知和一个切点表达式
>
>* 一个aspect可以配置多个通知和多个切点表达式任意组合
>
>使用场景不同:
>
>* 允许随意搭配情况下可以使用aspect进行配置
>* 如果通知类型单一、切面单一的情况下可以使用advisor进行配置
>* 在通知类型已经固定，不用人为指定通知类型时，可以使用advisor进行配置，例如后面要学习的Spring事务控制的配置，jar包内部已经写好通知(通过实现接口方式，并且通知是固定不变的)了，这时候就需要advisor方式配置。

### 2.3 xml方式AOP原理剖析

>视频讲解
>
>动态代理的实现的选择，在调用getProxy()方法时，我们可选用的AopProxy接口有两个实现类，如上图，这两种都是动态生成代理对象的方式，一种就是基于JDK的，一种是基于Cglib的。
>
>| 代理技术 | 使用条件                                                     | 配置方式                                                     |
>| -------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
>| JDK      | 目标类有接口，是基于接口动态生成实现类的代理对象             | 目标类有接口的情况下，默认方式                               |
>| CGlib    | 目标类无接口且不能使用final修饰，是基于被代理对象动态生成子对象为代理对象 | 标类无接口时，默状认使用该万式。目标类有接口时，手动配置<aop:config proxy-target-class="true">强制使用Cglib方式 |

#### 2.3.1 CGlib动态代理

>```java
>
>public class CGlibTest {
>    public static void main(String[] args) {
>        //CGlib基于父类（目标类）生成Proxy
>
>        //目标对象
>        Target target = new Target();
>        //通知对象（增强对象）
>        MyAdvice4 myAdvice4 = new MyAdvice4();
>
>        //编写CGlib的代码
>        Enhancer enhancer = new Enhancer();
>        //设置父类
>        enhancer.setSuperclass(Target.class);//生成的代理对象就是Target的子类
>        //设置回调
>        enhancer.setCallback(new MethodInterceptor() {
>            @Override
>            //intercept方法相当于JDK的Proxy的invoke方法
>            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
>                myAdvice4.before();//前置
>                Object res = method.invoke(target, objects); //执行目标方法
>                myAdvice4.after();//后置
>                return res;
>            }
>        });
>
>        //生成代理对象
>        Target proxy = (Target) enhancer.create();
>
>        //测试
>        proxy.show();
>
>    }
>
>}
>```

##3. 基于注解配置的AOP

### 3.1注解方式AOP基本使用

>步骤：
>
>1. 目标类(被代理类)加入容器
>2. 通知类加入容器
>3. 增加切面，其中切面类和通知类是同一个
>4. 在配置文件(或配置类)中添加AOP自动代理(开启使用注解配置AOP)
>
>```java
>//增强类，内部提供增强方法
>@Component
>@Aspect //这个MyAdvice类即是切面类又是通知类
>public class MyAdvice {
>
>    //<aop:before method="beforeAdvice" pointcut="execution(* com.itheima.service.impl.*.*(..))"/>
>    @Before("execution(* com.itheima.service.impl.*.*(..))") //切点表达式
>    public void beforeAdvice(JoinPoint joinPoint){
>        System.out.println("前置的增强....");
>    }
>}
>```
>
>```xml
><aop:aspectj-autoproxy />
>```
>
>```java
>@Configuration
>@EnableAspectJAutoProxy  //<aop:aspectj-autoproxy />
>public class SpringConfig {}
>```
>
>

### 3.2 注解方式AOP配置详解

>```java
>@Component
>@Aspect
>public class MyAdvice {
>
>    //切点表达式的抽取
>    @Pointcut("execution(* com.itheima.service.impl.*.*(..))")
>    public void myPointcut(){}
>
>    //<aop:before method="beforeAdvice" pointcut="execution(* com.itheima.service.impl.*.*(..))"/>
>    @Before("execution(* com.itheima.service.impl.*.*(..))")
>    public void beforeAdvice(JoinPoint joinPoint){
>        //System.out.println("当前目标对象是："+joinPoint.getTarget());
>        //System.out.println("表达式："+joinPoint.getStaticPart());
>        System.out.println("前置的增强....");
>    }
>
>    @AfterReturning("execution(* com.itheima.service.impl.*.*(..))")
>    public void afterReturningAdvice(){
>        System.out.println("后置的增强....");
>    }
>
>    @Around("MyAdvice.myPointcut()")
>    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
>        System.out.println("环绕前的增强....");
>        Object res = proceedingJoinPoint.proceed();//执行目标方法
>        System.out.println("环绕后的增强....");
>        return res;
>    }
>
>    @AfterThrowing(pointcut = "execution(* com.itheima.service.impl.*.*(..))",throwing = "e")
>    public void afterThrowingAdvice(Throwable e){
>        System.out.println("当前异常信息是："+e);
>        System.out.println("异常抛出通知...报异常才执行");
>    }
>
>    @After("execution(* com.itheima.service.impl.*.*(..))")
>    public void afterAdvice(){
>        System.out.println("最终的增强....");
>    }
>
>}
>```
>
>

### 3.3 注解方式AOP原理剖析

>视频
>
>![image-20230410160051258](D:\Program Files (x86)\vanyan\Typora\imgs\image-20230410160051258.png)

##4. 基于AOP的声明式事务控制

### 4.1 Spring事务编程概述

>事务是开发中必不可少的东西，使用JDBC开发时，我们使用connnection对事务进行控制，使用MyBatis时，我们使用SqlSession对事务进行控制，缺点显而易见，当我们切换数据库访问技术时，事务控制的方式总会变化，Spring 就将这些技术基础上，提供了统一的控制事务的接口。Spring的事务分为:编程式事务控制和声明式事务。
>
>| 事务控制方式   | 解释                                                         |
>| -------------- | ------------------------------------------------------------ |
>| 编程式事务控制 | Spring提供了事务控制的类和方法，使用编码的方式对业务代码进行事务控制，事务控制代码和业务操作代码耦合到了一起，开发中不使用 |
>| 声明式事务控制 | Spring将事务控制的代码封装，对外提供了xml和注解配置方式，通过配置的方式完成事务的控制，可以达到事务控制与业务操作代码解耦合，开发中推荐使用 |
>
>Spring事务编程相关的类主要有如下三个
>
>| 事务控制相关类                           | 解释                                                         |
>| ---------------------------------------- | ------------------------------------------------------------ |
>| 平台事务管理器PlatformTransactionManager | 是一个接口标准，实现类都具备事务提交、回滚和获得事务对象的功能，不同持久层框架可能会有不同实现方案，mybatis提供的是DataSourceTransactionManager |
>| 事务定义TransactionDefinition            | 封装事务的隔离级别、传播行为、过期时间等属性信息             |
>| 事务状态TransactionStatus                | 存储当前事务的状态信息，如果事务是否提交、是否回滚、是否有回滚点等 |
>
>虽然编程式事务控制我们不学习，但是编程式事务控制对应的这些类我们需要了解一下，因为我们在通过配置的方式进行声明式事务控制时也会看到这些类的影子

### 4.2 搭建测试环境

### 4.3 基于xml声明式事务控制

>结合上面我们学习的AOP的技术，很容易就可以想到，可以使用AOP对Service的方法进行事务的增强。
>
>* 目标类:自定义的AccountServicelmpl，内部的方法是切点
>* 通知类: Spring提供的，通知方法已经定义好，只需要配置即可
>
>步骤：
>
>* 通知类是Spring提供的，需要导入Spring事务的相关的坐标;
>* 配置目标类AccountServicelmpl;
>* 使用advisor标签配置切面。
>
>```xml
><bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
>    <property name="dataSource" ref="dataSource"/>
></bean>
>
><tx:advice id="txAdvice" transaction-manager="transactionManager">
>    <tx:attributes>
>        <tx:method name="show" isolation="REPEATABLE_READ" timeout="3"  />
>        <tx:method name="*"/>
>    </tx:attributes>
></tx:advice>
>
><aop:config>
>    <aop:pointcut id="txPointcut" expression="execution(xxx)"/>
>    <aop:advisor advice-ref="txAdvice" pointcut-ref="txPointcut"/>
></aop:config>
>```
>
>
>
>```xml
><!--配置平台事务管理器 通过这个事务管理器设置事务的一些属性信息-->
>    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
>        <property name="dataSource" ref="dataSource"/>
>    </bean>
>
>    <!--配置Spring提供好的Advice  实际类型是TransactionInterceptor 也就是将这个类对应的对象放到容器中-->
>    <tx:advice id="txAdvice" transaction-manager="transactionManager">
>        <!--
>            通过transactionManager设置事务的属性信息
>            虽然所有的切入点都由一个通知类TransactionInterceptor来通知
>            但处理时每个切入点对应的事务属性可以不同从而产生不同的效果
>        -->
>        <tx:attributes>
>            <!--
>                配置不同方法(切入点)对应的事务的属性 每个方法对应一个
>                name：方法(切入点)名称  * 表示通配符
>                isolation：事务的隔离级别 DEFAULT是默认级别 取决于数据库
>                timeout：超时时间 默认-1 无限时间 单位是秒
>                read-only：是否只读  查询操作可以设置为只读
>                propagation：事务的传播行为，解决业务方法调用业务方法（事务嵌套问题）
>            -->
>            <tx:method name="show" isolation="REPEATABLE_READ" timeout="3" read-only="false" propagation="REQUIRED" />
>            <!--*代表所有方法 不配置属性则属性都为默认值-->
>            <tx:method name="*"/>
>        </tx:attributes>
>    </tx:advice>
>
>    <aop:config>
>        <aop:pointcut id="txPointcut" expression="execution(void com.hfut.service.impl.UserServiceImpl.show())"/>
>        <aop:advisor advice-ref="txAdvice" pointcut-ref="txPointcut"/>
>    </aop:config>
>```
>
>![image-20230410202946798](D:\Program Files (x86)\vanyan\Typora\imgs\image-20230410202946798.png)
>
>其中，name属性名称指定哪个方法要进行哪些事务的属性配置，此处需要区分的是切点表达式指定的方法与此处指定的方法的区别?切点表达式，是过滤哪些方法可以进行事务增强;事务属性信息的name，是指定哪个方法要进行哪些事务属性的配置
>
>![image-20230410195944604](D:\Program Files (x86)\vanyan\Typora\imgs\image-20230410195944604.png)
>
>源码剖析：视频

### 4.4 基于注解声明式事务控制

>```java
>@Transcational(isolation=, propagation=, timeout=xxxxx)//可以加载类上和方法上  方法上的优先级高
>```
>
>```xml
><bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
>    <property name="dataSource" ref="dataSource"/>
></bean>
><!--事务的自动代理(注解驱动)-->
><tx:annotation-driven transaction-manager="transactionManager" />
>```
>
>```java
>//或者用纯注解方式
>@Configuration
>@EnableTransactionManagement //<tx:annotation-driven />   会自动找TransactionManager
>public class SpringConfig {
>    @Bean
>    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
>        //xxx
>    }
>}
>```

# 三、Spring整合web环境

## 1. Javaweb三大组件及环境特点

![image-20230410203500542](D:\Program Files (x86)\vanyan\Typora\imgs\image-20230410203500542.png)

## 2. Spring整合web环境的思路及实现

## 3. Spring的web开发组件spring-web

# 四、web层解决方案-SpringMVC

