package com.demo.tlias.controller;

import com.demo.tlias.mapper.EmpMapper;
import com.demo.tlias.pojo.Emp;
import com.demo.tlias.pojo.Result;
import com.demo.tlias.service.EmpService;
import com.demo.tlias.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author wjh
 * @date 2024/3/2 19:29
 */
@Slf4j
@RestController
public class LoginController {
    @Autowired
    EmpService empService;

    @PostMapping("/login")
    public Result login(@RequestBody Emp emp) {
        Emp emp1 = empService.login(emp);
        if (emp1 != null) {
            HashMap<String, Object> claims = new HashMap<>();
            claims.put("id", emp1.getId());
            claims.put("name", emp1.getName());
            claims.put("username", emp1.getUsername());
            String jwt = JwtUtils.genJwt(claims);
            return Result.success(jwt);
        }
        return Result.error("用户名或密码错误了");
    }
}
