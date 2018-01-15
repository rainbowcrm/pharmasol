package com.primus.abstracts;

import com.primus.admin.region.model.Region;
import com.primus.admin.region.service.RegionService;
import com.primus.common.CommonUtil;
import com.primus.common.ProductContext;
import com.primus.common.ServiceFactory;
import com.primus.util.ServiceLibrary;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.GeneralController;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.utils.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractGeneralController extends GeneralController {

    @Override
    public IRadsContext generateContext(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, UIPage uiPage) {
        return CommonUtil.generateContext(httpServletRequest,uiPage);
    }

    @Override
    public IRadsContext generateContext(String s, UIPage uiPage) {
        return CommonUtil.generateContext(s,uiPage);
    }

    public String getCompanyName() {
        if(((ProductContext) getContext()).getCompany() != null)
            return  ((ProductContext) getContext()).getCompany().getName();
        else
            return  ((ProductContext) getContext()).getLoggedinCompanyCode();
    }

    public String  getLogo() {
        if(((ProductContext) getContext()).getCompany() != null) {
            String logo =  ((ProductContext) getContext()).getCompany().getLogo() ;  //./images/logo1.gif
            if (Utils.isNull(logo))
                return "./images/logo1.gif";
            else  {
                String serverURL = ServiceLibrary.services().getApplicationManager().getDocServer();
                return serverURL + logo;
            }
        }
        return "./images/logo1.gif";

    }

    public Map<String,String> getAllRegions()
    {
        Map ans = new LinkedHashMap() ;
        RegionService service = ServiceFactory.getRegionService() ;
        List<Region> regions =(List<Region> ) service.fetchAllActive(null,"name", getProductContext()) ;
        regions.forEach( region ->  {
            ans.put(String.valueOf(region.getId()),region.getName());
        });
        return ans;

    }

    public  ProductContext getProductContext()
    {
        return (ProductContext) getContext() ;
    }

}
