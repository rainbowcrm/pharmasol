package com.primus.common.appointmentpreference.model;

import com.primus.abstracts.PrimusBusinessModel;
import com.primus.abstracts.PrimusModel;
import com.primus.common.user.model.User;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class AppointmentPreference extends PrimusBusinessModel implements  Comparable{

    protected int weekday;
    protected  Date preferredTime;
    protected int duration;
    protected User preferredAgent;
    protected User prefferedManager;
    protected Integer rank;
    protected String description;

    protected String hhMM;


    @Column(name  ="WEEKDAY")
    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    @Column(name  ="PREF_TIME")
    public Date getPreferredTime() {
        return preferredTime;
    }


    public void setPreferredTime(Date preferredTime) {
        this.preferredTime = preferredTime;
    }

    @Column(name  ="PREF_DURATION")
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @RadsPropertySet(useBKForJSON = true, useBKForMap = true, useBKForXML = true)
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "PREF_AGENT_USER_ID")
    public User getPreferredAgent() {
        return preferredAgent;
    }

    public void setPreferredAgent(User preferredAgent) {
        this.preferredAgent = preferredAgent;
    }

    @RadsPropertySet(useBKForJSON = true, useBKForMap = true, useBKForXML = true)
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "PREF_MANAGER_USER_ID")
    public User getPrefferedManager() {
        return prefferedManager;
    }

    public void setPrefferedManager(User prefferedManager) {
        this.prefferedManager = prefferedManager;
    }

    @Column(name  ="RANK")
    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Column(name  ="DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Transient
    public String getHhMM() {
        return hhMM;
    }

    public void setHhMM(String hhMM) {
        this.hhMM = hhMM;
    }

    @Override
    public int compareTo(Object o) {
        AppointmentPreference compared   =(AppointmentPreference) o;
        return this.getRank()<compared.getRank()?1:-1;
    }
}
