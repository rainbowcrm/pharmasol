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

    public int countAllStockists(  int locationId, int company)
    {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("Select count(*) from STOCKISTS DCTR , STOCKISTS_ASSOCIATIONS DASS where DCTR.ID =  DASS.STOCKIST_ID AND    "  +
                "  DCTR.IS_DELETED  = FALSE AND DASS.LOCATION_ID = ? AND DCTR.COMPANY_ID= ?   ", new Object[]{locationId,company});
        int ct = 0 ;
        if (rowSet.next()) {
            ct =  rowSet.getInt(1);
        }
        return ct;
    }

    public int countAllStores(  int locationId, int company)
    {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("Select count(*) from STORES DCTR , STORE_ASSOCIATIONS DASS where DCTR.ID =  DASS.STORE_ID AND    "  +
                "  DCTR.IS_DELETED  = FALSE AND DASS.LOCATION_ID = ? AND DCTR.COMPANY_ID= ?   ", new Object[]{locationId,company});
        int ct = 0 ;
        if (rowSet.next()) {
            ct =  rowSet.getInt(1);
        }
        return ct;
    }

    public int countAllDoctors(  int locationId, int company)
    {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("Select count(*) from DOCTORS DCTR , DOCTOR_ASSOCIATIONS DASS where DCTR.ID =  DASS.DOCTOR_ID AND    "  +
                "  DCTR.IS_DELETED  = FALSE AND DASS.LOCATION_ID = ? AND DCTR.COMPANY_ID= ?   ", new Object[]{locationId,company});
        int ct = 0 ;
        if (rowSet.next()) {
            ct =  rowSet.getInt(1);
        }
        return ct;
    }

    public int countDoctorsForClass( String doctorClass , int locationId, int company)
    {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("Select count(*) from DOCTORS DCTR , DOCTOR_ASSOCIATIONS DASS where DCTR.ID =  DASS.ID AND   "  +
                "  DCTR.IS_DELETED  = FALSE AND DASS.LOCATION_ID = ? AND DCTR.COMPANY_ID= ?  AND DCTR.DOCCLASS=  ? ", new Object[]{locationId,company,doctorClass});
        int ct = 0 ;
        if (rowSet.next()) {
            ct =  rowSet.getInt(1);
        }
        return ct;
    }

    public int countVisitsToAllStockists( Date from, Date to , int locationId, int company)
    {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("Select count(*) from APPOINTMENTS APPT where   "  +
                "  APPT.STOCKIST_ID IS  NOT NULL AND   APPT.APPT_DATE >= ?  AND APPT.APPT_DATE <= ? AND APPT.APPT_STATUS IN  " +
                "('CMPLTD','CLSD')  AND APPT.IS_DELETED  = FALSE AND APPT.LOCATION_ID = ? AND APPT.COMPANY_ID= ?  ", new Object[]{from,to,locationId, company});
        int ct = 0 ;
        if (rowSet.next()) {
            ct =  rowSet.getInt(1);
        }
        return ct;
    }

    public int countVisitsToAllStores( Date from, Date to , int locationId, int company)
    {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("Select count(*) from APPOINTMENTS APPT where   "  +
                "  APPT.STORE_ID IS  NOT NULL AND   APPT.APPT_DATE >= ?  AND APPT.APPT_DATE <= ? AND APPT.APPT_STATUS IN  " +
                "('CMPLTD','CLSD')  AND APPT.IS_DELETED  = FALSE AND APPT.LOCATION_ID = ? AND APPT.COMPANY_ID= ?  ", new Object[]{from,to,locationId, company});
        int ct = 0 ;
        if (rowSet.next()) {
            ct =  rowSet.getInt(1);
        }
        return ct;
    }

    public int countVisitsToAllDoctors( Date from, Date to , int locationId, int company)
    {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("Select count(*) from APPOINTMENTS APPT where   "  +
                "  APPT.DOCTOR_ID IS  NOT NULL AND   APPT.APPT_DATE >= ?  AND APPT.APPT_DATE <= ? AND APPT.APPT_STATUS IN  " +
                "('CMPLTD','CLSD')  AND APPT.IS_DELETED  = FALSE AND APPT.LOCATION_ID = ? AND APPT.COMPANY_ID= ?  ", new Object[]{from,to,locationId, company});
        int ct = 0 ;
        if (rowSet.next()) {
            ct =  rowSet.getInt(1);
        }
        return ct;
    }

    public int countAgentVisitsToAllStockists(String agentId, Date from, Date to , int locationId, int company)
    {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("Select count(*) from APPOINTMENTS APPT where   "  +
                "  APPT.STOCKIST_ID IS  NOT NULL AND   APPT.APPT_DATE >= ?  AND APPT.APPT_DATE <= ? AND APPT.APPT_STATUS IN  " +
                "('CMPLTD','CLSD')  AND APPT.IS_DELETED  = FALSE AND APPT.LOCATION_ID = ? AND APPT.AGENT_USER_ID =  ? AND APPT.COMPANY_ID= ?  ", new Object[]{from,to,locationId,agentId, company});
        int ct = 0 ;
        if (rowSet.next()) {
            ct =  rowSet.getInt(1);
        }
        return ct;
    }

    public int countAgentVisitsToAllStores(String agentId, Date from, Date to , int locationId, int company)
    {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("Select count(*) from APPOINTMENTS APPT where   "  +
                "  APPT.STORE_ID IS  NOT NULL AND   APPT.APPT_DATE >= ?  AND APPT.APPT_DATE <= ? AND APPT.APPT_STATUS IN  " +
                "('CMPLTD','CLSD')  AND APPT.IS_DELETED  = FALSE AND APPT.LOCATION_ID = ? AND APPT.AGENT_USER_ID =  ? AND APPT.COMPANY_ID= ?  ", new Object[]{from,to,locationId,agentId, company});
        int ct = 0 ;
        if (rowSet.next()) {
            ct =  rowSet.getInt(1);
        }
        return ct;
    }

    public int countAgentVisitsToAllDoctors(String agentId, Date from, Date to , int locationId, int company)
    {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("Select count(*) from APPOINTMENTS APPT where   "  +
                "  APPT.DOCTOR_ID IS  NOT NULL AND   APPT.APPT_DATE >= ?  AND APPT.APPT_DATE <= ? AND APPT.APPT_STATUS IN  " +
                "('CMPLTD','CLSD')  AND APPT.IS_DELETED  = FALSE AND APPT.LOCATION_ID = ?  AND APPT.AGENT_USER_ID =  ? AND APPT.COMPANY_ID= ?  ", new Object[]{from,to,locationId, agentId, company});
        int ct = 0 ;
        if (rowSet.next()) {
            ct =  rowSet.getInt(1);
        }
        return ct;
    }


    public int countVisitsToDoctorClass(String doctorClass , Date from, Date to , int locationId, int company)
    {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("Select count(*) from APPOINTMENTS APPT,DOCTORS DCTR where APPT.DOCTOR_ID = DCTR.ID  "  +
                " AND APPT.DOCTOR_ID IS  NOT NULL AND  DCTR.DOCCLASS =  ?  AND APPT.APPT_DATE >= ?  AND APPT.APPT_DATE <= ? AND APPT.APPT_STATUS IN  " +
                "('CMPLTD','CLSD')  AND APPT.IS_DELETED  = FALSE AND APPT.LOCATION_ID = ? AND APPT.COMPANY_ID= ?  ", new Object[]{doctorClass,from,to,locationId, company});
        int ct = 0 ;
        if (rowSet.next()) {
            ct =  rowSet.getInt(1);
        }
        return ct;
    }


    public int countVisitsToDoctor(int doctorId , Date from, Date to, int locationId, int company)
    {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("Select count(*) from APPOINTMENTS APPT  where DOCTOR_ID =  ?  AND APPT_DATE >= ?  AND APPT_DATE <= ? AND APPT_STATUS IN  " +
                "('CMPLTD','CLSD') AND IS_DELETED  = FALSE AND APPT.LOCATION_ID = ? AND APPT.COMPANY_ID= ?  ", new Object[]{doctorId,from,to,locationId,company});
        int ct = 0 ;
        if (rowSet.next()) {
             ct =  rowSet.getInt(1);
        }
        return ct;
    }

    public int countVisitsToStore(int storeId , Date from, Date to, int locationId, int company)
    {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("Select count(*) from APPOINTMENTS APPT where STORE_ID =  ?  AND APPT_DATE >= ?  AND APPT_DATE <= ? AND APPT_STATUS IN  " +
                "('CMPLTD','CLSD') AND IS_DELETED  = FALSE AND APPT.LOCATION_ID = ?  AND APPT.COMPANY_ID= ?  ", new Object[]{storeId,from,to,locationId,company});
        int ct = 0 ;
        if (rowSet.next()) {
            ct =  rowSet.getInt(1);
        }
        return ct;
    }

    public int countVisitsToStockist(int stockistId , Date from, Date to, int locationId , int company )
    {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("Select count(*) from APPOINTMENTS APPT where STOCKIST_ID =  ?  AND APPT_DATE >= ?  AND APPT_DATE <= ? AND APPT_STATUS IN  " +
                "('CMPLTD','CLSD') AND IS_DELETED  = FALSE AND APPT.LOCATION_ID = ? AND APPT.COMPANY_ID= ?  ", new Object[]{stockistId,from,to,locationId,company});
        int ct = 0 ;
        if (rowSet.next()) {
            ct =  rowSet.getInt(1);
        }
        return ct;
    }


    public int countAgentVisitsToDoctor(String agentId, int doctorId , Date from, Date to, int locationId, int company)
    {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("Select count(*) from APPOINTMENTS APPT  where DOCTOR_ID =  ?  AND APPT_DATE >= ?  AND APPT_DATE <= ? AND APPT_STATUS IN  " +
                "('CMPLTD','CLSD') AND IS_DELETED  = FALSE AND APPT.LOCATION_ID = ?  AND APPT.AGENT_USER_ID =  ?  AND APPT.COMPANY_ID= ?  ", new Object[]{doctorId,from,to,locationId,agentId,company});
        int ct = 0 ;
        if (rowSet.next()) {
            ct =  rowSet.getInt(1);
        }
        return ct;
    }

    public int countAgentVisitsToStore(String agentId,int storeId , Date from, Date to, int locationId, int company)
    {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("Select count(*) from APPOINTMENTS APPT where STORE_ID =  ?  AND APPT_DATE >= ?  AND APPT_DATE <= ? AND APPT_STATUS IN  " +
                "('CMPLTD','CLSD') AND IS_DELETED  = FALSE AND APPT.LOCATION_ID = ? AND APPT.AGENT_USER_ID =  ?  AND APPT.COMPANY_ID= ?  ", new Object[]{storeId,from,to,locationId,agentId,company});
        int ct = 0 ;
        if (rowSet.next()) {
            ct =  rowSet.getInt(1);
        }
        return ct;
    }

    public int countAgentVisitsToStockist(String agentId,  int stockistId , Date from, Date to, int locationId , int company )
    {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("Select count(*) from APPOINTMENTS APPT where STOCKIST_ID =  ?  AND APPT_DATE >= ?  AND APPT_DATE <= ? AND APPT_STATUS IN  " +
                "('CMPLTD','CLSD') AND IS_DELETED  = FALSE AND APPT.LOCATION_ID = ? AND APPT.AGENT_USER_ID =  ? AND APPT.COMPANY_ID= ?  ", new Object[]{stockistId,from,to,locationId,agentId,company});
        int ct = 0 ;
        if (rowSet.next()) {
            ct =  rowSet.getInt(1);
        }
        return ct;
    }

    public double countAgentTotalSale(String agentId,   Date from, Date to, int locationId , int company )
    {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("Select sum(OL.RATE * OL.QTY ) from STOCKIST_VISIT_ORDER_LINES OL, APPOINTMENTS APPT where APPT.ID =OL.APPOINTMENT_ID  " +
                "  AND APPT.APPT_DATE >= ?  AND APPT.APPT_DATE <= ? AND  OL.IS_DELETED  = FALSE AND   APPT.APPT_STATUS IN  " +
                "('CMPLTD','CLSD') AND APPT.IS_DELETED  = FALSE AND APPT.LOCATION_ID = ? AND APPT.AGENT_USER_ID =  ? AND APPT.COMPANY_ID= ?  ", new Object[]{from,to,locationId,agentId,company});
        double ct = 0 ;
        if (rowSet.next()) {
            ct =  rowSet.getDouble(1);
        }
        return ct;
    }

    public double countItemTotalSale(int itemId,   Date from, Date to, int locationId , int company )
    {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("Select sum(OL.RATE * OL.QTY ) from STOCKIST_VISIT_ORDER_LINES OL, SKUS SKS, ITEMS ITM,  APPOINTMENTS APPT where APPT.ID =OL.APPOINTMENT_ID  " +
                "AND OL.SKU_ID = SKS.ID AND SKS.ITEM_ID = ITM.ID    AND APPT.APPT_DATE >= ?  AND APPT.APPT_DATE <= ? AND  OL.IS_DELETED  = FALSE AND   APPT.APPT_STATUS IN  " +
                "('CMPLTD','CLSD') AND APPT.IS_DELETED  = FALSE AND APPT.LOCATION_ID = ? AND ITM.ID =  ? AND APPT.COMPANY_ID= ?  ", new Object[]{from,to,locationId,itemId,company});
        double ct = 0 ;
        if (rowSet.next()) {
            ct =  rowSet.getDouble(1);
        }
        return ct;
    }

}
