package com.duyi.examonline.controller;

import com.duyi.examonline.common.CommonData;
import com.duyi.examonline.domain.Teacher;
import com.duyi.examonline.domain.vo.PageVO;
import com.duyi.examonline.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @RequestMapping("/teacher/teacher.html")
    public String toTeacher(Model model){
        PageVO pageVO = teacherService.find(CommonData.DEFAULT_PAGE, CommonData.DEFAULT_ROWS, null);
        model.addAttribute("pageVO", pageVO);
        return "teacher/teacher";
    }

    @RequestMapping("/teacher/tableTemplate.html")
    public String toTableTemplate(Model model, int curr, String tname){
        PageVO pageVO = teacherService.find(curr, CommonData.DEFAULT_ROWS, tname);
        model.addAttribute("pageVO", pageVO);
        return "teacher/teacher::#tableTemplate";
    }

    @RequestMapping("/teacher/formTemplate.html")
    public String toformTemplate(Long id, Model model){
        if (id != null) {
            Teacher teacher = teacherService.selectById(id);
            model.addAttribute("teacher", teacher);
        }
        return "teacher/formTemplate";
    }

    @RequestMapping("/teacher/save")
    @ResponseBody
    public boolean save(String tname){
        Teacher teacher = new Teacher();
        teacher.setTname(tname);
        teacher.setPass(CommonData.DEFAULT_PASS);
        try {
            teacherService.insert(teacher);
            return true;
        } catch (DuplicateKeyException e){
            return false;
        }
    }

    @RequestMapping("/teacher/edit")
    @ResponseBody
    public boolean edit(Long id, String tname){
        return teacherService.updateNameById(id, tname);
    }

    @RequestMapping("/teacher/deleteAll")
    @ResponseBody
    public void deleteAll(String ids){
        teacherService.deleteAll(ids);
    }
}
