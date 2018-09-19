package com.codetreatise.bean.unitNumber;

import com.codetreatise.bean.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "temperature_input")
public class Temperature extends BaseEntity implements TemperatureConstants {


    private double temperature;

    @Size(max = 2)
//    @Column(name = "unit", columnDefinition = "DEFAULT varchar(2) '"+ CELSIUS +"'")
    private String unit;

    public Temperature(){

    }
    public Temperature(double temperature, String unit) {
        super();
        this.temperature = temperature;
        this.unit = unit;
    }

//    @OneToMany(mappedBy = "envTemperature", cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY)
//    private ConditionEntity conditionEntity;
//
//    @OneToMany(mappedBy = "inputTemperature", cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY)
//    private ConditionEntity conditionForInputTemp;
//
//    @OneToMany(mappedBy = "outputTemperature", cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY)
//    private ConditionEntity conditionForOutputTemp;
//
//    @OneToMany(mappedBy = "inputPressure", cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY)
//    private ConditionEntity conditionForInputPressure;
//
//    @OneToMany(mappedBy = "outputPressure", cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY)
//    private ConditionEntity conditionForOutputPressure;
//
//    @OneToOne(mappedBy = "debiInput", cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY, optional = false)
//    private ConditionEntity conditionForOutputDebi;


    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
