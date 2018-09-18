package com.codetreatise.service.impl;

import com.codetreatise.bean.station.ManualSecEntity;
import com.codetreatise.repository.ManualSecRepository;
import com.codetreatise.service.ManualSecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManualSecServiceImpl implements ManualSecService {
    @Autowired
    ManualSecRepository repository;
    
    
    @Override
    public ManualSecEntity save(ManualSecEntity entity) {
        return repository.save(entity);
    }

    @Override
    public ManualSecEntity update(ManualSecEntity entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(ManualSecEntity entity) {
        repository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);

    }

    @Override
    public void deleteInBatch(List<ManualSecEntity> entities) {
        repository.deleteInBatch(entities);
    }

    @Override
    public ManualSecEntity find(Long id) {
        return repository.findOne(id);
    }

    @Override
    public List<ManualSecEntity> findAll() {
        return repository.findAll();
    }
}
