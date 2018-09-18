package com.codetreatise.service.impl;

import com.codetreatise.bean.station.FileSecEntity;
import com.codetreatise.repository.FileSecRepository;
import com.codetreatise.service.FileSecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileSecServiceImpl implements FileSecService {
    @Autowired
    FileSecRepository repository;
    
    
    @Override
    public FileSecEntity save(FileSecEntity entity) {
        return repository.save(entity);
    }

    @Override
    public FileSecEntity update(FileSecEntity entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(FileSecEntity entity) {
        repository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);

    }

    @Override
    public void deleteInBatch(List<FileSecEntity> entities) {
        repository.deleteInBatch(entities);
    }

    @Override
    public FileSecEntity find(Long id) {
        return repository.findOne(id);
    }

    @Override
    public List<FileSecEntity> findAll() {
        return repository.findAll();
    }
}
