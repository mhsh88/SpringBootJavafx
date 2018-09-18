package com.codetreatise.bean.station;

import com.codetreatise.bean.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "heater")
public class HeaterEntity extends BaseEntity {

    private Double efficiency;






    @JsonView
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinTable(
            name="heater_burner"
            , joinColumns={
            @JoinColumn(name="heater_id", nullable=false)
    }
            , inverseJoinColumns={
            @JoinColumn(name="burner_id", nullable=false)
    })
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
