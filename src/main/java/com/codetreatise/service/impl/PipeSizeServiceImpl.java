package com.codetreatise.service.impl;

import com.codetreatise.bean.station.PipeSizeEntity;
import com.codetreatise.repository.PipeSizeRepository;
import com.codetreatise.service.PipeSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PipeSizeServiceImpl implements PipeSizeService {
    @Autowired
    PipeSizeRepository repository;
    
    
    @Override
    public PipeSizeEntity save(PipeSizeEntity entity) {
        return repository.save(entity);
    }

    @Override
    public PipeSizeEntity update(PipeSizeEntity entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(PipeSizeEntity entity) {
        repository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);

    }

    @Override
    public void deleteInBatch(List<PipeSizeEntity> entities) {
        repository.deleteInBatch(entities);
    }

    @Override
    public PipeSizeEntity find(Long id) {
        return repository.findOne(id);
    }

    @Override
    public List<PipeSizeEntity> findAll() {
        return repository.findAll();
    }
}
