package com.primus;

import com.primus.common.Logger;
import com.primus.crm.appointment.service.AppointmentStatusUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SchedulerThread extends Thread {

    @Autowired
    AppointmentStatusUpdater appointmentStatusUpdater ;

    @Autowired
    ApplicationManager applicationManager ;

    @Override
    public void run() {

        try {
            for (; ; ) {
                appointmentStatusUpdater.runScheduledJob();
                Thread.sleep(Long.parseLong(applicationManager.getThreadInterval()));
            }
        }catch(Exception ex) {
            Logger.logException("Error" , this.getClass() ,ex);
        }
    }


}
