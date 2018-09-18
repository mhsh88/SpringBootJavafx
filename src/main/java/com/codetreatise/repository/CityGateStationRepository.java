package com.codetreatise.repository;

import com.codetreatise.bean.station.CityGateStationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityGateStationRepository extends JpaRepository<CityGateStationEntity, Long> {

}
