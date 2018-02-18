package com.primus.externals;

import com.primus.common.appointmentpreference.model.AppointmentPreference;
import com.primus.externals.doctor.model.DoctorAppointmentPreference;

import java.util.Collection;

public interface IAppointmentEntity {

    public Collection<? extends  AppointmentPreference> getAppointmentPreferences() ;

    public  String getIndividualVisitType();

    public  String getTotalVisitType();

    public int getId();
}
