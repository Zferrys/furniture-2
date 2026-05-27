package com.furniture.utils;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class DataUtils {
    public static int parseInt(String strInt, int defaultValue) {
        try {
            return Integer.parseInt(strInt);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static <T> T copyParamToBean(Map values, T bean) {
        try {
            BeanUtils.populate(bean, values);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return bean;
    }
}
