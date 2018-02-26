package com.primus.crm.appointmentplan;

import com.primus.common.user.model.User;
import com.primus.externals.IAppointmentEntity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class AppointmentUnit {

    IAppointmentEntity entity ;
    User agent;
    User manager  ;
    Timestamp apptTime;
    double duration ;

    public IAppointmentEntity getEntity() {
        return entity;
    }

    public void setEntity(IAppointmentEntity entity) {
        this.entity = entity;
    }

    public AppointmentUnit(IAppointmentEntity entity, User agent, User manager, Timestamp apptTime, double duration) {
        this.entity = entity;
        this.agent = agent;
        this.manager = manager;
        this.apptTime = apptTime;
        this.duration = duration;
    }

    public User getAgent() {
        return agent;
    }

    public void setAgent(User agent) {
        this.agent = agent;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public Timestamp getApptTime() {
        return apptTime;
    }

    public void setApptTime(Timestamp apptTime) {
        this.apptTime = apptTime;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String shortDisplay() {
        SimpleDateFormat dateFormat  = new SimpleDateFormat( "dd/mon/yyyy HH:mm");
        return  "Agent:"+ agent.getUserId() + "\t time:" + dateFormat.format(apptTime);
    }
}
