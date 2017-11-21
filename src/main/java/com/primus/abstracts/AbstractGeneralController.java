package com.primus.abstracts;

import com.primus.common.CommonUtil;
import com.primus.common.ProductContext;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.GeneralController;
import com.techtrade.rads.framework.ui.abstracts.UIPage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractGeneralController extends GeneralController {

    @Override
    public IRadsContext generateContext(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, UIPage uiPage) {
        return CommonUtil.generateContext(httpServletRequest,uiPage);
    }

    @Override
    public IRadsContext generateContext(String s, UIPage uiPage) {
        return CommonUtil.generateContext(s,uiPage);
    }

    public String getCompanyName() {
        if(((ProductContext) getContext()).getCompany() != null)
            return  ((ProductContext) getContext()).getCompany().getName();
        else
            return  ((ProductContext) getContext()).getLoggedinCompanyCode();
    }
}
