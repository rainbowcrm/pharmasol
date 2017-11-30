package com.primus.common.lookups;

import com.primus.abstracts.PrimusModel;
import com.primus.common.CommonUtil;
import com.primus.common.ProductContext;
import com.primus.common.ServiceFactory;
import com.primus.externals.doctor.model.Doctor;
import com.primus.externals.doctor.model.DoctorAssociation;
import com.primus.externals.doctor.service.DoctorService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.ILookupService;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.utils.Utils;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LookupDoctors implements ILookupService {

    @Override
    public Map<String, String> lookupData(IRadsContext iRadsContext, String searchString, int i, int i1, String lookupParam, List<String> list) {
        Map<String,String> ans = new LinkedHashMap<String,String>();
        String condition = null;
        if (!Utils.isNull(searchString)) {
            searchString = searchString.replace("*", "%");
            condition =  " where doctor.name like  '" + searchString + "'" ;
        }
        DoctorService service = ServiceFactory.getDoctorService() ;
        List<? extends PrimusModel> results = service.fetchAllLinked(condition, "",(ProductContext) iRadsContext);
        for (ModelObject obj :  results) {
            ans.put(((DoctorAssociation)obj).getDoctor().getName(),((DoctorAssociation)obj).getDoctor().getName());
        }

        return ans;
    }

    @Override
    public IRadsContext generateContext(HttpServletRequest httpServletRequest, UIPage uiPage) {
        return CommonUtil.generateContext(httpServletRequest,uiPage);
    }
}
