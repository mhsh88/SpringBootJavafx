package com.codetreatise.repository;

import com.codetreatise.bean.station.ManualSecEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManualSecRepository extends JpaRepository<ManualSecEntity, Long> {

}
