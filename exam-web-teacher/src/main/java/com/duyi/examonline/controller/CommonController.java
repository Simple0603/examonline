package com.duyi.examonline.controller;

import cn.hutool.crypto.digest.DigestUtil;
import com.duyi.examonline.domain.Teacher;
import com.duyi.examonline.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class CommonController {
    Logger logger = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private TeacherService teacherService;

    @RequestMapping({"/common/login.html","/"})
    public String toLogin(){
        System.out.println("请求过来了");
        return "common/login";
    }

    @RequestMapping("/common/login")
    @ResponseBody   // 第一次写这个方法忘了加这个注解，导致返回值无法被正常给回js，然后不知道哪里出了问题，开始怀疑人生
    public boolean checkLogin(String tname, String pass, HttpSession session){
        logger.info(tname + "---" + pass);
        Teacher teacher = teacherService.selectByName(tname);
        if (teacher == null){
            logger.info("用户" + tname + "不存在");
            return false;
        }
        pass = DigestUtil.md5Hex(pass);
        if (!pass.equals(teacher.getPass())){
            logger.info("用户" + tname + "的密码不正确");
            return false;
        }
        logger.info("login success");
        session.setAttribute("loginTeacher", teacher);
        return true;
    }

    @RequestMapping("/common/main.html")
    public String toMain(){
        return "common/main.html";
    }

    @RequestMapping("/common/exit")
    public String exit(HttpSession session){
        session.removeAttribute("loginTeacher");
        return "common/login";
    }

    @RequestMapping("/common/timeout.html")
    public String toTimeout(){
        return "common/timeout";
    }

    @RequestMapping("/common/updatePwdTemplate.html")
    public String toUpdatePwdTemplate(){
        return "common/updatePwdTemplate";
    }

    @RequestMapping("/common/updatePwd")
    @ResponseBody
    public boolean updatePwd(String oldpass, String newpass, HttpSession session){
        oldpass = DigestUtil.md5Hex(oldpass);
        newpass = DigestUtil.md5Hex(newpass);
        Teacher teacher = (Teacher) session.getAttribute("loginTeacher");
        if (!oldpass.equals(teacher.getPass())){
            return false;
        }
        long id = teacher.getId();
        teacherService.updatePwdById(id, newpass);
        return true;
    }
}
