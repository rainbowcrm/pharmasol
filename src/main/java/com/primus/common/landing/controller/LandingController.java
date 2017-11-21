package com.primus.common.landing.controller;

import com.primus.abstracts.AbstractGeneralController;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class LandingController extends AbstractGeneralController {

    @Override
    public PageResult submit(ModelObject object, String actionParam) {
        return super.submit(object, actionParam);
    }

    @Override
    public PageResult submit(ModelObject modelObject) {
        return new PageResult();
    }
}
