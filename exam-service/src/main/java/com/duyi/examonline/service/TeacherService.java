package com.duyi.examonline.service;

import com.duyi.examonline.domain.Teacher;

public interface TeacherService {
    void insert(Teacher teacher);
    Teacher selectByName(String tname);
}
