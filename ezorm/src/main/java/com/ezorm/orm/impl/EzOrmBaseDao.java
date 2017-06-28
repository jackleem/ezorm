package com.ezorm.orm.impl;

import com.ezorm.orm.EzOrm;
import com.ezorm.util.JDBCUtil;
import com.ezorm.util.MethodReflectUtil;
import com.ezorm.util.OrmUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Li Yu on 2017/6/13.
 */

/**
 * Define an abstract class to get the class type from T
 * @param <T>
 */
public abstract class EzOrmBaseDao<T> implements EzOrm<T> {

    @Autowired
    private MethodReflectUtil methodReflectUtil;

    private Class<T>  entityClass;

    public EzOrmBaseDao(){
        //获取泛型的类型
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
        entityClass = (Class) params[0];
    }

    @Override
    public T getById(Long id) {
        try {
            String tableName = OrmUtil.getLastEm(entityClass.getName());
            //System.out.println("tableName = " + tableName);
            String query = "select * from "+tableName;

            //创建T的一个实例对象
            T object = entityClass.newInstance();
            //获取这个实例对象的成员对象以及对应的值
            MethodReflectUtil mru = new MethodReflectUtil();
            mru.init(object);
            HashMap<String,Object> fields = mru.getFields();

            //遍历查找出带有“id”的主键
            String colParam = "";
            for(String key: fields.keySet()){
                if(key.toLowerCase().endsWith("id")){
                    colParam = OrmUtil.getLastEm(key);
                    //System.out.println("colParam = "+colParam);
                }
            }

            //如果找到了主键
            if(!colParam.isEmpty()){
                //构造SQL
                query += " where "+colParam+" = "+id;
                System.out.println("SELECT: query = "+query);
                //连接JDBC执行SQL语句
                Connection conn = JDBCUtil.getConnection();
                Statement sta = conn.createStatement();
                ResultSet rs = sta.executeQuery(query);
                //对每个成员调用对应的setter函数赋值
                while (rs.next()) {
                    for (String fieldNm : fields.keySet()) {
                        String tmpName = OrmUtil.getLastEm(fieldNm);
                        Object result = rs.getObject(tmpName);
                        mru.setMethodCall("set"+tmpName, result);
                    }
                }
                //关闭资源
                sta.close();
                conn.close();
                return object;
            }else{
                System.out.println("ERROR: no such parameter existed!");
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean remove(T entity) {
        String tableName = OrmUtil.getLastEm(entityClass.getName());
        //System.out.println("tableName = " + tableName);
        String query = "delete from "+tableName+" where ";

        //获取实体对象中所有的属性与对应的值
        methodReflectUtil.init(entity);
        HashMap<String,Object> fields = methodReflectUtil.getFields();

        for(String key : fields.keySet()){
            String colParam = OrmUtil.getLastEm(key);
            //System.out.println("colParam = "+colParam);
            query += colParam + " = \""+fields.get(key)+"\" and ";
        }

        //去掉多余的' and'
        query = query.substring(0,query.length()-4);
        System.out.println("DELETE: query = "+query);

        try{
            Connection conn = JDBCUtil.getConnection();
            Statement sta = conn.createStatement();
            int result = sta.executeUpdate(query);
            sta.close();
            conn.close();
            if(result > 0){
                System.out.println("Delete done successfully!");
                return true;
            }else {
                System.out.println("Delete failed!");
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean update(T entity){
        String tableName = OrmUtil.getLastEm(entityClass.getName());
        System.out.println("tableName = " + tableName);

        String query = "update "+tableName+" set ";

        //获取实体对象中所有的属性与对应的值
        methodReflectUtil.init(entity);
        HashMap<String,Object> fields = methodReflectUtil.getFields();

        for(String key : fields.keySet()){
            if(!key.toLowerCase().endsWith("id")) {
                String colParam = OrmUtil.getLastEm(key);
                System.out.println("colParam = " + colParam);
                query += colParam + " = \"" + fields.get(key) + "\" , ";
            }
        }

        query = query.substring(0,query.length()-2);
        query += " where ";

        for(String key : fields.keySet()){
            if(key.toLowerCase().endsWith("id")) {
                String colParam = OrmUtil.getLastEm(key);
                System.out.println("colParam = " + colParam);
                query += colParam + " = \"" + fields.get(key) + "\" , ";
            }
        }

        query = query.substring(0,query.length()-2);
        System.out.println("UPDATE: query = "+query);

        try{
            Connection conn = JDBCUtil.getConnection();
            Statement sta = conn.createStatement();
            int result = sta.executeUpdate(query);
            sta.close();
            conn.close();
            if(result > 0){
                System.out.println("Update done successfully!");
                return true;
            }else {
                System.out.println("Update failed!");
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean save(T entity){
        String tableName = OrmUtil.getLastEm(entityClass.getName());
        System.out.println("tableName = " + tableName);
        String query = "insert into "+tableName+" (";
        try {
            methodReflectUtil.init(entity);
            HashMap<String, Object> fields = methodReflectUtil.getFields();

            for(String key : fields.keySet()){
                String colParam = OrmUtil.getLastEm(key);
                System.out.println("colParam = "+colParam);
                query += colParam + ",";
            }
            //去掉多余的','
            query = query.substring(0,query.length()-1);
            query += ") values (";

            for(String key : fields.keySet()){
                Object obj = fields.get(key);
                System.out.println("obj = "+obj);
                query += "\""+obj + "\",";
            }

            //去掉多余的','
            query = query.substring(0,query.length()-1);
            query += ")";
            System.out.println("INSERT: query = "+query);

            Connection conn = JDBCUtil.getConnection();
            Statement sta = conn.createStatement();
            int result = sta.executeUpdate(query);
            sta.close();
            conn.close();
            if(result > 0){
                System.out.println("Insert done successfully!");
                return true;
            }else {
                System.out.println("Insert failed!");
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<T> getAll(){
        String tableName = OrmUtil.getLastEm(entityClass.getName());
        //System.out.println("tableName = " + tableName);
        String query = "select * from "+tableName;
        System.out.println("SELECT: query = "+query);
        List<T> entities = new ArrayList<T>();
        try{
            //连接JDBC执行SQL语句
            Connection conn = JDBCUtil.getConnection();
            Statement sta = conn.createStatement();
            ResultSet rs = sta.executeQuery(query);
            //对每个成员调用对应的setter函数赋值
            while (rs.next()) {
                //生成一个临时实例对象
                T tmpObj = entityClass.newInstance();
                //必须对反射工具类进行重新初始化，传入新创建的临时变量
                MethodReflectUtil mru = new MethodReflectUtil();
                mru.init(tmpObj);
                //TODO 获取对象成员只需要运行一次，需要进一步改进
                HashMap<String,Object> fields = mru.getFields();
                for (String fieldNm : fields.keySet()) {
                    String tmpName = OrmUtil.getLastEm(fieldNm);
                    Object value = rs.getObject(tmpName);
                    //调用setter方法给临时对象的成员赋值
                    mru.setMethodCall("set" + tmpName, value);
                }
                //将得到的结果放入结果List中
                entities.add(tmpObj);
            }
            //关闭资源
            sta.close();
            conn.close();

            return entities;

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
