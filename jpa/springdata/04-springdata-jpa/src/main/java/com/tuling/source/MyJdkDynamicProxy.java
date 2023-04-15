package com.tuling.source;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
public class MyJdkDynamicProxy implements InvocationHandler {
    // 目标对象——即MySimpleJpaRepository实例
    Object targetObject;

    public MyJdkDynamicProxy(Object targetObject) {
        this.targetObject = targetObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Method targetMethod = targetObject.getClass().getMethod(method.getName(), method.getParameterTypes());
        return targetMethod.invoke(targetObject,args);
    }
}
