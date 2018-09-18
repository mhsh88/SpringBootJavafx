package com.codetreatise.service.impl;

import com.codetreatise.bean.station.GasEntity;
import com.codetreatise.repository.GasRepository;
import com.codetreatise.service.GasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GasServiceImpl implements GasService {
    @Autowired
    GasRepository repository;
    
    
    @Override
    public GasEntity save(GasEntity entity) {
        return repository.save(entity);
    }

    @Override
    public GasEntity update(GasEntity entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(GasEntity entity) {
        repository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);

    }

    @Override
    public void deleteInBatch(List<GasEntity> entities) {
        repository.deleteInBatch(entities);
    }

    @Override
    public GasEntity find(Long id) {
        return repository.findOne(id);
    }

    @Override
    public List<GasEntity> findAll() {
        return repository.findAll();
    }
}
