package com.primus.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class GeneralSQL {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public  Map<String,String> getFiniteValues(String typeCode) {

        Map<String,String> finiteValues = new LinkedHashMap<String,String>();
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("Select CODE,DESCRIPTION from FINITE_VALUES where TYPE_CODE = ? ", new Object[]{typeCode});

        while (rowSet.next()) {
            String code = rowSet.getString(1);
            String desc = rowSet.getString(2);
            finiteValues.put(code, desc);
        }

        return finiteValues;
    }


}
