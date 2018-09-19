package com.codetreatise.bean.unitNumber;

import com.codetreatise.bean.base.BaseEntity;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
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
}
