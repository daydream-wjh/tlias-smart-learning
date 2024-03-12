package com.demo.tlias.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author wjh
 * @date 2024/3/6 16:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperateLog {
    private Integer id;
    private Integer operateUser;
    private LocalDateTime operateTime;
    private String className;
    private String methodName;
    private String methodParams;
    private String returnValue;
    private Long costTime;

}
