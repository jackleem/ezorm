package com.ezorm.util;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Li Yu on 2017/6/9.
 */
public class OrmUtil {
    public static String getLastEm(String string){
        String[] tmp = string.split("\\.");
        return tmp[tmp.length-1];
    }

    public static void getColsAndValsFromObject(List<String> cols,
                                                List<String> vals,
                                                List<Class> types,
                                                Object obj){
        try {
            for(Field field: obj.getClass().getDeclaredFields()){
                field.setAccessible(true);
                cols.add(OrmUtil.getLastEm(field.toString()));
                vals.add(field.get(obj).toString());
                types.add(field.getType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
