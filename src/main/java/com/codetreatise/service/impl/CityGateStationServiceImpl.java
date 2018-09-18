package com.codetreatise.service.impl;

import com.codetreatise.bean.station.CityGateStationEntity;
import com.codetreatise.repository.CityGateStationRepository;
import com.codetreatise.service.CityGateStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityGateStationServiceImpl implements CityGateStationService {
    @Autowired
    CityGateStationRepository cityGateStationRepository;
    
    
    @Override
    public CityGateStationEntity save(CityGateStationEntity entity) {
        return cityGateStationRepository.save(entity);
    }

    @Override
    public CityGateStationEntity update(CityGateStationEntity entity) {
        return cityGateStationRepository.save(entity);
    }

    @Override
    public void delete(CityGateStationEntity entity) {
        cityGateStationRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        cityGateStationRepository.delete(id);

    }

    @Override
    public void deleteInBatch(List<CityGateStationEntity> entities) {
        cityGateStationRepository.deleteInBatch(entities);
    }

    @Override
    public CityGateStationEntity find(Long id) {
        return cityGateStationRepository.findOne(id);
    }

    @Override
    public List<CityGateStationEntity> findAll() {
        return cityGateStationRepository.findAll();
    }
}
