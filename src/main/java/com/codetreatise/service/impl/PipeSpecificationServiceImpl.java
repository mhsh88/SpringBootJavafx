package com.codetreatise.service.impl;

import com.codetreatise.bean.station.PipeSpecificationsEntity;
import com.codetreatise.repository.PipeSpecificationRepository;
import com.codetreatise.service.PipeSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PipeSpecificationServiceImpl implements PipeSpecificationService {
    @Autowired
    PipeSpecificationRepository repository;
    
    
    @Override
    public PipeSpecificationsEntity save(PipeSpecificationsEntity entity) {
        return repository.save(entity);
    }

    @Override
    public PipeSpecificationsEntity update(PipeSpecificationsEntity entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(PipeSpecificationsEntity entity) {
        repository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);

    }

    @Override
    public void deleteInBatch(List<PipeSpecificationsEntity> entities) {
        repository.deleteInBatch(entities);
    }

    @Override
    public PipeSpecificationsEntity find(Long id) {
        return repository.findOne(id);
    }

    @Override
    public List<PipeSpecificationsEntity> findAll() {
        return repository.findAll();
    }
}
