package com.primus.crm.targetanalysis.controller;

import com.primus.abstracts.AbstractGeneralController;
import com.primus.common.ServiceFactory;
import com.primus.crm.target.service.TargetService;
import com.primus.crm.targetanalysis.model.TargetAnalyzer;
import com.techtrade.rads.framework.controller.abstracts.GeneralController;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

import java.util.Map;

public class TargetAnalyzerController extends AbstractGeneralController {

    @Override
    public PageResult submit(ModelObject modelObject) {
        return null;
    }

    @Override
    public PageResult submit(ModelObject object, String actionParam) {
        return super.submit(object, actionParam);
    }

    @Override
    public PageResult read(ModelObject object) {
        TargetAnalyzer analyzer = (TargetAnalyzer) object;
        int period = analyzer.getTarget();
        return  new PageResult() ;
    }

    public Map<String, String> getTargetPeriods()
    {
        TargetService targetService = ServiceFactory.getTargetService();
        return targetService.getAllTargetsForManager(getProductContext()) ;
    }

}
