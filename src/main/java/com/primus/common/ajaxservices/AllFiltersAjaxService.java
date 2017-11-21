package com.primus.common.ajaxservices;

import com.primus.common.CommonUtil;
import com.primus.common.filter.service.FilterService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.IAjaxLookupService;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;

public class AllFiltersAjaxService implements IAjaxLookupService {

    @Override
    public String lookupValues(Map<String, String> searchFields,
                               IRadsContext ctx) {
        JSONObject json = new JSONObject();
        try {
            String page=searchFields.get("page");
            String user = ctx.getUser();
            FilterService filterService = CommonUtil.getFilterService();
            Map<String,String> filters = filterService.findAllByPage(user, page) ;
            if (filters != null) {
                Iterator<String> it = filters.keySet().iterator();
                while(it.hasNext()) {
                    String key = it.next() ;
                    String value = filters.get(key);
                    json.put(key, value);
                }
            }
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return json.toString();
    }

    @Override
    public IRadsContext generateContext(HttpServletRequest request) {
        // TODO Auto-generated method stub
        return CommonUtil.generateContext(request,null);
    }



}
