package com.primus.externals.stockist.controller;

import com.primus.abstracts.AbstractCRUDController;
import com.primus.common.ServiceFactory;
import com.primus.externals.stockist.model.StockistAssociation;
import com.primus.externals.stockist.service.StockistService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class StockistAssociationController extends AbstractCRUDController {

    @Override
    protected String getServiceName() {
        return "StockistService";
    }


    @Override
    protected String getValidatorName() {
        return "StockistValidator";
    }

    @Override
    public PageResult submit(ModelObject object, String actionParam) {
        if ("Save".equalsIgnoreCase(actionParam)) {
            StockistAssociation association = (StockistAssociation) object;

            StockistService service = ServiceFactory.getStockistService();
            service.associateStockist(association, getProductContext());
        }
        PageResult result = new PageResult();
        result.setNextPageKey("stockists");
        result.setResult(TransactionResult.Result.SUCCESS);
        return result;
    }
}
