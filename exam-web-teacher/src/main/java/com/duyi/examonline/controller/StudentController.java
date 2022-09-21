package com.duyi.examonline.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.duyi.examonline.domain.Student;
import com.duyi.examonline.domain.Teacher;
import com.duyi.examonline.service.StudentService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @RequestMapping("/student")
    public void toStudent(){
    }

    @RequestMapping("/importTemplate")
    public void toImportTemplate(){
    }

    @RequestMapping(value = "/importStudents", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String importStudents(MultipartFile excel) throws IOException {
        ExcelReader reader = ExcelUtil.getReader(excel.getInputStream());
        reader.addHeaderAlias("学号", "code");
        reader.addHeaderAlias("姓名", "sname");
        List<String> sheetNames = reader.getSheetNames();
        List<Student> allStudents = new ArrayList<>();
        for (int i = 1; i < sheetNames.size(); i++){
            reader.setSheet(sheetNames.get(i));
            List<Student> students = reader.readAll(Student.class);
            String[] studentInfo = sheetNames.get(i).split("-");
            int grade = Integer.parseInt(studentInfo[0]);
            String major = studentInfo[1];
            String classNo = studentInfo[2];
            for (Student student : students){
                student.setGrade(grade);
                student.setMajor(major);
                student.setClassNo(classNo);
            }
            allStudents.addAll(students);
        }
        return studentService.insertAllWithoutTx(allStudents);
    }

    @RequestMapping("/downloadTemplate")
    public ResponseEntity<byte[]> downloadTemplate() throws IOException {
        InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("files/students.xlsx");
        byte[] bs = new byte[is.available()];
        IOUtils.read(is, bs);

        HttpHeaders headers = new HttpHeaders();
        headers.add("content-disposition", "attachment;filename=students.xlsx");
        headers.add("content-type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        return new ResponseEntity<>(bs, headers, HttpStatus.OK);
    }

    public ResponseEntity<byte[]> exportStudents(){
        // start from here, complete exporting students
        return new ResponseEntity<>(new byte[10], new HttpHeaders(), HttpStatus.OK);
    }
}
