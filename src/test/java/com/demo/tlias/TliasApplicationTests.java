package com.demo.tlias;

import com.demo.tlias.controller.DeptController;
import com.example.HeaderConfig;
import com.example.HeaderGenerator;
import com.example.HeaderParser;
import com.example.TokenParser;
import com.google.gson.Gson;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class TliasApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private SAXReader saxReader;


    @Test
    public void Bean() {

        for (int i = 0; i < 10; i++) {
            Object bean2 = applicationContext.getBean(DeptController.class);
            System.out.println(bean2);
        }

    }

    @Test

    public void thirdBean() throws DocumentException {
        Document read = saxReader.read(this.getClass().getClassLoader().getResource("1.xml"));
        Element rootElement = read.getRootElement();
        String name = rootElement.element("name").getText();
        String age = rootElement.element("age").getText();
        System.out.println(name + " " + age);

    }

    @Test
    public void testGenBean() {
        Object bean = applicationContext.getBean("saxReader");
        System.out.println(bean);
    }

    @Autowired
    private Gson gson;

    @Test
    public void testJson() {
        System.out.println(applicationContext.getBean(HeaderParser.class));
    }
}
