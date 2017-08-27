package com.arkr.hene;

import com.arkr.hekr.model.User;
import com.arkr.hene.data.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

/**
 * Created by hztanhuayou on 2017/8/21
 */

@RestController
//@MapperScan(basePackageClasses = DAOInterfaceMaker.class)
@SpringBootApplication
public class Application {
    @Autowired
    private UserDAO userDAO;


    @RequestMapping("/query")
    public User query(@RequestParam("id") Long id) {
        return userDAO.queryById(id);
    }

    @RequestMapping("/insert")
    public Integer insert(@RequestParam("name") String name) {
        User user = new User();
        user.setAge((long) (10000 * Math.random() % 100));
        user.setCtm(new Date(System.currentTimeMillis()));
        user.setUsername(name);
        return userDAO.insert(user);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
