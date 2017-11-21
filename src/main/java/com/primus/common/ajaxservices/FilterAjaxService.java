package com.primus.common.ajaxservices;

import com.primus.common.CommonUtil;
import com.primus.common.ProductContext;
import com.primus.common.filter.model.PRMFilter;
import com.primus.common.filter.model.PRMFilterDetails;
import com.primus.common.filter.service.FilterService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.IAjaxLookupService;
import com.techtrade.rads.framework.utils.Utils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class FilterAjaxService implements IAjaxLookupService {

    @Override
    public String lookupValues(Map<String, String> searchFields, IRadsContext ctx) {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        PRMFilter filter =null ;
        try {
            String filterId=searchFields.get("filterId");
            String filterName=searchFields.get("filterName");
            String page=searchFields.get("page");
            FilterService filterService = CommonUtil.getFilterService();
            if (!Utils.isNullString(filterId) && Utils.isPositiveInt(filterId)) {
                filter =(PRMFilter) filterService.getById(Integer.parseInt(filterId));
            } else if (!Utils.isNullString(filterName) &&  !Utils.isNullString(page)) {
                filter =(PRMFilter) filterService.findByKey(ctx.getUser(), page, filterName);
            }
            AtomicInteger index = new AtomicInteger(0);
            ProductContext context = (ProductContext) ctx;
            if (context.isMobileLogin()) {
                if (filter != null) {

                    for (PRMFilterDetails det : filter.getPrimusFilterDetails()) {
                        JSONObject filtJSON = new JSONObject();
                        filtJSON.put("field", det.getField());
                        filtJSON.put("value", det.getValue());
                        array.put(index.getAndIncrement(), filtJSON);
                    }
                }
                json.put("filter", array);
            } else {
                if (filter != null) {
                    json.put("FilterName", filter.getName());
                    for (PRMFilterDetails det : filter.getPrimusFilterDetails()) {
                        json.put(det.getField(), det.getValue());
                    }
                }
            }

        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return json.toString();

    }


    @Override
    public IRadsContext generateContext(HttpServletRequest request) {
        return CommonUtil.generateContext(request,null);
    }




}

