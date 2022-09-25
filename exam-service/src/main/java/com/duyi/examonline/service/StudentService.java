package com.duyi.examonline.service;

import com.duyi.examonline.domain.Student;
import com.duyi.examonline.domain.Teacher;
import com.duyi.examonline.domain.vo.PageVO;

import java.util.List;
import java.util.Map;

public interface StudentService {
    void insert(Student student);
    String insertAllWithoutTx(List<Student> students);
    PageVO findClasses(int pageNo, Map condition);
    List<Student> findStudents(Map condition);
    void update(Student student);
    Student findStudentById(Long id);
    void deleteByClasses(String classNames);
    void deleteStudent(Long id);
    void deleteStudents(String ids);
    List<Student> findStudentsByClasses(Map condition);
}
