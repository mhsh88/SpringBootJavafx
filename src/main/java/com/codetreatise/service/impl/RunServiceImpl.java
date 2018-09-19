package com.codetreatise.service.impl;

import com.codetreatise.bean.station.RunEntity;
import com.codetreatise.repository.RunRepository;
import com.codetreatise.service.RunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RunServiceImpl implements RunService {
    @Autowired
    RunRepository repository;
    
    
    @Override
    public RunEntity save(RunEntity entity) {
        return repository.save(entity);
    }

    @Override
    public RunEntity update(RunEntity entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(RunEntity entity) {
        repository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);

    }

    @Override
    public void deleteInBatch(List<RunEntity> entities) {
        repository.deleteInBatch(entities);
    }

    @Override
    public RunEntity find(Long id) {
        return repository.findOne(id);
    }

    @Override
    public List<RunEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public List<RunEntity> save(List<RunEntity> runs) {
        return repository.save(runs);
    }
}
