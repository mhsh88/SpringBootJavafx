package com.codetreatise.bean.station;

import com.codetreatise.bean.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "condition_result")
public class ResultEntity extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "condition_id")
    private ConditionEntity condition;

}
