package com.primus.crm.appointment.model;

import com.techtrade.rads.framework.model.abstracts.ModelObject;

import java.util.Date;

public class PastAppointment  extends ModelObject {

    String feedback;
    String agentRemarks;
    String managerRemarks;
    Date date;
    String time;

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getAgentRemarks() {
        return agentRemarks;
    }

    public void setAgentRemarks(String agentRemarks) {
        this.agentRemarks = agentRemarks;
    }

    public String getManagerRemarks() {
        return managerRemarks;
    }

    public void setManagerRemarks(String managerRemarks) {
        this.managerRemarks = managerRemarks;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
