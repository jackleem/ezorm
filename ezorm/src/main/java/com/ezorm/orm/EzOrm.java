package com.ezorm.orm;

import java.util.List;

/**
 * Created by Li Yu on 2017/6/9.
 */
public interface EzOrm<T> {
    boolean save(T entity);
    List<T> getAll();
    T getById(Long id);
    boolean remove(T entity);
    boolean update(T entity);
}
