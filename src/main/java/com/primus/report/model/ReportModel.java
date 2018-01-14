package com.primus.report.model;

import com.primus.abstracts.PrimusBusinessModel;
import com.primus.admin.region.model.Region;

import java.util.Date;

public class ReportModel extends PrimusBusinessModel {

    Region region ;
    Date fromDate;
    Date toDate;

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
}
