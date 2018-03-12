package com.primus.crm.targetanalysis.sqls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TargetAnalyseSQLs {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public int countVisitsToDoctorClass(String doctorClass , Date from, Date to)
    {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("Select count(*) from APPOINTMENTS APPT,DOCTORS DCTR where APPT.DOCTOR_ID = DCTR.ID  "  +
                " AND APPT.DOCTOR_ID IS  NOT NULL AND  DCTR.DOCCLASS =  ?  AND APPT.APPT_DATE >= ?  AND APPT.APPT_DATE <= ? AND APPT.APPT_STATUS IN  " +
                "('CMPLTD','CLSD') ", new Object[]{doctorClass,from,to});
        int ct = 0 ;
        if (rowSet.next()) {
            ct =  rowSet.getInt(1);
        }
        return ct;
    }


    public int countVisitsToDoctor(int doctorId , Date from, Date to)
    {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("Select count(*) from APPOINTMENTS where DOCTOR_ID =  ?  AND APPT_DATE >= ?  AND APPT_DATE <= ? AND APPT_STATUS IN  " +
                "('CMPLTD','CLSD') ", new Object[]{doctorId,from,to});
        int ct = 0 ;
        if (rowSet.next()) {
             ct =  rowSet.getInt(1);
        }
        return ct;
    }

    public int countVisitsToStore(int storeId , Date from, Date to)
    {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("Select count(*) from APPOINTMENTS where STORE_ID =  ?  AND APPT_DATE >= ?  AND APPT_DATE <= ? AND APPT_STATUS IN  " +
                "('CMPLTD','CLSD') ", new Object[]{storeId,from,to});
        int ct = 0 ;
        if (rowSet.next()) {
            ct =  rowSet.getInt(1);
        }
        return ct;
    }

    public int countVisitsToStockist(int stockistId , Date from, Date to)
    {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("Select count(*) from APPOINTMENTS where STOCKIST_ID =  ?  AND APPT_DATE >= ?  AND APPT_DATE <= ? AND APPT_STATUS IN  " +
                "('CMPLTD','CLSD') ", new Object[]{stockistId,from,to});
        int ct = 0 ;
        if (rowSet.next()) {
            ct =  rowSet.getInt(1);
        }
        return ct;
    }
}
