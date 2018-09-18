package com.codetreatise.repository;

import com.codetreatise.bean.station.RunEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RunRepository extends JpaRepository<RunEntity, Long> {

}
