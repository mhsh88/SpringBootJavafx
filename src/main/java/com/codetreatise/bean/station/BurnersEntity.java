package com.codetreatise.bean.station;

import com.codetreatise.bean.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "burner")
@EntityListeners({AuditingEntityListener.class})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BurnersEntity extends BaseEntity{


    @Lob
    public String text;

    private Double oxygenPercent;
    private Double flueGasTemprature;

    public BurnersEntity(){
        super();
    }
    public BurnersEntity(double oxygenPercent, double flueGasTemprature) {
        super();
        this.oxygenPercent = oxygenPercent;
        this.flueGasTemprature = flueGasTemprature;
    }


    @Basic
    @Column(name = "oxygen_percent")
    public Double getOxygenPercent() {
        return oxygenPercent;
    }

    public void setOxygenPercent(Double oxygenPercent) {
        this.oxygenPercent = oxygenPercent;
    }

    @Basic
    @Column(name = "flue_gas_temprature")
    public Double getFlueGasTemprature() {
        return flueGasTemprature;
    }

    public void setFlueGasTemprature(Double flueGasTemprature) {
        this.flueGasTemprature = flueGasTemprature;
    }


    @ManyToMany(mappedBy = "burners")
    public List<HeaterEntity> heaters;




}
