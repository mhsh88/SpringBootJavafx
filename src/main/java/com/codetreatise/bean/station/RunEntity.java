package com.codetreatise.bean.station;

import com.codetreatise.bean.unitNumber.Debi;

import javax.persistence.*;

@Entity
@Table(name = "run")
public class RunEntity extends PipeSpecificationsEntity {

    public RunEntity(){

    }

    public RunEntity(double runLength, String pipeSizeUnit, Debi debi) {
        super(runLength, pipeSizeUnit);
        this.debiInput = debi;
    }

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "debi_input_id")
    private Debi debiInput;



    public RunEntity(double runLength, String pipeSizeUnit) {
        super(runLength, pipeSizeUnit);
    }

    public Debi getDebiInput() {
        return debiInput;
    }

    public void setDebiInput(Debi debiInput) {
        this.debiInput = debiInput;
    }
    ////    private Double length;
//    private double debi;
////
//    @Column(name = "debi")
//    public double getDebi() {
//        return debi;
//    }
//
//    public void setDebi(double debi) {
//        this.debi = debi;
//    }
//
//    @ManyToOne
//    @JoinColumn(name="pipe_size_id")
//    public PipeSizeEntity pipeSize;
//
//
//    @Basic
//    @Column(name = "length")
//    public Double getLength() {
//        return length;
//    }
//
//    public void setLength(Double length) {
//        this.length = length;
//    }

}
