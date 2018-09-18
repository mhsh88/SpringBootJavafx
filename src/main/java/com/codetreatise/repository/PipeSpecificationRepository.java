package com.codetreatise.repository;

import com.codetreatise.bean.station.PipeSpecificationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PipeSpecificationRepository extends JpaRepository<PipeSpecificationsEntity, Long> {

}
