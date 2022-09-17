package com.duyi.examonline.service.impl;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.extra.pinyin.PinyinUtil;
import com.duyi.examonline.dao.TeacherMapper;
import com.duyi.examonline.domain.Teacher;
import com.duyi.examonline.domain.vo.PageVO;
import com.duyi.examonline.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;

    public void insert(Teacher teacher){
        teacher.setCreateTime(new Date());
        teacher.setMnemonicCode(PinyinUtil.getPinyin(teacher.getTname(),""));
        teacher.setPass(new Digester(DigestAlgorithm.MD5).digestHex(teacher.getPass()));
        try{
            teacherMapper.insert(teacher);
        } catch(DuplicateKeyException e){
            throw e;
        }
    }

    @Override
    public Teacher selectByName(String tname) {
        return teacherMapper.selectByName(tname);
    }

    @Override
    public void updatePwdById(Long id, String pass) {
        teacherMapper.updatePwdById(id, pass);
    }

    @Override
    public PageVO find(int curr, int rows, String tname) {
        if (curr < 1){
            curr = 1;
        }
        long total = teacherMapper.total(tname);
        int start = (curr-1) * rows;
        int end = start + rows;
        int max = (int) ((total%rows==0) ? (total/rows) : (total/rows+1));
        if (max < 1){
            max = 1;
        }
        Map<String, Object> condition = new HashMap<>();
        condition.put("tname", tname);
        List<Teacher> teachers = teacherMapper.find(start, end, tname);
        return new PageVO(curr, rows, total, max, start, end, teachers, condition);
    }

    @Override
    public Teacher selectById(long id) {
        return teacherMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateNameById(Long id, String tname) {
        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacher.setTname(tname);
        teacher.setUpdateTime(new Date());
        teacher.setMnemonicCode(PinyinUtil.getPinyin(tname, ""));
        try {
            teacherMapper.updateByPrimaryKeySelective(teacher);
            return true;
        }catch (DuplicateKeyException e){
            return false;
        }
    }
}
