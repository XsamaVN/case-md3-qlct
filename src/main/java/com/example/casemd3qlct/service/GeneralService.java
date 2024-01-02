package com.example.casemd3qlct.service;

import java.util.List;

public interface GeneralService<T> {
    List<T> showAll(int id);
    void create(T t);
    void edit(int id, T t);
    void delete(int id);
    T findByid(int id);
    int findIndexById(int id);
}
