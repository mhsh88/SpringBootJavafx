package com.codetreatise.bean.unitNumber;

import com.codetreatise.bean.base.BaseEntity;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

@Entity
@Table(name = "pressure_input")
public class Pressure extends BaseEntity implements PressureConstants {


    private double pressure;

    @Size(max = 3)
    @ColumnDefault(value="'"+KPA+"'")
    @Column(name = "unit")
    private String unit;

    public Pressure(){

    }
    public Pressure(double pressure, String unit) {
        super();
        this.pressure = pressure;
        this.unit = unit;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }


    @Transient
    public double getCostumePressure(String unit){
        switch (unit){
            case KPA: return getToBase() / 1;
            case MPA: return getToBase() / 1000;
            case PSI: return getToBase() / 6.89476;
            case KPA_GAUGE: return getToBase() - 101.235;
            case MPA_GAUGE: return (getToBase() - 101.235) / 1000;
            case PSI_GAUGE: return (getToBase() - 101.235) / 6.89476;
            default: return getToBase();
        }

    }
    @Transient
    private double getToBase(){
        switch (unit){
            case KPA: return pressure * 1 ;
            case MPA: return pressure * 1000;
            case PSI: return pressure * 6.89476;
            case KPA_GAUGE: return (pressure + 101.235) *  1 ;
            case MPA_GAUGE: return pressure * 1000 + 101.235;
            case PSI_GAUGE: return pressure * 6.89476 + 101.235;
            default: return pressure;
        }
    }

}
