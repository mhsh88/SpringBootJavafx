package com.codetreatise.bean.unitNumber;

import com.codetreatise.bean.base.BaseEntity;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

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

    @Transient
    public double getCostumeDebi(String unit){
        switch (unit){
            case M3_PER_HOUR: return getToBase();
            case M3_PER_DAY: return getToBase() * 24;
            case M3_PER_MONTH: return getToBase() * 24 * 30;
            case M3_PER_YEAR: return getToBase() * 24 * 30 * 365;
            default: return getToBase();
        }

    }
    @Transient
    private double getToBase(){
        switch (unit){
            case M3_PER_HOUR: return debi / 1 ;
            case M3_PER_DAY: return debi / 24;
            case M3_PER_MONTH: return debi / 24 / 30;
            case M3_PER_YEAR: return debi / 24 / 30 / 365;
            default: return debi;
        }
    }
}
