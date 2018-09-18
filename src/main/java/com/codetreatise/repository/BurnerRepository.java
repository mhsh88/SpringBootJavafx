package com.codetreatise.repository;

import com.codetreatise.bean.station.BurnersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BurnerRepository extends JpaRepository<BurnersEntity, Long> {

}
