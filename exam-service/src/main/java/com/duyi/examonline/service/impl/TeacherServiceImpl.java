package com.duyi.examonline.service.impl;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.extra.pinyin.PinyinUtil;
import com.duyi.examonline.dao.TeacherMapper;
import com.duyi.examonline.domain.Teacher;
import com.duyi.examonline.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;

    public void insert(Teacher teacher){
        teacher.setCreateTime(new Date());
        teacher.setMnemonicCode(PinyinUtil.getPinyin(teacher.getTname(),""));
        teacher.setPass(new Digester(DigestAlgorithm.MD5).digestHex(teacher.getPass()));
        teacherMapper.insert(teacher);
    }

    @Override
    public Teacher selectByName(String tname) {
        return teacherMapper.selectByName(tname);
    }
}
