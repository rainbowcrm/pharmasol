package com.primus.framework.nextup;

import com.primus.common.CommonUtil;
import com.primus.common.ProductContext;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.IAjaxLookupService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class NextUpGenAjaxService implements IAjaxLookupService {

    @Override
    public String lookupValues(Map<String, String> map, IRadsContext iRadsContext) {
        String program = map.get("program") ;
        String nextNumber = NextUpGenerator.getNextNumber(program,(ProductContext)iRadsContext,null,null,new java.util.Date());
        return nextNumber ;

    }

    @Override
    public IRadsContext generateContext(HttpServletRequest httpServletRequest) {
        return CommonUtil.generateContext(httpServletRequest,null);
    }
}
