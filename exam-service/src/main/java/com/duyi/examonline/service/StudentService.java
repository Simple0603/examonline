package com.duyi.examonline.service;

import com.duyi.examonline.domain.Student;
import com.duyi.examonline.domain.Teacher;

import java.util.List;

public interface StudentService {
    String insertAllWithoutTx(List<Student> students);
}
