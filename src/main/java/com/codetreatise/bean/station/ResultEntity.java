package com.codetreatise.bean.station;

import com.codetreatise.bean.base.BaseEntity;
import ir.behinehsazan.gasStation.model.station.StationLogic;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "condition_result")
public class ResultEntity extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "condition_id")
    private ConditionEntity condition;

    @Lob
    @ElementCollection
    private List<StationLogic> singleCalculation;

    public ConditionEntity getCondition() {
        return condition;
    }

    public void setCondition(ConditionEntity condition) {
        this.condition = condition;
    }

    public List<StationLogic> getSingleCalculation() {
        return singleCalculation;
    }

    public void setSingleCalculation(List<StationLogic> singleCalculation) {
        this.singleCalculation = singleCalculation;
    }
}
