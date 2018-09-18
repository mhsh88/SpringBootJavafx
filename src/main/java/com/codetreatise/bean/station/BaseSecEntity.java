package com.codetreatise.bean.station;

import com.codetreatise.bean.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BaseSecEntity extends BaseEntity {

}
