package com.primus.crm.appointmentplan.model;

import com.primus.abstracts.PrimusModel;
import com.primus.crm.appointmentplan.AppointmentUnit;
import com.primus.crm.target.model.Target;

import java.util.ArrayList;
import java.util.List;

public class AppointmentPlan extends PrimusModel {

    List<AppointmentUnit>  appointmentUnits ;

    Target target ;


    public List<AppointmentUnit> getAppointmentUnits() {
        return appointmentUnits;
    }

    public void setAppointmentUnits(List<AppointmentUnit> appointmentUnits) {
        this.appointmentUnits = appointmentUnits;

    }

    public void addAppointmentUnits(List<AppointmentUnit> appointmentUnits) {
        if(this.appointmentUnits == null)
            this.appointmentUnits = new ArrayList<>() ;
        this.appointmentUnits.addAll(appointmentUnits);

    }
    public void addAppointmentUnit(AppointmentUnit appointmentUnit) {
        if(this.appointmentUnits == null)
            this.appointmentUnits = new ArrayList<>() ;
        this.appointmentUnits.add(appointmentUnit);

    }


    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }
}
