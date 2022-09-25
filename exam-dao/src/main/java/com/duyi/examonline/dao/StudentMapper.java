package com.duyi.examonline.dao;

import com.duyi.examonline.domain.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StudentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);

    /**
     * 暂时没有班级相关的实体，使用map存储
     * @param condition
     * @return {班级名称-className ， 班级人数-total}
     *      班级名称格式：2021-软件-1班
     */
    List<Map> findClasses(Map condition) ;

    List<Student> findStudents(Map condition) ;

    void deleteByClasses(String classNames) ;

    void deleteStudents(String ids) ;

    List<Student> findStudentsByClasses(Map condition) ;
}