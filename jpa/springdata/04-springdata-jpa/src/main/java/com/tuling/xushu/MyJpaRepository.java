package com.tuling.xushu;

import javax.persistence.EntityManager;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
public class MyJpaRepository  implements InvocationHandler {


    EntityManager em;
    Class pojoClass;

    public MyJpaRepository(EntityManager em, Class pojoClass) {
        this.em = em;
        this.pojoClass = pojoClass;
    }

    // method 当前调用的方法   = findById
    // args 当前调用方法的参数  = 1
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // jpa统一实现类
        MyJpaProxy myJpaProxy = new MyJpaProxy(em, pojoClass);

        Method jpaMethod = myJpaProxy.getClass().getMethod(method.getName(), method.getParameterTypes());

        return jpaMethod.invoke(myJpaProxy,args);
    }
}
