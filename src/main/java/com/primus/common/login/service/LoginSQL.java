package com.primus.common.login.service;

import com.primus.common.ProductContext;
import com.primus.common.login.model.Login;
import com.techtrade.rads.framework.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

@Component
public class LoginSQL {


    class LoginWrapper  implements RowMapper<Login>
    {
        @Override
        public Login mapRow(ResultSet rs, int rowNum) throws SQLException {
            Login login = new Login();
            login.setUsername(rs.getString("user_id"));
            return login;
        }
    }




    @Autowired
    JdbcTemplate jdbcTemplate;


    public boolean isValidLogin(String userId, String password) {

        List<Login> logins =  jdbcTemplate.query("select * from users where user_id= ? and password = ? " , new Object[] { userId,password },
                new LoginWrapper() );
        if (Utils.isNullList(logins))
            return false;
        else return true ;
    }

    public  ProductContext loggedInUser(String tokenId) {

        try {

            String sql =   "SELECT TOKEN_ID,LOGGED_IN_TIME,USERS.USER_ID,COMPANIES.ID,COMPANIES.COMPANY_CODE,LOGIN_RECORDS.IS_MOBILE_LOGIN FROM LOGIN_RECORDS,USERS, COMPANIES  where LOGIN_RECORDS.USER_ID = USERS.USER_ID  " +
                    " AND USERS.COMPANY_ID = COMPANIES.ID  AND " +
                    " TOKEN_ID = ? AND  (EXPIRED_TIME is null && LOGGED_OFF_TIME is null) " ;

            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, new Object[]{tokenId});
            if (rs.next()) {
                ProductContext context = new ProductContext();
                context.setAuthenticated(true);
                context.setAuthenticationToken(rs.getString("TOKEN_ID"));
                context.setLogginTime(rs.getTimestamp("LOGGED_IN_TIME"));
                context.setUser(rs.getString("USER_ID"));
                context.setLoggedinCompany(rs.getInt("ID"));
                context.setLoggedinCompanyCode(rs.getString("COMPANY_CODE"));
                context.setMobileLogin(rs.getBoolean("IS_MOBILE_LOGIN"));
                return context;
            }else
                return null;

        }catch(Exception ex) {
           ex.printStackTrace();
        }
        return null;
    }

    public  void registerLogin (ProductContext context) {
        try {
            String sql =   "SELECT * FROM LOGIN_RECORDS where USER_ID = ?  " ;
            SqlRowSet rs  =jdbcTemplate.queryForRowSet(sql, new Object[] { context.getUser() });
             if (rs.next()) {
                updateLogin(context.getUser(), context.getAuthenticationToken(), context.getAuthenticationToken(),context.isMobileLogin());
            } else {
                registerFirstLogin(context.getUser(), context.getAuthenticationToken(), context.getAuthenticationToken(),context.isMobileLogin());
            }
        }catch(Exception ex) {
           ex.printStackTrace();
        }

    }



    public void deleteSessionforOtherUsers (String session,String user) {
        try {
            String sql = " DELETE FROM LOGIN_RECORDS WHERE SESSION_ID = ? AND USER_ID <> ?" ;
            jdbcTemplate.update(sql,new Object[] {session,user} );
        }catch(Exception ex) {
           ex.printStackTrace();
        }
    }

    public void registerFirstLogin(String user, String token, String session,boolean isMobile){
        ResultSet rs  = null ;
        try {
            deleteSessionforOtherUsers(session,user);

            String sql = " INSERT INTO LOGIN_RECORDS (TOKEN_ID,USER_ID,SESSION_ID,LOGGED_IN_TIME,IS_MOBILE_LOGIN) VALUES (?,?,?,?,?)" ;
            jdbcTemplate.update(sql, new Object[] {token,user,session,new Timestamp(new java.util.Date().getTime()),isMobile});
        }catch(Exception ex) {
           ex.printStackTrace();
        }
    }

    public  void updateLogin(String user, String token, String session,boolean isMobileLogin){
        ResultSet rs  = null ;
        try {
            deleteSessionforOtherUsers(session, user);
            String sql = " UPDATE LOGIN_RECORDS SET TOKEN_ID = ? ,  SESSION_ID = ? , LOGGED_IN_TIME = ? , " +
                    " LOGGED_OFF_TIME = null, EXPIRED_TIME = null, IS_MOBILE_LOGIN=?   WHERE USER_ID = ? ";
            jdbcTemplate.update(sql, new Object[] {session,session,new Timestamp(new java.util.Date().getTime()),isMobileLogin,user});
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

}
