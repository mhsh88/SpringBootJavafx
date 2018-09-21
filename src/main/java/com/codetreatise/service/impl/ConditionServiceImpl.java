package com.codetreatise.service.impl;

import com.codetreatise.bean.station.ConditionEntity;
import com.codetreatise.bean.station.SecEntity;
import com.codetreatise.repository.ConditionRepository;
import com.codetreatise.service.ConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConditionServiceImpl implements ConditionService {
    @Autowired
    ConditionRepository conditionRepository;
    
    
    @Override
    public ConditionEntity save(ConditionEntity entity) {
        return conditionRepository.save(entity);
    }

    @Override
    public ConditionEntity update(ConditionEntity entity) {
        return conditionRepository.save(entity);
    }

    @Override
    public void delete(ConditionEntity entity) {
        conditionRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        conditionRepository.delete(id);

    }

    @Override
    public void deleteInBatch(List<ConditionEntity> entities) {
        conditionRepository.deleteInBatch(entities);
    }

    @Override
    public ConditionEntity find(Long id) {
        return conditionRepository.findOne(id);
    }

    @Override
    public List<ConditionEntity> findAll() {
        return conditionRepository.findAll();
    }

    @Override
    public List<ConditionEntity> findBySec(SecEntity secEntity) {
        return conditionRepository.findBySec(secEntity);
    }

    @Override
    public List<ConditionEntity> save(List<ConditionEntity> list) {
        return conditionRepository.save(list);
    }
}
