package com.roger.core.service.impl;

import com.roger.core.dao.GeneralDao;
import com.roger.core.db.GeneralOgnlParam;
import com.roger.core.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class CommonServiceImpl<T> implements CommonService<T> {

    @Autowired
    protected GeneralDao generalDao;

    @Override
    public <T> T selectByPrimaryKey(Class<T> clazz, Map<String, Object> paramMap) {
        return generalDao.selectByPrimaryKey(clazz, paramMap);
    }

    @Override
    public <T> List<T> selectAdvanced(Class<T> clazz, GeneralOgnlParam generalOgnlParam) {
        return generalDao.selectAdvanced(clazz, generalOgnlParam);
    }

    @Transactional
    @Override
    public <T> int insert(T target) {
        return generalDao.insert(target);
    }

    @Transactional
    @Override
    public <T> int batchInsert(List<T> targetList) {
        return generalDao.batchInsert(targetList);
    }

    @Transactional
    @Override
    public int insert(Map<String, Object> paramMap) {
        return generalDao.insert(paramMap);
    }

    @Transactional
    @Override
    public <T> int update(T target, GeneralOgnlParam generalOgnlParam) {
        return generalDao.update(target, generalOgnlParam);
    }

    @Transactional
    @Override
    public <T> int deleteByPrimaryKey(Class<T> clazz, Map<String, Object> paramMap) {
        return generalDao.deleteByPrimaryKey(clazz, paramMap);
    }

    @Transactional
    @Override
    public <T> int deleteAdvanced(Class<T> clazz, GeneralOgnlParam generalOgnlParam) {
        return generalDao.deleteAdvanced(clazz, generalOgnlParam);
    }
}
