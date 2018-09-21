package com.codetreatise.service;

import com.codetreatise.bean.station.ConditionEntity;
import com.codetreatise.bean.station.SecEntity;
import com.codetreatise.generic.GenericService;

import java.util.List;

public interface ConditionService extends GenericService<ConditionEntity> {

    List<ConditionEntity> findBySec(SecEntity secEntity);

    List<ConditionEntity> save(List<ConditionEntity> list);
}
