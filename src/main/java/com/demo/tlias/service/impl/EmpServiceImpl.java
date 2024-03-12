package com.demo.tlias.service.impl;

import com.demo.tlias.mapper.EmpMapper;
import com.demo.tlias.pojo.Emp;
import com.demo.tlias.pojo.PageBean;
import com.demo.tlias.service.EmpService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    /*@Override
    public PageBean page(Integer page, Integer pageSize) {
        Long count = empMapper.count();
        List<Emp> list = empMapper.page((page - 1) * pageSize, pageSize);
        return new PageBean(count, list);
    }*/
    @Override
    public PageBean page(Integer page, Integer pageSize, String name, Short gender, LocalDate begin, LocalDate end) {
        PageHelper.startPage(page, pageSize);
        List<Emp> list = empMapper.list(name,gender, begin, end);
        Page<Emp> plist = (Page<Emp>) list;
        return new PageBean(plist.getTotal(), plist.getResult());
    }

    @Override
    public void delete(List<Integer> ids) {
        empMapper.delete(ids);
    }

    @Override
    public void save(Emp emp) {
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.insert(emp);
    }

    @Override
    public Emp getById(Integer id) {
        return empMapper.getById(id);
    }

    @Override
    public void update(Emp emp) {
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.update(emp);
    }

    @Override
    public Emp login(Emp emp) {
       Emp emp1= empMapper.getByUsernameAndPassword(emp);
        return emp1;
    }


}
