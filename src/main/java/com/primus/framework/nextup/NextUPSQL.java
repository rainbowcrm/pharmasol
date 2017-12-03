package com.primus.framework.nextup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.primus.common.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class NextUPSQL {


    @Autowired
    JdbcTemplate jdbcTemplate;

    public void updateSEQValue(String program, int division, int company) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            String sql = " UPDATE NEXTUP_VALUES SET SEQ_VALUE  =  SEQ_VALUE + 1 WHERE COMPANY_ID = ? AND DIVISION_ID = ? AND PROGRAM = ? ";
            jdbcTemplate.update(sql, new Object[]{company, division, program});
        } catch (Exception ex) {
            Logger.logException("ailed", this.getClass(), ex);
        }
    }

    public void insertSEQValue(String program, int division, int company) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {

            String sql = " INSERT INTO NEXTUP_VALUES(COMPANY_ID,DIVISION_ID,PROGRAM,SEQ_VALUE) VALUES (?,?,?,1) ";
            jdbcTemplate.update(sql, new Object[]{company, division, program});
            statement.setInt(1, company);
            statement.setInt(2, division);
            statement.setString(3, program);
            statement.executeUpdate();
        } catch (SQLException ex) {

        }
    }


    public synchronized int getNextPKValue(String program, int division, int company) {

        Map<String, String> ans = new HashMap<String, String>();
        try {

            String sql = "Select max(SEQ_VALUE) from NEXTUP_VALUES  where COMPANY_ID =? and DIVISION_ID = ? and  PROGRAM = ? ";
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, new Object[]{company, division, program});
            if (rs.next()) {
                Object obj = rs.getObject(1);
                if (obj == null) {
                    insertSEQValue(program, division, company);
                    return 1;
                } else {
                    int val = Integer.parseInt(String.valueOf(obj));
                    updateSEQValue(program, division, company);
                    return ++val;
                }
            }
        } catch (Exception ex) {

        }
        return 1;
    }


    private static NextUpConfig.NextUpComponent getComponent(NextUpConfig config, SqlRowSet rs, int seq) throws SQLException {
        NextUpConfig.NextUpComponent comp = config.new NextUpComponent();
        String type = rs.getString("FIELD" + seq + "_TYPE");
        int width = rs.getInt("FIELD" + seq + "_WIDTH");
        String value = rs.getString("FIELD" + seq + "_VAULE");
        comp.setFieldType(type);
        comp.setFieldValue(value);
        comp.setFieldWidth(width);
        return comp;
    }

    public NextUpConfig getNextUpConfig(int company, String program) {
        NextUpConfig nextUpConfig = new NextUpConfig();


        Map<String, String> ans = new HashMap<String, String>();
        try {

            String sql = "Select *  from NEXTUP_CONFIG  where COMPANY_ID in (-1,?) and  PROGRAM = ? ORDER BY COMPANY_ID DESC ";
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, new Object[]{company, program});
            if (rs.next()) {
                nextUpConfig.setCompany(rs.getInt("COMPANY_ID"));
                nextUpConfig.setProgram(rs.getString("PROGRAM"));
                NextUpConfig.NextUpComponent comp1 = getComponent(nextUpConfig, rs, 1);
                NextUpConfig.NextUpComponent comp2 = getComponent(nextUpConfig, rs, 2);
                NextUpConfig.NextUpComponent comp3 = getComponent(nextUpConfig, rs, 3);
                NextUpConfig.NextUpComponent comp4 = getComponent(nextUpConfig, rs, 4);
                nextUpConfig.setComponent1(comp1);
                nextUpConfig.setComponent2(comp2);
                nextUpConfig.setComponent3(comp3);
                nextUpConfig.setComponent4(comp4);
                nextUpConfig.setDateFormat(rs.getString("DATEFORMAT1"));
            }
        } catch (SQLException ex) {

        }
        return nextUpConfig;
    }
}
