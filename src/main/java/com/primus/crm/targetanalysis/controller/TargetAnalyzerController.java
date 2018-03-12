package com.primus.crm.targetanalysis.controller;

import com.primus.abstracts.AbstractGeneralController;
import com.primus.common.ServiceFactory;
import com.primus.crm.target.model.Target;
import com.primus.crm.target.service.TargetService;
import com.primus.crm.targetanalysis.model.TargetAnalyzer;
import com.primus.crm.targetanalysis.service.TargetAnalyzeService;
import com.techtrade.rads.framework.controller.abstracts.GeneralController;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.graphdata.BarChartData;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import javafx.scene.chart.BarChart;

import java.util.Map;

public class TargetAnalyzerController extends AbstractGeneralController {

    @Override
    public PageResult submit(ModelObject modelObject) {
        return new PageResult();
    }

    @Override
    public PageResult submit(ModelObject object, String actionParam) {
        return super.submit(object, actionParam);
    }

    @Override
    public PageResult read(ModelObject object) {
        TargetAnalyzer analyzer = (TargetAnalyzer) object;
        int period = analyzer.getTarget();
        TargetAnalyzeService targetAnalyzeService =  ServiceFactory.getTargetAnalyzeService() ;
        TargetService targetService = ServiceFactory.getTargetService() ;
        Target target   = (Target)targetService.getById(period);
        BarChartData barChartData = targetAnalyzeService.getTargetVisitBarChart(target,getProductContext());
        analyzer.setTargetData(barChartData);
        PageResult result =new PageResult() ;
        result.setObject(analyzer);
        return  result ;
    }

    public Map<String, String> getTargetPeriods()
    {
        TargetService targetService = ServiceFactory.getTargetService();
        return targetService.getAllTargetsForManager(getProductContext()) ;
    }

}
