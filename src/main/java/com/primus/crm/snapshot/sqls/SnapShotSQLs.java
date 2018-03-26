package com.primus.crm.snapshot.sqls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SnapShotSQLs {

    @Autowired
    JdbcTemplate jdbcTemplate;


}
