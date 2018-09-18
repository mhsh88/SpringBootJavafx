package com.codetreatise.bean.station;

import com.codetreatise.bean.base.BaseEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "run")
public class RunEntity extends BaseEntity {
    private Double length;
    private double debi;

    @Column(name = "debi")
    public double getDebi() {
        return debi;
    }

    public void setDebi(double debi) {
        this.debi = debi;
    }

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

    @Override
    public int hashCode() {

        return Objects.hash(id, length);
    }
}
