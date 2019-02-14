package com.roger.core.aspect;

import com.roger.core.annotation.TargetDataSource;
import com.roger.core.multiple.DataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Component
@Aspect
@Order(-10)//保证切面的执行时间在@Transactional之前
public class DynamicDataSourceAspect {

    @Pointcut("execution(* com.roger.biz.service..*.*(..)) || execution(* com.roger.core.service..*.*(..))")
    public void selectDataSource(){

    }

    @After("selectDataSource()")
    public void after(){
        DataSourceContextHolder.clear();
    }

    @Before("selectDataSource()")
    public void before(JoinPoint joinPoint){
        Class<?> targetCls = joinPoint.getTarget().getClass();

        TargetDataSource targetDataSourceAnno = targetCls.getAnnotation(TargetDataSource.class);
        if(targetDataSourceAnno != null){
            DataSourceContextHolder.setDataSource(targetDataSourceAnno.value().name());
            return;
        }
        //获取即将要执行方法
        Method method = getInvokedMethod(targetCls, joinPoint);
        if (method == null) {
            return;
        }
        targetDataSourceAnno = method.getAnnotation(TargetDataSource.class);
        if(targetDataSourceAnno != null){
            DataSourceContextHolder.setDataSource(targetDataSourceAnno.value().name());
        }
    }

    private Method getInvokedMethod(Class targetCls, JoinPoint pJoinPoint) {
        List<Class<? extends Object>> clazzList = new ArrayList<>();
        Object[] args = pJoinPoint.getArgs();
        for (Object arg : args) {
            clazzList.add(arg.getClass());
        }

        Class[] argsCls = (Class[]) clazzList.toArray(new Class[0]);

        String methodName = pJoinPoint.getSignature().getName();
        Method method = null;
        try {
            method = targetCls.getMethod(methodName, argsCls);
        } catch (NoSuchMethodException e) {
            //不做任何处理,这个切面只处理事务相关逻辑
            //其他任何异常不在这个切面的考虑范围
        }
        return method;
    }

}
