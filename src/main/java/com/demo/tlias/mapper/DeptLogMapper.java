package com.demo.tlias.mapper;

import com.demo.tlias.pojo.DeptLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wjh
 * @date 2024/3/4 20:44
 */
@Mapper
public interface DeptLogMapper {
    @Insert("insert into dept_log(create_time, description) VALUES (#{createTime},#{description})")
    void insert(DeptLog deptLog);
}
