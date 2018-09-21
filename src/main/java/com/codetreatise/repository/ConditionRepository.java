package com.codetreatise.repository;

import com.codetreatise.bean.station.ConditionEntity;
import com.codetreatise.bean.station.SecEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConditionRepository extends JpaRepository<ConditionEntity, Long> {

    List<ConditionEntity> findBySec(SecEntity secEntity);

}
