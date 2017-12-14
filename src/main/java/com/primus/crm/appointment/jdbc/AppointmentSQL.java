package com.primus.crm.appointment.jdbc;

import com.primus.common.FVConstants;
import com.techtrade.rads.framework.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
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
            jdbcTemplate.update(sql, new Object[] {FVConstants.APPT_STATUS.SCHEDULED,FVConstants.APPT_STATUS.PLANNED,
                    new Timestamp(new java.util.Date().getTime()  + (24 * 60 * 60 * 1000)  )});

            String sql2 = " UPDATE APPOINTMENTS SET APPT_STATUS = ?     WHERE APPT_STATUS =? AND APPT_DATE < ?    " ;
            jdbcTemplate.update(sql2, new Object[] {FVConstants.APPT_STATUS.PENDING,FVConstants.APPT_STATUS.SCHEDULED,
                    new Timestamp(new java.util.Date().getTime()  - (24 * 60 * 60 * 1000)  )});
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getLastFeedBack(int currentId, String partyType, int partyId) {

        String sql = "";
        Object result = null;
        if(FVConstants.EXTERNAL_PARTY.DOCTOR.equalsIgnoreCase(partyType)) {
            sql = " SELECT FEEDBACK FROM APPOINTMENTS WHERE DOCTOR_ID = ? AND VISIT_COMPLETION = ( SELECT MAX(VISIT_COMPLETION) FROM APPOINTMENTS WHERE  DOCTOR_ID = ? AND IS_DELETED = FALSE AND APPT_STATUS IN ('CMPLTD','CLSD' ) ) ";
        } else if(FVConstants.EXTERNAL_PARTY.STOCKIST.equalsIgnoreCase(partyType)) {
            sql = " SELECT FEEDBACK FROM APPOINTMENTS WHERE STOCKIST_ID = ? AND  VISIT_COMPLETION = ( SELECT MAX(VISIT_COMPLETION) FROM APPOINTMENTS WHERE   STOCKIST_ID = ? AND IS_DELETED = FALSE AND APPT_STATUS IN ('CMPLTD','CLSD' ) )  ";
        }else if(FVConstants.EXTERNAL_PARTY.STORE.equalsIgnoreCase(partyType)) {
            sql = " SELECT FEEDBACK FROM APPOINTMENTS WHERE STORE_ID = ? AND VISIT_COMPLETION = ( SELECT MAX(VISIT_COMPLETION) FROM APPOINTMENTS WHERE   STORE_ID = ? AND IS_DELETED = FALSE AND APPT_STATUS IN ('CMPLTD','CLSD' ) )  ";
        }
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, new Object[]{partyId, partyId});
        if (rs.next()) {
            result  = rs.getObject(1);
        }
            return Utils.isNull(result)?"":new String((byte[]) result);

    }
}
