package com.primus.common.user.controller;

import com.primus.abstracts.AbstractListController;
import com.primus.common.ProductContext;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

import java.util.List;

public class UserListController extends AbstractListController {

    @Override
    protected String getServiceName() {
        return "UserService";
    }

    @Override
    protected String getAddEditPageKey() {
        ProductContext ctx = (ProductContext) getContext() ;
        if (ctx.getLoggedinCompany() == 1)
            return "newuser";
        else
            return "newappuser";
    }

    @Override
    protected String getValidatorName() {
        return "UserValidator";
    }
}
