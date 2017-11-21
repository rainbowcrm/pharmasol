package com.primus.common.company.controller;

import com.primus.abstracts.AbstractCRUDController;
import com.primus.common.CommonUtil;
import com.primus.common.FVConstants;
import com.primus.common.ajaxservices.CountryStateAjaxService;
import com.primus.common.company.model.Company;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.utils.Utils;

import java.util.*;

public class CompanyController extends AbstractCRUDController {

    @Override
    protected String getServiceName() {
        return "CompanyService";
    }


    @Override
    protected String getValidatorName() {
        return "CompanyValidator";
    }

    public Map<String,String> getIndustryTypes()
    {
        return CommonUtil.getFiniteValues(FVConstants.FV_INDUSTRY_TYPE);

    }

    @Override
    public List<RadsError> validateforCreate() {
        return super.validateforCreate();
    }

    public Map<String,String> getBusinessTypes()
    {
        return CommonUtil.getFiniteValues(FVConstants.FV_BUSINESS_TYPE);

    }

    public Map <String, String > getStates() {
        Map<String, String> ans = new HashMap<String, String>();
        if(object != null  && (!Utils.isNullString(((Company)object).getCountry()))){
            CountryStateAjaxService serv = new CountryStateAjaxService();
            return serv.findStates(((Company)object).getCountry());
        }
        return ans;
    }

    public Map <String, String > getCountries() {
        Map <String, String > ans = new TreeMap();
        String[] locales = Locale.getISOCountries();
        for (String countryCode : locales) {
            Locale obj = new Locale("", countryCode);
            //if("IN".equalsIgnoreCase(obj.getCountry()) || "US".equalsIgnoreCase(obj.getCountry())) {
            ans.put(obj.getCountry(), obj.getDisplayCountry()) ;
            //}
        }
        return ans ;
    }

}
