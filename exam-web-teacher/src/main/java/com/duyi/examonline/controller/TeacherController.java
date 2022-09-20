package com.duyi.examonline.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.duyi.examonline.common.CommonData;
import com.duyi.examonline.domain.Teacher;
import com.duyi.examonline.domain.vo.PageVO;
import com.duyi.examonline.service.TeacherService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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

    @RequestMapping("/teacher/importTemplate")
    public void toImportTemplate(){
    }

    @RequestMapping("/teacher/downloadTemplate")
    public ResponseEntity<byte[]> downloadTemplate() throws IOException {
        InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("files/teachers.xlsx");
        byte[] bs = new byte[is.available()];
        IOUtils.read(is, bs);

        HttpHeaders headers =new HttpHeaders();
        headers.add("content-disposition", "attachment;filename=teachers.xlsx");
        headers.add("content-type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        return new ResponseEntity<>(bs, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/teacher/importTeachers", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String importTeachers(MultipartFile excel) throws IOException {
        // 此处excel没传过来，与源码对照一下看是什么原因导致的
        ExcelReader reader = ExcelUtil.getReader(excel.getInputStream());
        reader.addHeaderAlias("教师名称", "tname");
        List<Teacher> teachers = reader.read(0, 1, Teacher.class);
        return teacherService.insertAllWithoutTx(teachers);
    }
}
