package com.codetreatise.repository;

import com.codetreatise.bean.station.FileSecEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileSecRepository extends JpaRepository<FileSecEntity, Long> {

}
