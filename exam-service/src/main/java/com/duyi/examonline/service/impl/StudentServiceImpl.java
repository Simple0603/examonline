package com.duyi.examonline.service.impl;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.extra.pinyin.PinyinUtil;
import com.duyi.examonline.common.CommonData;
import com.duyi.examonline.dao.StudentMapper;
import com.duyi.examonline.domain.Student;
import com.duyi.examonline.domain.Teacher;
import com.duyi.examonline.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import java.util.Date;
import java.util.List;

public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    public void insert(Student student){
        student.setCreateTime(new Date());
        student.setMnemonicCode(PinyinUtil.getPinyin(student.getSname(),""));
        student.setPass(new Digester(DigestAlgorithm.MD5).digestHex(student.getPass()));
        try{
            studentMapper.insert(student);
        } catch(DuplicateKeyException e){
            throw e;
        }
    }

    @Override
    public String insertAllWithoutTx(List<Student> students) {
        String msg = "";
        int success = 0;
        int fail = 0;
        for (Student student : students){
            student.setPass(CommonData.DEFAULT_PASS);
            try {
                this.insert(student);
                success++;
            } catch (DuplicateKeyException e){
                msg += "【"+student.getSname()+"】记录，因为名称重复导致失败|";
                fail++;
            }
        }
        msg = "共有记录【"+students.size()+"】条|" + "成功导入记录【"+success+"】条|" + "失败导入记录【"+fail+"】条|" + msg;
        return msg;
    }
}
