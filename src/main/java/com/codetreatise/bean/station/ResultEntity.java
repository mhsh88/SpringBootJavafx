package com.codetreatise.bean.station;

import com.codetreatise.bean.base.BaseEntity;
import ir.behinehsazan.gasStation.model.station.StationLogic;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "condition_result")
public class ResultEntity extends BaseEntity {

//    @OneToOne(fetch = FetchType.LAZY)
//    private ConditionEntity condition;

    @Lob
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<StationLogic> singleCalculation;

    @Lob
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<StationLogic> eightTemp;



    @Lob
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<StationLogic> fifteenTemp;

    public List<StationLogic> getFifteenTemp() {
        return fifteenTemp;
    }

    public void setFifteenTemp(List<StationLogic> fifteenTemp) {
        this.fifteenTemp = fifteenTemp;
    }

//    public ConditionEntity getCondition() {
//        return condition;
//    }
//
//    public void setCondition(ConditionEntity condition) {
//        this.condition = condition;
//    }

    public List<StationLogic> getSingleCalculation() {
        return singleCalculation;
    }

    public void setSingleCalculation(List<StationLogic> singleCalculation) {
        this.singleCalculation = singleCalculation;
    }

    public List<StationLogic> getEightTemp() {
        return eightTemp;
    }

    public void setEightTemp(List<StationLogic> eightTemp) {
        this.eightTemp = eightTemp;
    }
}
