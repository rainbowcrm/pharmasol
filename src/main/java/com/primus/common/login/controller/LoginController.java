package com.primus.common.login.controller;

import com.primus.abstracts.AbstractGeneralController;
import com.primus.common.CommonUtil;
import com.primus.common.ProductContext;
import com.primus.util.ServiceLibrary;
import com.primus.common.login.model.Login;
import com.primus.common.login.service.LoginService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.utils.Utils;
import org.apache.log4j.LogManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginController extends AbstractGeneralController {

    String sessionId;

    @Override
    public IRadsContext generateContext(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, UIPage uiPage) {
        sessionId = httpServletRequest.getSession().getId() ;
        ProductContext ctx =  new ProductContext();
        ctx.setAuthenticated(true);
        return ctx;
    }

    @Override
    public IRadsContext generateContext(String s, UIPage uiPage) {
        ProductContext ctx =  new ProductContext();
        ctx.setAuthenticated(true);
        return ctx;
    }

    @Override
    public PageResult submit(ModelObject object, String actionParam) {
        return  new PageResult();
    }

    @Override
    public PageResult submit(ModelObject modelObject) {
        LoginService service = (LoginService) ServiceLibrary.services().getService("login") ;
        Login login =(Login)modelObject;
        if  (service.validUser(login)) {
            login.setAuthToken(sessionId);
            ProductContext ctx= CommonUtil.generateContext(login) ;
            ctx.setAuthenticationToken(sessionId);
            service.registerLogin(ctx);
            PageResult res = new PageResult();
            if( ctx.getLoggedinCompany()  == 1)
                res.setNextPageKey("landing");
            else
                res.setNextPageKey("applanding");
            LogManager.getLogger(this.getClass()).debug("logging in ");
            return res;
        } else {
            PageResult result = new PageResult();
            if (!Utils.isNullString(login.getUsername()))
                 result.addError(new RadsError("101","wrong "));
            return result;
        }
    }


}
