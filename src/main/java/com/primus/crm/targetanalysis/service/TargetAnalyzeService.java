package com.primus.crm.targetanalysis.service;

import com.primus.abstracts.AbstractDAO;
import com.primus.abstracts.AbstractService;
import com.primus.common.FVConstants;
import com.primus.common.ProductContext;
import com.primus.crm.target.model.Target;
import com.primus.crm.target.model.TotalVisitTarget;
import com.primus.crm.targetanalysis.sqls.TargetAnalyseSQLs;
import com.techtrade.rads.framework.model.graphdata.BarChartData;
import com.techtrade.rads.framework.model.graphdata.BarData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class TargetAnalyzeService extends AbstractService {

    @Autowired
    TargetAnalyseSQLs targetAnalyseSQLs ;

    @Override
    public AbstractDAO getDAO() {
        return null;
    }

    private int getExpectedCount(TotalVisitTarget totalVisitTarget)
    {
        return totalVisitTarget.getTargettedVisit();

    }

    private int getActualCount(TotalVisitTarget totalVisitTarget)
    {
        if  (totalVisitTarget.getDoctor() !=  null  ) {
            return targetAnalyseSQLs.countVisitsToDoctor(totalVisitTarget.getDoctor().getId(),totalVisitTarget.getTarget().getFromDate(),totalVisitTarget.getTarget().getToDate());
        } else if (totalVisitTarget.getStockist() != null  ) {
            return targetAnalyseSQLs.countVisitsToStockist(totalVisitTarget.getStockist().getId(),totalVisitTarget.getTarget().getFromDate(),totalVisitTarget.getTarget().getToDate());
        }else if(totalVisitTarget.getStore() != null)
            return targetAnalyseSQLs.countVisitsToStore(totalVisitTarget.getStore().getId(),totalVisitTarget.getTarget().getFromDate(),totalVisitTarget.getTarget().getToDate());
        else if (totalVisitTarget.getDoctorClass() != null)
            return targetAnalyseSQLs.countVisitsToDoctorClass(totalVisitTarget.getDoctorClass().getCode(),totalVisitTarget.getTarget().getFromDate(),totalVisitTarget.getTarget().getToDate());
        else if (FVConstants.VISIT_TO.ALL_DOCTOR.equalsIgnoreCase(totalVisitTarget.getVisitingType().getCode())) {
            return 3;
        }else if (FVConstants.VISIT_TO.ALL_STORE.equalsIgnoreCase(totalVisitTarget.getVisitingType().getCode())) {
            return 5;
        }else if (FVConstants.VISIT_TO.ALL_STOCKIST.equalsIgnoreCase(totalVisitTarget.getVisitingType().getCode())) {
            return 2;
        }
        return   3;
    }

    public BarChartData getTargetVisitBarChart(Target target, ProductContext context)
    {
        BarChartData barChartData  = new BarChartData() ;
        AtomicInteger maxY=  new AtomicInteger(0);
        if (target.getTotalVisitTargets() != null ) {
            target.getTotalVisitTargets().forEach( totalVisitTarget ->   {
                BarChartData.Division division = barChartData.new Division()  ;

                BarData barData = new BarData();
                barData.setText(totalVisitTarget.getVisitingDisplay());
                barData.setLegend("Target");
                barData.setColor("Red");
                int expectedTarget = getExpectedCount(totalVisitTarget);
                barData.setValue(expectedTarget);
                if (maxY.get() < expectedTarget)
                    maxY.set(expectedTarget) ;
                division.addBarData(barData);

                BarData actualBarData = new BarData();
                actualBarData.setText(totalVisitTarget.getVisitingDisplay());
                actualBarData.setLegend("Actuals");
                actualBarData.setColor("Blue");
                actualBarData.setValue(getActualCount(totalVisitTarget));
                division.addBarData(actualBarData);
                division.setDivisionTitle(totalVisitTarget.getVisitingDisplay());

                barChartData.addDivision(division);

            });
            BarChartData.Range range = barChartData.new Range();
            range.setyMax(maxY.get());
            range.setyMin(0);
            barChartData.setRange(range);
        }
        return barChartData ;
    }

}
