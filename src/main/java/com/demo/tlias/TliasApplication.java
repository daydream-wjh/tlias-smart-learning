package com.demo.tlias;

import com.example.*;
import jakarta.servlet.annotation.WebServlet;
import org.dom4j.io.SAXReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ServletComponentScan
@EnableHeaderConfig
public class TliasApplication {

    public static void main(String[] args) {
        SpringApplication.run(TliasApplication.class, args);
    }

}
