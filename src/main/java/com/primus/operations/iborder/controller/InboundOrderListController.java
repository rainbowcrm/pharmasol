package com.primus.operations.iborder.controller;

import com.primus.abstracts.AbstractListController;

public class InboundOrderListController extends AbstractListController {

    @Override
    protected String getServiceName() {
        return "InboundOrderService";
    }

    @Override
    protected String getValidatorName() {
        return "InboundOrderValidator";
    }

    @Override
    protected String getAddEditPageKey() {
        return "newinboundorder";
    }
}
