package com.primus.common.company.controller;

import com.primus.abstracts.AbstractListController;
import com.primus.common.CommonUtil;
import com.primus.common.FVConstants;

import java.util.Map;

public class CompanyListController extends AbstractListController {

    @Override
    protected String getServiceName() {
        return "CompanyService";
    }

    @Override
    protected String getAddEditPageKey() {
        return "compcreate";
    }

    @Override
    protected String getValidatorName() {
        return "CompanyValidator";
    }
}
