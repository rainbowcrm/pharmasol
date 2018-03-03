package com.primus.crm.appointmentplan.controller;

import com.primus.abstracts.AbstractTransactionController;
import com.techtrade.rads.framework.controller.abstracts.TransactionController;

public class AppointmentPlanController extends AbstractTransactionController {

    @Override
    protected String getServiceName() {
        return "TargetService";
    }


    @Override
    protected String getValidatorName() {
        return "TargetValidator";
    }




}
