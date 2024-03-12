package com.demo.tlias.service.impl;

import com.demo.tlias.mapper.DeptLogMapper;
import com.demo.tlias.pojo.DeptLog;
import com.demo.tlias.service.DeptLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wjh
 * @date 2024/3/4 20:41
 */
@Service
public class DeptLogServiceImpl implements DeptLogService {
    @Autowired
    DeptLogMapper deptLogMapper;
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insert(DeptLog deptLog) {
        deptLogMapper.insert(deptLog);
    }
}
