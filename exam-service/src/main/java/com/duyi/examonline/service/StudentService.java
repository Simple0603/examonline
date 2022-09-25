package com.duyi.examonline.service;

import com.duyi.examonline.domain.Student;
import com.duyi.examonline.domain.Teacher;
import com.duyi.examonline.domain.vo.PageVO;

import java.util.List;
import java.util.Map;

public interface StudentService {
    String insertAllWithoutTx(List<Student> students);
    PageVO findClasses(int pageNo, Map condition);
    List<Student> findStudents(Map condition);
}
