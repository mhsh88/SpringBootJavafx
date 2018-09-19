package com.codetreatise.bean.station;

import com.codetreatise.bean.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "heater")
public class HeaterEntity extends BaseEntity {

    private Double efficiency;

    public HeaterEntity(){

    }
    public HeaterEntity(double efficiency, List<BurnersEntity> burners) {
        super();
        this.efficiency = efficiency;
        this.burners = burners;
    }




    @JsonView
    @ManyToMany
    @JoinTable(
            name="heater_burner"
            , joinColumns={
            @JoinColumn(name="heater_id", nullable=false)
    }
            , inverseJoinColumns={
            @JoinColumn(name="burner_id", nullable=false)
    })
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<BurnersEntity> burners;


    @JsonView
    @ManyToMany(mappedBy = "heaters")
    public List<CityGateStationEntity> cityGateStation;




    @Basic
    @Column(name = "Efficiency")
    public Double getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(Double efficiency) {
        this.efficiency = efficiency;
    }

    public List<BurnersEntity> getBurners() {
        return burners;
    }

    public void setBurners(List<BurnersEntity> burners) {
        this.burners = burners;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeaterEntity that = (HeaterEntity) o;
        return id == that.id &&
                Objects.equals(efficiency, that.efficiency);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, efficiency);
    }
}
