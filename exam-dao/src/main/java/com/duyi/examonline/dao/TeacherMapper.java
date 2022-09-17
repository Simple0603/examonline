package com.duyi.examonline.dao;

import com.duyi.examonline.domain.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeacherMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Teacher record);

    int insertSelective(Teacher record);

    Teacher selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Teacher record);

    int updateByPrimaryKey(Teacher record);

    Teacher selectByName(String tname);

    void updatePwdById(@Param("id") long id, @Param("pass") String pass);

    long total(String tname);

    List<Teacher> find(@Param("start") int start, @Param("end") int end, @Param("tname") String tname);
}