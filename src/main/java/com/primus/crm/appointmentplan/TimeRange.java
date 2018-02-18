package com.primus.crm.appointmentplan;

import java.util.Date;

public class TimeRange {

    Date start;
    Date end;


    Date suggestedDate ;
    Date suggestedTime  ;
    int suggestedTimeHH ;
    int suggestedTimeMM;

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public TimeRange(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public Date getSuggestedDate() {
        return suggestedDate;
    }

    public void setSuggestedDate(Date suggestedDate) {
        this.suggestedDate = suggestedDate;
    }

    public Date getSuggestedTime() {
        return suggestedTime;
    }

    public void setSuggestedTime(Date suggestedTime) {
        this.suggestedTime = suggestedTime;
    }

    public int getSuggestedTimeHH() {
        return suggestedTimeHH;
    }

    public void setSuggestedTimeHH(int suggestedTimeHH) {
        this.suggestedTimeHH = suggestedTimeHH;
    }

    public int getSuggestedTimeMM() {
        return suggestedTimeMM;
    }

    public void setSuggestedTimeMM(int suggestedTimeMM) {
        this.suggestedTimeMM = suggestedTimeMM;
    }
}
