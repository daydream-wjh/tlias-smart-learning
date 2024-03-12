package com.demo.tlias.mapper;

import com.demo.tlias.pojo.OperateLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wjh
 * @date 2024/3/6 16:54
 */
@Mapper
public interface OperateLogMapper {
    @Insert("insert into operate_log(operate_user, operate_time, class_name, method_name, method_params, return_value, cost_time) VALUES" +
            "(#{operateUser}, #{operateTime}, #{className}, #{methodName}, #{methodParams}, #{returnValue}, #{costTime})")
    public void insert(OperateLog log);
}
