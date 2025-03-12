package com.umss.be_gestor.service;

import java.util.List;

public interface ICRUD<T,ID> {

    List<T> findAll() throws Exception;
    T findById(ID id) throws Exception;
    T save(T t) throws Exception;
    T update(T t, ID id) throws Exception;
    void deleteById(ID id) throws Exception;
    
}
