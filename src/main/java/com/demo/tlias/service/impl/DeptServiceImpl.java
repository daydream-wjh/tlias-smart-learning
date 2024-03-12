package com.demo.tlias.service.impl;

import com.demo.tlias.mapper.DeptMapper;
import com.demo.tlias.mapper.EmpMapper;
import com.demo.tlias.pojo.Dept;
import com.demo.tlias.pojo.DeptLog;
import com.demo.tlias.service.DeptLogService;
import com.demo.tlias.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private DeptLogService deptLogService;

    @Override
    public List<Dept> list() {
        return deptMapper.list();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) throws Exception {
        DeptLog deptLog;
        try {
            deptMapper.deleteById(id);
            empMapper.deleteByDeptId(id);
        } finally {
            deptLog = new DeptLog();
            deptLog.setCreateTime(LocalDateTime.now());
            deptLog.setDescription("执行解散了部门" + id);
            deptLogService.insert(deptLog);
        }


    }

    @Override
    public void add(Dept dept) {
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());

        deptMapper.insert(dept);
    }

    @Override
    public Dept getById(Integer id) {

        return deptMapper.getById(id);
    }

    @Override
    public void update(Dept dept) {
        deptMapper.update(dept);
    }
}
