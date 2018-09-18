package com.codetreatise.repository;

import com.codetreatise.bean.station.GasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GasRepository extends JpaRepository<GasEntity, Long> {

}
