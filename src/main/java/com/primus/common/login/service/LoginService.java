package com.primus.common.login.service;

import com.primus.abstracts.AbstractDAO;
import com.primus.abstracts.AbstractService;
import com.primus.common.ProductContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.primus.common.login.model.Login;

@Component
public class LoginService  extends AbstractService {

    @Autowired
    LoginSQL loginSQL ;

    public  void registerLogin (ProductContext context) {
        loginSQL.registerLogin(context);
    }

    public ProductContext generateContext(String token )
    {
        return loginSQL.loggedInUser(token);

    }
    public  boolean validUser(Login login)
    {
        /*if ("admin".equalsIgnoreCase(login.getUsername()) && "password".equalsIgnoreCase(login.getPassword()))
            return true;
        else*/
            boolean valid =  loginSQL.isValidLogin(login.getUsername(),login.getPassword());

            return valid;
   //   return false;
    }


    @Override
    public AbstractDAO getDAO() {
        return null;
    }



}
