package com.duyi.examonline.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonController {
    Logger logger = LoggerFactory.getLogger(CommonController.class);

    @RequestMapping({"/common/login.html","/"})
    public String toLogin(){
        System.out.println("请求过来了");
        return "common/login";
    }

//    @RequestMapping("/common/login")
//    public String checkLogin(String tname, String pass){
//        logger.info("tname", tname);
//        logger.info("pass", pass);
//    }
}
