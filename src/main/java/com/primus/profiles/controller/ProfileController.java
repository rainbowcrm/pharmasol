package com.primus.profiles.controller;

import com.primus.abstracts.AbstractTransactionController;

public class ProfileController extends AbstractTransactionController{

    @Override
    protected String getServiceName() {
        return "ProfileService";
    }

    @Override
    protected String getValidatorName() {
        return null;
    }
}
