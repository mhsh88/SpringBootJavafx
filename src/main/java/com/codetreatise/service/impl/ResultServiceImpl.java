package com.codetreatise.service.impl;

import com.codetreatise.bean.station.ResultEntity;
import com.codetreatise.repository.ResultRepository;
import com.codetreatise.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {
    @Autowired
    ResultRepository repository;
    
    
    @Override
    public ResultEntity save(ResultEntity entity) {
        return repository.save(entity);
    }

    @Override
    public ResultEntity update(ResultEntity entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(ResultEntity entity) {
        repository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);

    }

    @Override
    public void deleteInBatch(List<ResultEntity> entities) {
        repository.deleteInBatch(entities);
    }

    @Override
    public ResultEntity find(Long id) {
        return repository.findOne(id);
    }

    @Override
    public List<ResultEntity> findAll() {
        return repository.findAll();
    }
}
