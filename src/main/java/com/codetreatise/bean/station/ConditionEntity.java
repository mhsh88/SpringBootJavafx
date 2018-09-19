package com.codetreatise.bean.station;

import com.codetreatise.bean.base.BaseEntity;
import com.codetreatise.bean.unitNumber.Debi;
import com.codetreatise.bean.unitNumber.Pressure;
import com.codetreatise.bean.unitNumber.Temperature;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "conditions")
public class ConditionEntity extends BaseEntity  {

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "env_tempreture_id")
    private Temperature envTemperature;
    private Double windSpeed;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "debi_input_id")
    private Debi debiInput;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "input_tempeature_id")
    private Temperature inputTemperature;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "input_pressure_id")
    private Pressure inputPressure;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "output_tempeature_id")
    private Temperature outputTemperature;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "output_pressure_id")
    private Pressure outputPressure;


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

    private Double envTempreture;
    private Double stationDebi;
    private Double stationInputTemprature;
    private Double stationInputPressure;
    private Double stationOutputTemprature;
    private Double stationOutputPressure;


    @Basic
    @Column(name = "env_tempreture")
    public Double getEnvTempreture() {
        return envTempreture;
    }

    public void setEnvTempreture(Double envTempreture) {
        this.envTempreture = envTempreture;
    }

    @Basic
    @Column(name = "wind_speed")
    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    @Basic
    @Column(name = "station_debi")
    public Double getStationDebi() {
        return stationDebi;
    }

    public void setStationDebi(Double stationDebi) {
        this.stationDebi = stationDebi;
    }

    @Basic
    @Column(name = "station_input_temprature")
    public Double getStationInputTemprature() {
        return stationInputTemprature;
    }

    public void setStationInputTemprature(Double stationInputTemprature) {
        this.stationInputTemprature = stationInputTemprature;
    }

    @Basic
    @Column(name = "station_input_pressure")
    public Double getStationInputPressure() {
        return stationInputPressure;
    }

    public void setStationInputPressure(Double stationInputPressure) {
        this.stationInputPressure = stationInputPressure;
    }

    @Basic
    @Column(name = "station_output_temprature")
    public Double getStationOutputTemprature() {
        return stationOutputTemprature;
    }

    public void setStationOutputTemprature(Double stationOutputTemprature) {
        this.stationOutputTemprature = stationOutputTemprature;
    }

    @Basic
    @Column(name = "station_output_pressure")
    public Double getStationOutputPressure() {
        return stationOutputPressure;
    }

    @OneToOne(mappedBy = "condition", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = true)
    private CityGateStationEntity station;

    @OneToOne(mappedBy = "condition", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, optional = true)
    private ResultEntity result;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="sec_id")
    private BaseSecEntity baseSecEntity;

    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public ResultEntity getResult() {
        return result;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public BaseSecEntity getBaseSecEntity() {
        return baseSecEntity;
    }

    public void setBaseSecEntity(BaseSecEntity baseSecEntity) {
        this.baseSecEntity = baseSecEntity;
    }

    public CityGateStationEntity getStation() {
        return station;
    }

    public void setStation(CityGateStationEntity station) {
        this.station = station;
    }

    public void setStationOutputPressure(Double stationOutputPressure) {
        this.stationOutputPressure = stationOutputPressure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConditionEntity that = (ConditionEntity) o;
        return id == that.id &&
                Objects.equals(envTempreture, that.envTempreture) &&
                Objects.equals(windSpeed, that.windSpeed) &&
                Objects.equals(stationDebi, that.stationDebi) &&
                Objects.equals(stationInputTemprature, that.stationInputTemprature) &&
                Objects.equals(stationInputPressure, that.stationInputPressure) &&
                Objects.equals(stationOutputTemprature, that.stationOutputTemprature) &&
                Objects.equals(stationOutputPressure, that.stationOutputPressure);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, envTempreture, windSpeed, stationDebi, stationInputTemprature, stationInputPressure, stationOutputTemprature, stationOutputPressure);
    }
}
