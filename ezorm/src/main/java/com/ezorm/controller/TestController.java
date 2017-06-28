package com.ezorm.controller;

import com.ezorm.dao.PackageDao;
import com.ezorm.dao.UserDao;
import com.ezorm.model.Package;
import com.ezorm.model.User;
import com.ezorm.util.MethodReflectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Li Yu on 2017/6/6.
 */
@RestController
public class TestController {

    @Autowired
    private MethodReflectUtil mru;
    @Autowired
    @Qualifier("userDao")
    private UserDao userDao;
    @Autowired
    @Qualifier("packageDao")
    private PackageDao packageDao;



    @RequestMapping(value = "/exam/ormtest", method = RequestMethod.GET)
    public String testOrm(HttpServletRequest request, Model model){
        //User user = new User();
        //user.setId(0L);
        //user.setName("Test");
        //user.setNationality("Earth");
        //user.setGender("Male");
        //user = userDao.getById(1L);
        //System.out.println(user.toString());
        //userDao.save(user);

        Package pkg = new Package();
        pkg.setPkgId(2L);
        pkg.setPkgName("测试叠加包");
        pkg.setDataLimit(25000L);
        pkg.setMsgLimit(600L);
        pkg.setVoiceLimit(54000L);
        packageDao.save(pkg);
        //pkg = packageDao.getById(1L);
        //System.out.println(pkg.toString());

        return "OK";
    }

    @RequestMapping(value = "/exam/delete", method = RequestMethod.GET)
    public String testOrmDetele(HttpServletRequest request, Model model){
        User user = new User();
        user.setId(0L);
        user.setName("Test");
        user.setNationality("Earth");
        user.setGender("Male");
        System.out.println(user.toString());
        userDao.remove(user);

        //Package pkg = new Package();
        //pkg.setPkgId(2L);
        //pkg.setPkgName("测试叠加包");
        //pkg.setDataLimit(25000L);
        //pkg.setMsgLimit(600L);
        //pkg.setVoiceLimit(54000L);
        //packageDao.save(pkg);
        //pkg = packageDao.getById(1L);
        //System.out.println(pkg.toString());

        return "OK";
    }

    @RequestMapping(value = "/exam/update", method = RequestMethod.GET)
    public String testOrmUpdate(HttpServletRequest request, Model model){
        User user = new User();
        user.setId(2L);
        user.setName("Test");
        user.setNationality("Earth");
        user.setGender("Male");
        System.out.println(user.toString());
        userDao.update(user);

        //Package pkg = new Package();
        //pkg.setPkgId(2L);
        //pkg.setPkgName("测试叠加包");
        //pkg.setDataLimit(25000L);
        //pkg.setMsgLimit(600L);
        //pkg.setVoiceLimit(54000L);
        //packageDao.save(pkg);
        //pkg = packageDao.getById(1L);
        //System.out.println(pkg.toString());

        return "OK";
    }

    @RequestMapping(value = "/exam/getAll", method = RequestMethod.GET)
    public String testOrmSelectAll(HttpServletRequest request, Model model){
        List<User> users = userDao.getAll();
        System.out.println(users.toString());
        List<Package> pkgs = packageDao.getAll();
        System.out.println(pkgs.toString());
        return "OK";
    }
}
