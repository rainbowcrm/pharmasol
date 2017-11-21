package com.primus.common.lookups;

import com.primus.abstracts.PrimusModel;
import com.primus.common.CommonUtil;
import com.primus.common.ProductContext;
import com.primus.admin.department.model.Department;
import com.primus.admin.department.service.DepartmentService;
import com.primus.admin.division.model.Division;
import com.primus.admin.division.service.DivisionService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.ILookupService;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.utils.Utils;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LookupDepartments implements ILookupService {

    @Override
    public Map<String, String> lookupData(IRadsContext iRadsContext, String searchString, int from, int noRecords, String s1, List<String> list) {
        Map<String,String> ans = new LinkedHashMap<String,String>();
        String condition = null;
        if (!Utils.isNull(searchString)) {
            searchString = searchString.replace("*", "%");
            condition =  " where name like  '" + searchString + "'" ;
        }
        DepartmentService service = CommonUtil.getDepartmentService() ;
        List<? extends PrimusModel> companies = service.fetchAllActive(condition, "",(ProductContext) iRadsContext);
        for (ModelObject obj :  companies) {
            ans.put(((Department)obj).getName(),((Department)obj).getName());
        }

        return ans;
    }

    @Override
    public IRadsContext generateContext(HttpServletRequest httpServletRequest, UIPage uiPage) {
        return CommonUtil.generateContext(httpServletRequest,uiPage);
    }
}
