package com.roger.core.multiple;

import java.util.ArrayList;
import java.util.List;

public class DataSourceContextHolder {
    /**
     *      当使用TreadLocal维护变量时，ThreadLocal为每个使用这个变量的线程
     * 维护一个变量副本
     *      因此每一个线程都能独立的改变自己的副本，而不会影响其他线程的所对应的副本
     */
    private static final ThreadLocal<String> contextHolder = new InheritableThreadLocal<>();

    /**
     *      管理所有的数据源ID，主要了判断数据源是否存在
     */
    public static List<String> dataSourceIds = new ArrayList<String>();

    /**
     *  设置数据源
     * @param db
     */
    public static void setDataSource(String db){
        contextHolder.set(db);
    }

    /**
     * 取得当前数据源
     * @return
     */
    public static String getDataSource(){
        return contextHolder.get();
    }

    /**
     * 清除上下文数据
     */
    public static void clear(){
        contextHolder.remove();
    }


    public static boolean containsDataSource(String dataSourceId){
        return dataSourceIds.contains(dataSourceId);
    }
}
