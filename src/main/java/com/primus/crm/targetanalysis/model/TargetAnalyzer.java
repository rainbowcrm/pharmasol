package com.primus.crm.targetanalysis.model;

import com.primus.abstracts.PrimusBusinessModel;
import com.techtrade.rads.framework.model.graphdata.BarChartData;

public class TargetAnalyzer  extends PrimusBusinessModel {

    int target ;
    BarChartData targetData;

    String basedOn;

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public BarChartData getTargetData() {
        return targetData;
    }

    public void setTargetData(BarChartData targetData) {
        this.targetData = targetData;
    }

    public String getBasedOn() {
        return basedOn;
    }

    public void setBasedOn(String basedOn) {
        this.basedOn = basedOn;
    }
}
