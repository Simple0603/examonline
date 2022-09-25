package com.duyi.examonline.service.impl;

import com.duyi.examonline.dao.DictionaryMapper;
import com.duyi.examonline.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DictionaryServiceImpl implements DictionaryService {
    @Autowired
    private DictionaryMapper dictionaryMapper ;

    @Override
    public List<String> findMajors() {
        return dictionaryMapper.findMajors();
    }
}
