package com.primus.crm.appointment.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;

@Component
public class AppointmentSQL {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public  void ScheduleAppointmentStatus( ){
        try {
            String sql = " UPDATE APPOINTMENTS SET APPT_STATUS = ?     WHERE APPT_STATUS =? AND APPT_DATE <= ?    " ;
            jdbcTemplate.update(sql, new Object[] {"SCHD","PLND",new Timestamp(new java.util.Date().getTime()  + (24 * 60 * 60 * 1000)  )});
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
