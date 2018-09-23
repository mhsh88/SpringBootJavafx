package com.codetreatise.bean.station;

import com.codetreatise.bean.base.BaseEntity;
import com.codetreatise.bean.unitNumber.Debi;
import com.codetreatise.bean.unitNumber.Pressure;
import com.codetreatise.bean.unitNumber.Temperature;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.time.LocalDate;

@Entity
@Table(name = "conditions")
public class ConditionEntity extends BaseEntity {
    @Transient
    DecimalFormat df = new DecimalFormat("#.###");

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "env_tempreture_id")
    private Temperature envTemperature;
    private Double windSpeed;
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "debi_input_id")
    private Debi debiInput;
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "input_tempeature_id")
    private Temperature inputTemperature;
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "input_pressure_id")
    private Pressure inputPressure;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "output_tempeature_id")
    private Temperature outputTemperature;
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "output_pressure_id")
    private Pressure outputPressure;

//    @OneToOne(mappedBy = "condition",
//            fetch = FetchType.LAZY, optional = true)
//    @Cascade(value = { org.hibernate.annotations.CascadeType.DELETE, org.hibernate.annotations.CascadeType.SAVE_UPDATE})
//    private CityGateStationEntity station;
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "condition_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    private ResultEntity result;

    @ManyToOne(fetch = FetchType.LAZY)
    private SecEntity sec;
    //    @Temporal(TemporalType.TIMESTAMP)
    private LocalDate time;


    @Transient
    public String getOutputPressureWithUnit() {
        return df.format(outputPressure.getPressure()) + " " + outputPressure.getUnit();
    }

    @Transient
    public String getOutputTempWithUnit() {
        return df.format(outputTemperature.getTemperature()) + " " + outputTemperature.getUnit();
    }

    @Transient
    public String getInputPressureWithUnit() {
        return df.format(inputPressure.getPressure()) + " " + inputPressure.getUnit();
    }

    @Transient
    public String getInputTempWithUnit() {
        return df.format(inputTemperature.getTemperature()) + " " + inputTemperature.getUnit();
    }

    @Transient
    public String getDebiWithUnit() {
        return df.format(debiInput.getDebi()) + " " + debiInput.getUnit();
    }

    @Transient
    public String getEnvTempWithUnit() {
        return df.format(envTemperature.getTemperature()) + " " + envTemperature.getUnit();
    }

    public Temperature getEnvTemperature() {
        return envTemperature;
    }

    public void setEnvTemperature(Temperature envTemperature) {
        this.envTemperature = envTemperature;
    }

    public Temperature getInputTemperature() {
        return inputTemperature;
    }

    public void setInputTemperature(Temperature inputTemperature) {
        this.inputTemperature = inputTemperature;
    }

    public Temperature getOutputTemperature() {
        return outputTemperature;
    }

    public void setOutputTemperature(Temperature outputTemperature) {
        this.outputTemperature = outputTemperature;
    }

    public Pressure getInputPressure() {
        return inputPressure;
    }

    public void setInputPressure(Pressure inputPressure) {
        this.inputPressure = inputPressure;
    }

    public Pressure getOutputPressure() {
        return outputPressure;
    }

    public void setOutputPressure(Pressure outputPressure) {
        this.outputPressure = outputPressure;
    }

    public Debi getDebiInput() {
        return debiInput;
    }

    public void setDebiInput(Debi debiInput) {
        this.debiInput = debiInput;
    }


    @Basic
    @Column(name = "wind_speed")
    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }









    public SecEntity getSec() {
        return sec;
    }

    public void setSec(SecEntity sec) {
        this.sec = sec;
    }

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public ResultEntity getResult() {
        return result;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
    }

//    public CityGateStationEntity getStation() {
//        return station;
//    }
//
//    public void setStation(CityGateStationEntity station) {
//        this.station = station;
//    }


}
