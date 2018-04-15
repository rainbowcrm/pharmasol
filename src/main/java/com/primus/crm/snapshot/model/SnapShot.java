package com.primus.crm.snapshot.model;

import com.techtrade.rads.framework.model.abstracts.ModelObject;

import java.util.Date;
import java.util.List;

public class SnapShot extends ModelObject {

    Date periodFrom;
    Date periodTo;

    String  location;
    String  period;

    int visitCount;

    SalesFigure salesFigure ;
    OrderFigure orderFigure ;
    POBFigure pobFigure ;

    List<FeedbackDetail> feedbackDetailList  ;
    int feedbackCount;

    public Date getPeriodFrom() {
        return periodFrom;
    }

    public void setPeriodFrom(Date periodFrom) {
        this.periodFrom = periodFrom;
    }

    public Date getPeriodTo() {
        return periodTo;
    }

    public void setPeriodTo(Date periodTo) {
        this.periodTo = periodTo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public SalesFigure getSalesFigure() {
        return salesFigure;
    }

    public void setSalesFigure(SalesFigure salesFigure) {
        this.salesFigure = salesFigure;
    }

    public OrderFigure getOrderFigure() {
        return orderFigure;
    }

    public void setOrderFigure(OrderFigure orderFigure) {
        this.orderFigure = orderFigure;
    }

    public POBFigure getPobFigure() {
        return pobFigure;
    }

    public void setPobFigure(POBFigure pobFigure) {
        this.pobFigure = pobFigure;
    }

    public List<FeedbackDetail> getFeedbackDetailList() {
        return feedbackDetailList;
    }

    public void setFeedbackDetailList(List<FeedbackDetail> feedbackDetailList) {
        this.feedbackDetailList = feedbackDetailList;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    public int getFeedbackCount() {
        return feedbackCount;
    }

    public void setFeedbackCount(int feedbackCount) {
        this.feedbackCount = feedbackCount;
    }
}
