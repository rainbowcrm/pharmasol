package com.primus.crm.snapshot.service;

import com.primus.common.CommonUtil;
import com.primus.common.ProductContext;
import com.primus.common.ServiceFactory;
import com.primus.crm.snapshot.model.SnapShot;
import com.primus.util.ServiceLibrary;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.IAjaxLookupService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class SnapShotAjaxService implements IAjaxLookupService {

    @Override
    public String lookupValues(Map<String, String> map, IRadsContext iRadsContext) {
        SnapShotService service = ServiceLibrary.services().getSnapShotService() ;
        SnapShot snapShot = service.getSnapShot((ProductContext) iRadsContext , new java.util.Date()) ;

        return snapShot.toJSON();
    }

    @Override
    public IRadsContext generateContext(HttpServletRequest httpServletRequest) {
        return CommonUtil.generateContext(httpServletRequest,null);
    }
}
