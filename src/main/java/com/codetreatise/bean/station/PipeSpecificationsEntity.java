package com.codetreatise.bean.station;

import com.codetreatise.bean.base.BaseEntity;
import com.codetreatise.bean.constants.PipeSpecificatonConstants;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "pipe_specifications")
public class PipeSpecificationsEntity extends BaseEntity implements PipeSpecificatonConstants {
    private Double length;
    private Double insulationFactor;
    private Double insulationThickness;
    private String insulationThicknessUnit;
    private String pipeSizeUnit;
    private boolean insulation;

    @ManyToOne
    @JoinColumn(name="pipe_size_id")
    public PipeSizeEntity pipeSize;

    @Basic
    @Column(name = "length")
    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    @Basic
    @Column(name = "insulation_factor")
    public Double getInsulationFactor() {
        return insulationFactor;
    }

    public void setInsulationFactor(Double insulationFactor) {
        this.insulationFactor = insulationFactor;
    }

    @Basic
    @Column(name = "insulation_thickness")
    public Double getInsulationThickness() {
        return insulationThickness;
    }

    public void setInsulationThickness(Double insulationThickness) {
        this.insulationThickness = insulationThickness;
    }

    @Column(name = "insulation_thickness_unit")
    public String getInsulationThicknessUnit() {
        return insulationThicknessUnit;
    }

    public void setInsulationThicknessUnit(String insulationThicknessUnit) {
        this.insulationThicknessUnit = insulationThicknessUnit;
    }

    @Column(name = "pipe_size_unit")
    public String getPipeSizeUnit() {
        return pipeSizeUnit;
    }

    public void setPipeSizeUnit(String pipeSizeUnit) {
        this.pipeSizeUnit = pipeSizeUnit;
    }

    @Column(name = "is_insulation")
    public boolean isInsulation() {
        return insulation;
    }

    public void setInsulation(boolean insulation) {
        this.insulation = insulation;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PipeSpecificationsEntity that = (PipeSpecificationsEntity) o;
        return id == that.id &&
                Objects.equals(length, that.length) &&
                Objects.equals(insulationFactor, that.insulationFactor) &&
                Objects.equals(insulationThickness, that.insulationThickness);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, length, insulationFactor, insulationThickness);
    }
}
