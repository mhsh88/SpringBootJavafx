package com.codetreatise.service.impl;

import com.codetreatise.bean.station.BurnersEntity;
import com.codetreatise.repository.BurnerRepository;
import com.codetreatise.service.BurnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BurnerServiceImpl implements BurnerService {
    @Autowired
    BurnerRepository burnerRepository;


    @Override
    public BurnersEntity save(BurnersEntity entity) {
        return burnerRepository.save(entity);
    }

    @Override
    public BurnersEntity update(BurnersEntity entity) {
        return burnerRepository.save(entity);
    }

    @Override
    public void delete(BurnersEntity entity) {
        burnerRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        burnerRepository.delete(id);

    }

    @Override
    public void deleteInBatch(List<BurnersEntity> entities) {
        burnerRepository.deleteInBatch(entities);
    }

    @Override
    public BurnersEntity find(Long id) {
        return burnerRepository.findOne(id);
    }

    @Override
    public List<BurnersEntity> findAll() {
        return burnerRepository.findAll();
    }

    @Override
    public List<BurnersEntity> save(List<BurnersEntity> burners) {
        return burnerRepository.save(burners);
    }
}
