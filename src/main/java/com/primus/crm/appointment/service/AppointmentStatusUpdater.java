package com.primus.crm.appointment.service;

import com.primus.crm.appointment.jdbc.AppointmentSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppointmentStatusUpdater {

    @Autowired
    AppointmentService service ;

    @Autowired
    AppointmentSQL appointmentSQL ;

    public void runScheduledJob()
    {
        appointmentSQL.ScheduleAppointmentStatus();
    }
}
