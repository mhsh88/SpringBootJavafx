package com.codetreatise.bean.station;

import com.codetreatise.bean.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "gas")
public class GasEntity extends BaseEntity {
    private Double nitrogen;
    private Double carbonDioxide;
    private Double methan;
    private Double ethane;
    private Double propane;
    private Double nButane;
    private Double isoButane;
    private Double nPentane;
    private Double isoPentane;
    private Double hexane;
    private Double heptane;
    private Double octane;
    private Double nonane;
    private Double decane;
    private Double hydrogen;
    private Double oxygen;
    private Double carbonMonoxide;
    private Double water;
    private Double hydrogenSulfide;
    private Double helium;
    private Double argon;
    private Boolean moleWightPersent;
    private String name;

    @JsonView
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    public CityGateStationEntity cityGateStation;

    @Basic
    @Column(name = "carbon_dioxide")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "nitrogen")
    public Double getNitrogen() {
        return nitrogen;
    }

    public void setNitrogen(Double nitrogen) {
        this.nitrogen = nitrogen;
    }

    @Basic
    @Column(name = "carbon_dioxide")
    public Double getCarbonDioxide() {
        return carbonDioxide;
    }

    public void setCarbonDioxide(Double carbonDioxide) {
        this.carbonDioxide = carbonDioxide;
    }

    @Basic
    @Column(name = "methan")
    public Double getMethan() {
        return methan;
    }

    public void setMethan(Double methan) {
        this.methan = methan;
    }

    @Basic
    @Column(name = "ethane")
    public Double getEthane() {
        return ethane;
    }

    public void setEthane(Double ethane) {
        this.ethane = ethane;
    }

    @Basic
    @Column(name = "propane")
    public Double getPropane() {
        return propane;
    }

    public void setPropane(Double propane) {
        this.propane = propane;
    }

    @Basic
    @Column(name = "n_butane")
    public Double getnButane() {
        return nButane;
    }

    public void setnButane(Double nButane) {
        this.nButane = nButane;
    }

    @Basic
    @Column(name = "iso_butane")
    public Double getIsoButane() {
        return isoButane;
    }

    public void setIsoButane(Double isoButane) {
        this.isoButane = isoButane;
    }

    @Basic
    @Column(name = "n_pentane")
    public Double getnPentane() {
        return nPentane;
    }

    public void setnPentane(Double nPentane) {
        this.nPentane = nPentane;
    }

    @Basic
    @Column(name = "iso_pentane")
    public Double getIsoPentane() {
        return isoPentane;
    }

    public void setIsoPentane(Double isoPentane) {
        this.isoPentane = isoPentane;
    }

    @Basic
    @Column(name = "hexane")
    public Double getHexane() {
        return hexane;
    }

    public void setHexane(Double hexane) {
        this.hexane = hexane;
    }

    @Basic
    @Column(name = "heptane")
    public Double getHeptane() {
        return heptane;
    }

    public void setHeptane(Double heptane) {
        this.heptane = heptane;
    }

    @Basic
    @Column(name = "octane")
    public Double getOctane() {
        return octane;
    }

    public void setOctane(Double octane) {
        this.octane = octane;
    }

    @Basic
    @Column(name = "nonane")
    public Double getNonane() {
        return nonane;
    }

    public void setNonane(Double nonane) {
        this.nonane = nonane;
    }

    @Basic
    @Column(name = "decane")
    public Double getDecane() {
        return decane;
    }

    public void setDecane(Double decane) {
        this.decane = decane;
    }

    @Basic
    @Column(name = "hydrogen")
    public Double getHydrogen() {
        return hydrogen;
    }

    public void setHydrogen(Double hydrogen) {
        this.hydrogen = hydrogen;
    }

    @Basic
    @Column(name = "oxygen")
    public Double getOxygen() {
        return oxygen;
    }

    public void setOxygen(Double oxygen) {
        this.oxygen = oxygen;
    }

    @Basic
    @Column(name = "carbon_monoxide")
    public Double getCarbonMonoxide() {
        return carbonMonoxide;
    }

    public void setCarbonMonoxide(Double carbonMonoxide) {
        this.carbonMonoxide = carbonMonoxide;
    }

    @Basic
    @Column(name = "water")
    public Double getWater() {
        return water;
    }

    public void setWater(Double water) {
        this.water = water;
    }

    @Basic
    @Column(name = "hydrogen_sulfide")
    public Double getHydrogenSulfide() {
        return hydrogenSulfide;
    }

    public void setHydrogenSulfide(Double hydrogenSulfide) {
        this.hydrogenSulfide = hydrogenSulfide;
    }

    @Basic
    @Column(name = "helium")
    public Double getHelium() {
        return helium;
    }

    public void setHelium(Double helium) {
        this.helium = helium;
    }

    @Basic
    @Column(name = "argon")
    public Double getArgon() {
        return argon;
    }

    public void setArgon(Double argon) {
        this.argon = argon;
    }


    @Column(name = "mole_wight_persent")
    @NotNull
    public Boolean isMoleWightPersent() {
        if (moleWightPersent == null) {
            return false;
        }
        return moleWightPersent;
    }

    public void setMoleWightPersent(Boolean moleWightPersent) {
        this.moleWightPersent = moleWightPersent;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, nitrogen, carbonDioxide, methan, ethane, propane, nButane, isoButane, nPentane, isoPentane, hexane, heptane, octane, nonane, decane, hydrogen, oxygen, carbonMonoxide, water, hydrogenSulfide, helium, argon, moleWightPersent);
    }
}
