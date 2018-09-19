package com.codetreatise.service;

import com.codetreatise.bean.station.BurnersEntity;
import com.codetreatise.generic.GenericService;

import java.util.List;

public interface BurnerService extends GenericService<BurnersEntity> {

    List<BurnersEntity> save(List<BurnersEntity> burners);
}
