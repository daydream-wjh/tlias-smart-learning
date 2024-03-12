package com.demo.tlias.controller;

import com.demo.tlias.anno.Log;
import com.demo.tlias.pojo.Dept;
import com.demo.tlias.pojo.Result;
import com.demo.tlias.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wjh
 * @date 2024/2/22 15:29
 */
@Slf4j
//@Lazy
@RestController
//@Scope("prototype")
@RequestMapping("/depts")
public class DeptController {
    @Autowired
    private DeptService deptService;

    public DeptController(){
        System.out.println("DeptController被实例化");
    }

    @GetMapping
    public Result list() {
        log.info("查询全部部门数据");
        List<Dept> deptList = deptService.list();
        return Result.success(deptList);
    }

    @Log
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) throws Exception {
        log.info("根据id删除部门:{}", id);
        deptService.delete(id);
        return Result.success();
    }

    @Log
    @PostMapping
    public Result add(@RequestBody Dept dept) {
        log.info("新增部门:{}", dept);
        deptService.add(dept);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        log.info("根据id查找对应部门:{}", id);
        Dept byId = deptService.getById(id);
        return Result.success(byId);
    }

    @Log
    @PutMapping
    public Result update(@RequestBody Dept dept) {
        log.info("根据id修改对应部门:{}", dept);
        deptService.update(dept);
        return Result.success();
    }
}
