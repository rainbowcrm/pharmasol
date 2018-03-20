package com.primus.crm.targetanalysis.service;

import com.primus.abstracts.AbstractDAO;
import com.primus.abstracts.AbstractService;
import com.primus.common.FVConstants;
import com.primus.common.ProductContext;
import com.primus.crm.target.model.AgentVisitTarget;
import com.primus.crm.target.model.Target;
import com.primus.crm.target.model.TotalVisitTarget;
import com.primus.crm.targetanalysis.sqls.TargetAnalyseSQLs;
import com.techtrade.rads.framework.model.graphdata.BarChartData;
import com.techtrade.rads.framework.model.graphdata.BarData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.management.Agent;

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
    private int getExpectedCount(AgentVisitTarget agentVisitTarget)
    {
        return agentVisitTarget.getTargettedVisit();

    }

    private double getActualCount(TotalVisitTarget totalVisitTarget,ProductContext context)
    {
        if  (totalVisitTarget.getDoctor() !=  null  ) {
            return targetAnalyseSQLs.countVisitsToDoctor(totalVisitTarget.getDoctor().getId(),totalVisitTarget.getTarget().getFromDate(),
                    totalVisitTarget.getTarget().getToDate(),totalVisitTarget.getTarget().getLocation().getId(),context.getLoggedinCompany());
        } else if (totalVisitTarget.getStockist() != null  ) {
            return targetAnalyseSQLs.countVisitsToStockist(totalVisitTarget.getStockist().getId(),totalVisitTarget.getTarget().getFromDate(),
                    totalVisitTarget.getTarget().getToDate(),totalVisitTarget.getTarget().getLocation().getId(),context.getLoggedinCompany());
        }else if(totalVisitTarget.getStore() != null)
            return targetAnalyseSQLs.countVisitsToStore(totalVisitTarget.getStore().getId(),totalVisitTarget.getTarget().getFromDate(),
                    totalVisitTarget.getTarget().getToDate(),totalVisitTarget.getTarget().getLocation().getId(),context.getLoggedinCompany());
        else if (totalVisitTarget.getDoctorClass() != null) {
            double vistCount = targetAnalyseSQLs.countVisitsToDoctorClass(totalVisitTarget.getDoctorClass().getCode(), totalVisitTarget.getTarget().getFromDate(),
                    totalVisitTarget.getTarget().getToDate(),totalVisitTarget.getTarget().getLocation().getId(),context.getLoggedinCompany());
            double doctorsCount = targetAnalyseSQLs.countDoctorsForClass(totalVisitTarget.getDoctorClass().getCode(),totalVisitTarget.getTarget().getLocation().getId(),
                    context.getLoggedinCompany()) ;
            if(doctorsCount  == 0 ) return  0 ;
            return vistCount/doctorsCount;
        } else if (FVConstants.VISIT_TO.ALL_DOCTOR.equalsIgnoreCase(totalVisitTarget.getVisitingType().getCode())) {
            double vistCount =targetAnalyseSQLs.countVisitsToAllDoctors(totalVisitTarget.getTarget().getFromDate(),
                    totalVisitTarget.getTarget().getToDate(),totalVisitTarget.getTarget().getLocation().getId(),context.getLoggedinCompany());
            double doctorCount =targetAnalyseSQLs.countAllDoctors(totalVisitTarget.getTarget().getLocation().getId(),context.getLoggedinCompany());
            if(doctorCount  == 0 ) return  0 ;
            return vistCount/doctorCount;
        }else if (FVConstants.VISIT_TO.ALL_STORE.equalsIgnoreCase(totalVisitTarget.getVisitingType().getCode())) {
            double vistCount =targetAnalyseSQLs.countVisitsToAllStores(totalVisitTarget.getTarget().getFromDate(),
                    totalVisitTarget.getTarget().getToDate(),totalVisitTarget.getTarget().getLocation().getId(),context.getLoggedinCompany());
            double entityCount =targetAnalyseSQLs.countAllStores(totalVisitTarget.getTarget().getLocation().getId(),context.getLoggedinCompany());
            if(entityCount  == 0 ) return  0 ;
            return vistCount/entityCount;
        }else if (FVConstants.VISIT_TO.ALL_STOCKIST.equalsIgnoreCase(totalVisitTarget.getVisitingType().getCode())) {
            double vistCount =targetAnalyseSQLs.countVisitsToAllStockists(totalVisitTarget.getTarget().getFromDate(),
                    totalVisitTarget.getTarget().getToDate(),totalVisitTarget.getTarget().getLocation().getId(),context.getLoggedinCompany());
            double entityCount =targetAnalyseSQLs.countAllStockists(totalVisitTarget.getTarget().getLocation().getId(),context.getLoggedinCompany());
            if(entityCount  == 0 ) return  0 ;
            return vistCount/entityCount;
        }
        return   3;
    }

    private double getActualCount(AgentVisitTarget agentVisitTarget, ProductContext context)
    {
        if  (agentVisitTarget.getDoctor() !=  null  ) {
            return targetAnalyseSQLs.countAgentVisitsToDoctor(agentVisitTarget.getAgent().getUserId() , agentVisitTarget.getDoctor().getId(),agentVisitTarget.getTarget().getFromDate(),
                    agentVisitTarget.getTarget().getToDate(),agentVisitTarget.getTarget().getLocation().getId(),context.getLoggedinCompany());
        } else if (agentVisitTarget.getStockist() != null  ) {
            return targetAnalyseSQLs.countAgentVisitsToStockist(agentVisitTarget.getAgent().getUserId() , agentVisitTarget.getStockist().getId(),agentVisitTarget.getTarget().getFromDate(),
                    agentVisitTarget.getTarget().getToDate(),agentVisitTarget.getTarget().getLocation().getId(),context.getLoggedinCompany());
        }else if(agentVisitTarget.getStore() != null)
            return targetAnalyseSQLs.countAgentVisitsToStore(agentVisitTarget.getAgent().getUserId() ,agentVisitTarget.getStore().getId(),agentVisitTarget.getTarget().getFromDate(),
                    agentVisitTarget.getTarget().getToDate(),agentVisitTarget.getTarget().getLocation().getId(),context.getLoggedinCompany());
       else if (FVConstants.VISIT_TO.ALL_DOCTOR.equalsIgnoreCase(agentVisitTarget.getVisitingType().getCode())) {
            double vistCount =targetAnalyseSQLs.countAgentVisitsToAllDoctors(agentVisitTarget.getAgent().getUserId() ,agentVisitTarget.getTarget().getFromDate(),
                    agentVisitTarget.getTarget().getToDate(),agentVisitTarget.getTarget().getLocation().getId(),context.getLoggedinCompany());
            double doctorCount =targetAnalyseSQLs.countAllDoctors(agentVisitTarget.getTarget().getLocation().getId(),context.getLoggedinCompany());
            if(doctorCount  == 0 ) return  0 ;
            return vistCount/doctorCount;
        }else if (FVConstants.VISIT_TO.ALL_STORE.equalsIgnoreCase(agentVisitTarget.getVisitingType().getCode())) {
            double vistCount =targetAnalyseSQLs.countAgentVisitsToAllStores(agentVisitTarget.getAgent().getUserId() ,agentVisitTarget.getTarget().getFromDate(),
                    agentVisitTarget.getTarget().getToDate(),agentVisitTarget.getTarget().getLocation().getId(),context.getLoggedinCompany());
            double entityCount =targetAnalyseSQLs.countAllStores(agentVisitTarget.getTarget().getLocation().getId(),context.getLoggedinCompany());
            if(entityCount  == 0 ) return  0 ;
            return vistCount/entityCount;
        }else if (FVConstants.VISIT_TO.ALL_STOCKIST.equalsIgnoreCase(agentVisitTarget.getVisitingType().getCode())) {
            double vistCount =targetAnalyseSQLs.countAgentVisitsToAllStockists(agentVisitTarget.getAgent().getUserId() ,agentVisitTarget.getTarget().getFromDate(),
                    agentVisitTarget.getTarget().getToDate(),agentVisitTarget.getTarget().getLocation().getId(),context.getLoggedinCompany());
            double entityCount =targetAnalyseSQLs.countAllStockists(agentVisitTarget.getTarget().getLocation().getId(),context.getLoggedinCompany());
            if(entityCount  == 0 ) return  0 ;
            return vistCount/entityCount;
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
                actualBarData.setValue(getActualCount(totalVisitTarget,context));
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



    public BarChartData getAgentTargetVisitBarChart(Target target, ProductContext context)
    {
        BarChartData barChartData  = new BarChartData() ;
        AtomicInteger maxY=  new AtomicInteger(0);
        if (target.getAgentVisitTargets() != null ) {
            target.getAgentVisitTargets().forEach( agentVisitTarget->   {
                BarChartData.Division division = barChartData.new Division()  ;

                BarData barData = new BarData();
                barData.setText(agentVisitTarget.getVisitingDisplay());
                barData.setLegend("Target");
                barData.setColor("Red");
                int expectedTarget = getExpectedCount(agentVisitTarget);
                barData.setValue(expectedTarget);
                if (maxY.get() < expectedTarget)
                    maxY.set(expectedTarget) ;
                division.addBarData(barData);

                BarData actualBarData = new BarData();
                actualBarData.setText(agentVisitTarget.getVisitingDisplay());
                actualBarData.setLegend("Actuals");
                actualBarData.setColor("Blue");
                actualBarData.setValue(getActualCount(agentVisitTarget,context));
                division.addBarData(actualBarData);
                division.setDivisionTitle(agentVisitTarget.getVisitingDisplay());

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
