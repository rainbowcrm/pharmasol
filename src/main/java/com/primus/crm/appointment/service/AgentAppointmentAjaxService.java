package com.primus.crm.appointment.service;

import com.primus.common.CommonUtil;
import com.primus.common.FVConstants;
import com.primus.common.ProductContext;
import com.primus.common.ServiceFactory;
import com.primus.crm.appointment.model.Appointment;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.IAjaxLookupService;
import com.techtrade.rads.framework.utils.Utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AgentAppointmentAjaxService implements IAjaxLookupService {
    @Override
    public String lookupValues(Map<String, String> map, IRadsContext iRadsContext) {

        AppointmentService appointmentService = ServiceFactory.getAppointmentService() ;
        Date fromDate =  new java.util.Date(new java.util.Date().getTime() - ( 6l * 30l * 24l * 3600l * 1000l ));
        Date toDate =  new java.util.Date(new java.util.Date().getTime() + ( 6l * 30l * 24l * 3600l * 1000l ));
        List<Appointment> appointments = appointmentService.getAllAgentAppointment(iRadsContext.getUser(),fromDate,toDate,(ProductContext) iRadsContext) ;
        if (!Utils.isNullList(appointments)) {
            StringBuffer buffer = new StringBuffer("");
            appointments.forEach( appointment ->  {
                if (buffer.length()  >3  )
                    buffer.append(",\n") ;
                buffer.append("\n{\n");
                buffer.append("\"id\":" + appointment.getId() + ",\n");
                if (FVConstants.EXTERNAL_PARTY.DOCTOR.equalsIgnoreCase(appointment.getPartyType().getCode()))
                    buffer.append("\"title\":\"-" + appointment.getDoctor().getName() + "\",\n");
                else if (FVConstants.EXTERNAL_PARTY.STOCKIST.equalsIgnoreCase(appointment.getPartyType().getCode()))
                    buffer.append("\"title\":\"-" + appointment.getStockist().getName() + "\",\n");
                else
                    buffer.append("\"title\":\"-" + appointment.getStore().getName() + "\",\n");
                buffer.append("\"start\":\"" + appointmentService.getAppointmentTimeAsString(appointment,"yyyy-MM-dd","HH:mm") + "\",\n");
                buffer.append("\"allDay\":false,\n");
                if(appointment.getStatus().getCode().equalsIgnoreCase(FVConstants.APPT_STATUS.SCHEDULED) || appointment.getStatus().getCode().equalsIgnoreCase(FVConstants.APPT_STATUS.PLANNED) )
                    buffer.append("\"className\":\"scheduled\"\n");
                else if(appointment.getStatus().getCode().equalsIgnoreCase(FVConstants.APPT_STATUS.COMPLETED) ||  appointment.getStatus().getCode().equalsIgnoreCase(FVConstants.APPT_STATUS.CLOSED))
                    buffer.append("\"className\":\"completed\"\n");
                else if(appointment.getStatus().getCode().equalsIgnoreCase(FVConstants.APPT_STATUS.CANCELLED) ||  appointment.getStatus().getCode().equalsIgnoreCase(FVConstants.APPT_STATUS.CLOSEDCANCELED))
                    buffer.append("\"className\":\"cancelled\"\n");
                else if(appointment.getStatus().getCode().equalsIgnoreCase(FVConstants.APPT_STATUS.PENDING))
                    buffer.append("\"className\":\"pending\"\n");
                buffer.append("\n}");

            });
            return buffer.toString();

        }

        return "" ;
    }

    @Override
    public IRadsContext generateContext(HttpServletRequest httpServletRequest) {

        return CommonUtil.generateContext(httpServletRequest,null);
    }
}
