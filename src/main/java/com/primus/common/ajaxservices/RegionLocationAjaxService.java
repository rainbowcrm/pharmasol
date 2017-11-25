package com.primus.common.ajaxservices;

import com.primus.admin.region.model.Location;
import com.primus.admin.region.service.RegionService;
import com.primus.common.CommonUtil;
import com.primus.common.ProductContext;
import com.primus.common.ServiceFactory;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.IAjaxLookupService;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class RegionLocationAjaxService implements IAjaxLookupService{


    @Override
    public String lookupValues(Map<String, String> searchFields, IRadsContext iRadsContext) {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        String regionId=searchFields.get("region");
        RegionService regionService = ServiceFactory.getRegionService() ;
        List<Location> locations =(List<Location> ) regionService.fetchAllActive("com.primus.admin.region.model.Location"," where region.id = " + regionId,"name", (ProductContext)iRadsContext ) ;
        locations.forEach( location ->  {
            JSONObject state = new JSONObject();
            String code = String.valueOf(location.getId()) ;
            String name = location.getName() ;
            state.put("value", code) ;
            state.put("text", name) ;
            array.put(state);
        });

        return "{\"location\":" + array + "}" ;

    }

    @Override
    public IRadsContext generateContext(HttpServletRequest request) {
        return CommonUtil.generateContext(request,null);
    }
}
