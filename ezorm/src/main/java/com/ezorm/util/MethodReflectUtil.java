package com.ezorm.util;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Created by Li Yu on 2017/6/12.
 */
@Component
public class MethodReflectUtil {
    private Class clazz;
    private Object obj;
    private HashMap<String, Method> getMethods;
    private HashMap<String, Method> setMethods;
    private HashMap<String, Object> fields;

    public void init(Object o){
        obj = o;
        getMethods = new HashMap<String, Method>();
        setMethods = new HashMap<String, Method>();
        fields = new HashMap<String, Object>();
        try {
            Method[] methods = o.getClass().getMethods();
            //get all fields
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                fields.put(field.toString(), field.get(obj));
            }

            //get all setter/getter methods
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                String mName = method.getName();
                mName = mName.toLowerCase();
                if (mName.startsWith("get")) {
                    getMethods.put(mName, method);
                } else if (mName.startsWith("set")) {
                    setMethods.put(mName, method);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setMethodCall(String getMName, Object value){
        //make setter method call
        getMName = getMName.toLowerCase();
        Method method = setMethods.get(getMName);
        if(method != null){
            try{
                method.invoke(obj, value);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public HashMap<String, Object> getFields(){
        return this.fields;
    }
}
