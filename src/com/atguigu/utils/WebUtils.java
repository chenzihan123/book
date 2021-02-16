package com.atguigu.utils;

import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class WebUtils {
    public static <T> T copyParamToBean(Map value, T bean){
        try {
            System.out.println("注入之前：" + bean);
            /**
             * 把所有请求的参数都注入到user对象中
             */
            BeanUtils.populate(bean,value);

            System.out.println("注入之后：" + bean);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return bean;
    }

    /**
     * 将字符串转换成int类型是数据
     * @param strInt 字符串
     * @param defaultValue int类型数据，转换失败返回默认值
     * @return
     */
    public static int parseInt(String strInt,int defaultValue){
        try {
            if (strInt != null){
                return Integer.parseInt(strInt);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }
}
