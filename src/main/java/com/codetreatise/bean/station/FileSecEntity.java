package com.codetreatise.bean.station;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "file_sec")
public class FileSecEntity extends BaseSecEntity {
    @Lob
    @Column(name="file")
    private byte[] excelFile;


}
