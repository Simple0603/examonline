package com.duyi.examonline.test;

import com.duyi.examonline.domain.Teacher;
import com.duyi.examonline.service.TeacherService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class Test1 {
    @Autowired
    private TeacherService teacherService;
    @Test
    public void insertTeacher(){
        Teacher teacher = new Teacher();
        teacher.setTname("张三");
        teacher.setPass("123");
        teacherService.insert(teacher);
    }
    @Test
    public void selectTeacher(){
        Teacher teacher = teacherService.selectByName("zhangsan");
        System.out.println(teacher.getTname());
    }
}
