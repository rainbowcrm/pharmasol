package com.primus.common.lookups;

import com.primus.abstracts.PrimusModel;
import com.primus.common.CommonUtil;
import com.primus.common.ProductContext;
import com.primus.common.company.model.Company;
import com.primus.common.company.service.CompanyService;
import com.primus.util.ServiceLibrary;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.ILookupService;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.utils.Utils;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LookupCompanies implements ILookupService {

    @Override
    public Map<String, String> lookupData(IRadsContext ctx, String searchString, int from, int noRecords, String s1, List<String> list) {
        Map<String,String> ans = new LinkedHashMap<String,String>();
        String condition = null;
        if (!Utils.isNull(searchString)) {
            searchString = searchString.replace("*", "%");
            condition =  " where name like  '" + searchString + "'" ;
        }
        CompanyService service =(CompanyService) ServiceLibrary.services().getService("CompanyService") ;
        List<? extends PrimusModel> companies = service.listData(from, from  + noRecords, condition,(ProductContext) ctx,null);
        for (ModelObject obj :  companies) {
            ans.put(((Company)obj).getName(),((Company)obj).getName());
        }

        return ans;
    }

    @Override
    public IRadsContext generateContext(HttpServletRequest httpServletRequest, UIPage uiPage) {
        return CommonUtil.generateContext(httpServletRequest,uiPage);
    }
}
