package com.primus.crm.appointmentplan.controller;

import com.primus.abstracts.AbstractTransactionController;
import com.primus.common.ServiceFactory;
import com.primus.crm.appointment.model.Appointment;
import com.primus.crm.appointmentplan.AppointmentPlanner;
import com.primus.crm.appointmentplan.model.AppointmentPlan;
import com.primus.crm.target.model.Target;
import com.primus.crm.target.service.TargetService;
import com.techtrade.rads.framework.controller.abstracts.TransactionController;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class AppointmentPlanController extends AbstractTransactionController {

    @Override
    protected String getServiceName() {
        return "TargetService";
    }


    @Override
    protected String getValidatorName() {
        return "TargetValidator";
    }



    @Override
    public PageResult submit(ModelObject object, String actionParam) {
        if("confirm".equalsIgnoreCase(actionParam))  {
            TargetService targetService  = ServiceFactory.getTargetService()   ;
            Target target =  (Target)targetService.getById(((AppointmentPlan)object).getTarget().getId());
            AppointmentPlanner planner = new AppointmentPlanner() ;
            AppointmentPlan plan  = planner.generatePlan(target,getProductContext());
            plan.setTarget(target);
            targetService.createAppointments(plan,getProductContext()) ;
            target.setInstanceCreated(new Boolean(true));
            targetService.update(target,getProductContext());
            PageResult result  = new PageResult( );
            result.setResult(TransactionResult.Result.SUCCESS);
            result.setNextPageKey("apptcalendar");
            return result;
        }
        if("replan".equalsIgnoreCase(actionParam))
        {
            TargetService targetService  = ServiceFactory.getTargetService()   ;
            Target target =  (Target)targetService.getById(((AppointmentPlan)object).getTarget().getId());
            PageResult result  = new PageResult( );
            result.setResult(TransactionResult.Result.SUCCESS);
            result.setObject(target);
            result.setMode(Mode.UPDATE);
            result.setNextPageKey("newtarget");
            return result;
        }

        return super.submit(object, actionParam);
    }
}
