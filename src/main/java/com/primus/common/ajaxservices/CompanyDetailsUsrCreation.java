package com.primus.common.ajaxservices;

import com.primus.common.CommonUtil;
import com.primus.common.company.model.Company;
import com.primus.common.company.service.CompanyService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.IAjaxLookupService;
import com.techtrade.rads.framework.utils.Utils;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class CompanyDetailsUsrCreation implements IAjaxLookupService {

    @Override
    public String lookupValues(Map<String, String> searchFields,
                               IRadsContext ctx) {
        JSONObject json = new JSONObject();
        try {
            String name = searchFields.get("companyName");
            String code = searchFields.get("companyCode");
            CompanyService service = (CompanyService) CommonUtil.getCompanyService();
            Company company = null;
            if(!Utils.isNullString(code)) {
                company = service.findByCode(code);
            } else if(!Utils.isNullString(name)) {
                company  =service.findByName(name);
            }
            if (company != null) {
                json.put("companyCode", company.getCode());
                json.put("companyName", company.getName());
                json.put("userNameSuffix", "@" + company.getCode());
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