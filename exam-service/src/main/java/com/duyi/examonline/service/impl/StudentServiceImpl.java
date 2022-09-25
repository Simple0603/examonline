package com.duyi.examonline.service.impl;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.extra.pinyin.PinyinUtil;
import com.duyi.examonline.common.CommonData;
import com.duyi.examonline.dao.StudentMapper;
import com.duyi.examonline.domain.Student;
import com.duyi.examonline.domain.Teacher;
import com.duyi.examonline.domain.vo.PageVO;
import com.duyi.examonline.service.StudentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Override
    public PageVO findClasses(int pageNo, Map condition) {
        PageHelper.startPage(pageNo, CommonData.DEFAULT_ROWS);
        List<Map> classes = studentMapper.findClasses(condition);
        PageInfo info = new PageInfo(classes) ;
        //将插件提供的PageInfo对象，组成我们之前更习惯使用的PageVO

        return new PageVO(
                info.getPageNum(),
                info.getPageSize(),
                info.getTotal(),
                info.getNavigateLastPage(),
                (int)info.getStartRow(),
                (int)info.getEndRow(),
                info.getList(),
                condition
        ) ;

    }

    public List<Student> findStudents(Map condition){
        return studentMapper.findStudents(condition);
    }
}
