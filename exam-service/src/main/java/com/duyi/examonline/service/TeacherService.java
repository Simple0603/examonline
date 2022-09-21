package com.duyi.examonline.service;

import com.duyi.examonline.domain.Teacher;
import com.duyi.examonline.domain.vo.PageVO;

import java.util.List;

public interface TeacherService {
    void insert(Teacher teacher);
    Teacher selectByName(String tname);
    void updatePwdById(Long id, String pass);
    PageVO find(int curr, int rows, String tname);
    Teacher selectById(long id);
    boolean updateNameById(Long id, String tname);
    void deleteAll(String ids);
    String insertAllWithoutTx(List<Teacher> teachers);
    List<Teacher> findAll();
}
