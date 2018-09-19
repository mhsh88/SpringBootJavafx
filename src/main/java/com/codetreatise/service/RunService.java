package com.codetreatise.service;

import com.codetreatise.bean.station.RunEntity;
import com.codetreatise.generic.GenericService;

import java.util.List;

public interface RunService extends GenericService<RunEntity> {

    List<RunEntity> save(List<RunEntity> runs);
}
