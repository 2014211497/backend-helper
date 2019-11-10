package net.engyne.backend.annotation;

import com.alibaba.fastjson.JSONObject;
import net.engyne.backend.wrap.ExecResult;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EasyUpdator {

    private static Map<Class<?>, Set<String>> fieldMap = new HashMap<>();

    private static Set<String> getClassUpdateFields(Class<?> clazz) {
        if (fieldMap.containsKey(clazz)) {
            return fieldMap.get(clazz);
        }
        Field[] fields = clazz.getDeclaredFields();
        Set<String> set = new HashSet<>();
        for (Field field: fields) {
            if (field.isAnnotationPresent(EasyUpdate.class)) {
                set.add(field.getName());
            }
        }
        fieldMap.put(clazz, set);
        return set;
    }

    private static String upperCaseFirstChar(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        }
        return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    public static void doUpdate(Object obj, JSONObject params) throws Exception {
        Class<?> clazz = obj.getClass();
        Set<String> fields = EasyUpdator.getClassUpdateFields(clazz);
        for (String field: fields) {
            if (params.containsKey(field)) {
                String methodName = "set" + EasyUpdator.upperCaseFirstChar(field);
                // 获取参数类型
                Class<?> type = clazz.getDeclaredField(field).getType();
                // 获取setter方法
                Method method = clazz.getMethod(methodName, type);
                // 调用setter方法
                method.invoke(obj, params.get(field));
            }
        }
    }

    public static ExecResult update(Object obj, JSONObject params) {
        try {
            EasyUpdator.doUpdate(obj, params);
            return ExecResult.success();
        } catch (Exception e) {
            return ExecResult.fail(e.getMessage());
        }
    }
}
