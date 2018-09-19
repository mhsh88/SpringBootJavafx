package com.codetreatise.bean.unitNumber;

import com.codetreatise.bean.base.BaseEntity;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "debi_input")
public class Debi extends BaseEntity implements DebiConstants {


    private double debi;


    @ColumnDefault(value="'"+M3_PER_HOUR+"'")
    @Column(name = "unit")
    private String unit;

    public Debi(){

    }
    public Debi(double parseDouble, String m3PerHour) {
        super();
        this.debi = parseDouble;
        this.unit = m3PerHour;
    }

    public double getDebi() {
        return debi;
    }

    public void setDebi(double debi) {
        this.debi = debi;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
