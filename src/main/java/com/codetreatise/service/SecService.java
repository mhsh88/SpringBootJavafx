package com.codetreatise.service;

import com.codetreatise.bean.station.CityGateStationEntity;
import com.codetreatise.bean.station.SecEntity;
import com.codetreatise.generic.GenericService;

import java.util.List;

public interface SecService extends GenericService<SecEntity> {


    List<SecEntity> findByCityGateStation(CityGateStationEntity cityGateStationEntity);
}
