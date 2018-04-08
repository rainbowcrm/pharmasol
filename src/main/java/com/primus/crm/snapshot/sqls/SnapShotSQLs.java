package com.primus.crm.snapshot.sqls;

import com.primus.crm.snapshot.model.FeedbackDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SnapShotSQLs {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /*public List<FeedbackDetail> getFeedBacksForLocation(int location, int company)
    {
        List<FeedbackDetail> feedBacks = new ArrayList<>();
        String sql = " SELECT FEEDBACK,DOCTORS.NAME , STORES.NAME, STOCKISTS.NAME FROM APPOINTMENTS , DOCTORS, STORES,STOCKISTS  WHERE APPOINTMENTS.DOCTOR_ID =DOCTORS.ID  AND " +
                " LOCATION_ID = ?  AND COMPANY_ID =  ? AND" +
                " IS_DELETED = FALSE AND APPT_STATUS IN ('CMPLTD','CLSD' ) ) ";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, new Object[]{location, company});
        while (rs.next()) {
            FeedbackDetail detail   = new FeedbackDetail();
            detail.setFeedback(rs.getString(1));
            detail.setDate();
            detail.setGivenBy();
            feedBacks.add(rs.getString(1));
        }
        return feedBacks ;

    }*/



}
