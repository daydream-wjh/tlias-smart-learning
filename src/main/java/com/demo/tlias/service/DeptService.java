package com.demo.tlias.service;

import com.demo.tlias.pojo.Dept;

import java.util.List;

/**
 * 部门管理
 */
public interface DeptService {

    List<Dept> list();

    void delete(Integer id) throws Exception;

    void add(Dept dept);

    Dept getById(Integer id);

    void update(Dept dept);
}
