package com.primus.crm.appointment.service;

import com.primus.common.CommonUtil;
import com.primus.common.ProductContext;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.IAjaxLookupService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class AllAppointmentAjaxService implements IAjaxLookupService {
    @Override
    public String lookupValues(Map<String, String> map, IRadsContext iRadsContext) {
        String ans = "\n" +
                "\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\ttitle: 'All Day Event',\n" +
                "\t\t\t\t\tstart: new Date(2018, 0, 12)\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\tid: 999,\n" +
                "\t\t\t\t\ttitle: 'Morning Event',\n" +
                "\t\t\t\t\tstart: new Date(2018, 0, 11, 8, 0),\n" +
                "\t\t\t\t\tallDay: false,\n" +
                "\t\t\t\t\tclassName: 'info'\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\tid: 999,\n" +
                "\t\t\t\t\ttitle: 'Afternoon Event',\n" +
                "\t\t\t\t\tstart: new Date(2018, 0, 11, 16, 0),\n" +
                "\t\t\t\t\tallDay: false,\n" +
                "\t\t\t\t\tclassName: 'info'\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\tid: 999,\n" +
                "\t\t\t\t\ttitle: 'Timepass Event',\n" +
                "\t\t\t\t\tstart: new Date(2018, 0, 18, 10, 0),\n" +
                "\t\t\t\t\tallDay: false,\n" +
                "\t\t\t\t\tclassName: 'info'\n" +
                "\t\t\t\t}\n" +
                "\t\t\t ";
        return ans ;
    }

    @Override
    public IRadsContext generateContext(HttpServletRequest httpServletRequest) {
        ProductContext ctx = new ProductContext();
        ctx.setAuthenticated(true);
        ctx.setAuthorized(true);
        return ctx;
      //  return CommonUtil.generateContext(httpServletRequest,null);
    }
}
