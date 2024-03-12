package com.demo.tlias.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wjh
 * @date 2024/3/1 10:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageBean {
    private Long total;
    private List rows;
}
