package com.codetreatise.repository;

import com.codetreatise.bean.station.CityGateStationEntity;
import com.codetreatise.bean.station.SecEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecRepository extends JpaRepository<SecEntity, Long> {

    List<SecEntity> findByStation(CityGateStationEntity cityGateStationEntity);
}
