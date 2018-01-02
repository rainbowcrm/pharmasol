package com.primus.operations.iborder.controller;

import com.primus.abstracts.AbstractTransactionController;

public class InboundOrderController extends AbstractTransactionController {

    @Override
    protected String getServiceName() {
        return "InboundOrderService";
    }


    @Override
    protected String getValidatorName() {
        return "InboundOrderValidator";
    }

}
