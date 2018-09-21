package com.codetreatise.service.impl;

import com.codetreatise.bean.station.CityGateStationEntity;
import com.codetreatise.bean.station.SecEntity;
import com.codetreatise.repository.SecRepository;
import com.codetreatise.service.SecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service/*("fileSecServiceImpl")*/
public class SecServiceImpl implements SecService {
    @Autowired
    SecRepository repository;

    @Override
    public List<SecEntity> findByCityGateStation(CityGateStationEntity cityGateStationEntity) {
        return repository.findByStation(cityGateStationEntity);
    }

    @Override
    public SecEntity save(SecEntity entity) {
        return repository.save(entity);
    }

    @Override
    public SecEntity update(SecEntity entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(SecEntity entity) {
        repository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }

    @Override
    public void deleteInBatch(List<SecEntity> entities) {
        repository.deleteInBatch(entities);
    }

    @Override
    public SecEntity find(Long id) {
        return repository.findOne(id);
    }

    @Override
    public List<SecEntity> findAll() {
        return repository.findAll();
    }
}
