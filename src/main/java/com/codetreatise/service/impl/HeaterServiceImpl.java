package com.codetreatise.service.impl;

import com.codetreatise.bean.station.HeaterEntity;
import com.codetreatise.repository.HeaterRepository;
import com.codetreatise.service.HeaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeaterServiceImpl implements HeaterService {
    @Autowired
    HeaterRepository repository;
    
    
    @Override
    public HeaterEntity save(HeaterEntity entity) {
        return repository.save(entity);
    }

    @Override
    public HeaterEntity update(HeaterEntity entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(HeaterEntity entity) {
        repository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);

    }

    @Override
    public void deleteInBatch(List<HeaterEntity> entities) {
        repository.deleteInBatch(entities);
    }

    @Override
    public HeaterEntity find(Long id) {
        return repository.findOne(id);
    }

    @Override
    public List<HeaterEntity> findAll() {
        return repository.findAll();
    }
}
