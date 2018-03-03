package com.primus.crm.appointmentplan;

import com.primus.common.FVConstants;
import com.primus.common.user.model.User;
import com.primus.externals.IAppointmentEntity;
import com.techtrade.rads.framework.model.abstracts.ModelObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class AppointmentUnit extends ModelObject {

    IAppointmentEntity entity ;
    User agent;
    User manager  ;
    Timestamp apptTime;
    double duration ;
    int pass ;

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

    public AppointmentUnit(IAppointmentEntity entity, User agent, User manager, Timestamp apptTime, double duration,int pass) {
        this.entity = entity;
        this.agent = agent;
        this.manager = manager;
        this.apptTime = apptTime;
        this.duration = duration;
        this.pass = pass;
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
        SimpleDateFormat dateFormat  = new SimpleDateFormat( "dd/MM/yyyy HH:mm");
        return  "Agent:"+ agent.getUserId() + "\t time:" + dateFormat.format(apptTime) +" \tpass:" + pass;
    }

    public String getFullTime()
    {
        SimpleDateFormat dateFormat  = new SimpleDateFormat( "dd/MM/yyyy HH:mm");
        return dateFormat.format(apptTime);
    }

    public String getVisitType()
    {
        if (entity.getIndividualVisitType().equalsIgnoreCase(FVConstants.VISIT_TO.IND_DOCTOR))
            return "Doctor" ;
        else if (entity.getIndividualVisitType().equalsIgnoreCase(FVConstants.VISIT_TO.IND_STOCKIST))
            return "Stockist" ;
        else
            return "Store" ;
    }

    public int getPass() {
        return pass;
    }

    public void setPass(int pass) {
        this.pass = pass;
    }
}
