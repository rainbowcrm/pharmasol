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
                "\t\t\t\t\t\"title\": \"All Day Event\",\n" +
                "\t\t\t\t\t\"start\": \"2018-01-11\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\t\"id\": 999,\n" +
                "\t\t\t\t\t\"title\": \"Morning Event\",\n" +
                "\t\t\t\t\t\"start\": \"2018-01-11 11:00\",\n" +
                "\t\t\t\t\t\"allDay\": false,\n" +
                "\t\t\t\t\t\"className\": \"info\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\t\"id\": 999,\n" +
                "\t\t\t\t\t\"title\": \"Afternoon Event\",\n" +
                "\t\t\t\t\t\"start\": \"2018-01-12 16:00\",\n" +
                "\t\t\t\t\t\"allDay\": false,\n" +
                "\t\t\t\t\t\"className\": \"info\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\t\"id\": 999,\n" +
                "\t\t\t\t\t\"title\": \"Timepass Mod Event\",\n" +
                "\t\t\t\t\t\"start\": \"2018-01-18 11:00\",\n" +
                "\t\t\t\t\t\"allDay\": false,\n" +
                "\t\t\t\t\t\"className\": \"info\"\n" +
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
