package com.codetreatise.service;

import com.codetreatise.bean.station.HeaterEntity;
import com.codetreatise.generic.GenericService;

import java.util.List;

public interface HeaterService extends GenericService<HeaterEntity> {

    List<HeaterEntity> save(List<HeaterEntity> heaters);
}
