package com.codetreatise.repository;

import com.codetreatise.bean.station.PipeSizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PipeSizeRepository extends JpaRepository<PipeSizeEntity, Long> {

}
