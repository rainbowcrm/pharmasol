package com.primus.common.lookups;

import com.primus.abstracts.AbstractService;
import com.primus.abstracts.PrimusModel;
import com.primus.common.CommonUtil;
import com.primus.common.ProductContext;
import com.primus.common.ServiceFactory;
import com.primus.externals.doctor.model.Doctor;
import com.primus.externals.doctor.service.DoctorService;
import com.primus.externals.stockist.model.Stockist;
import com.primus.externals.stockist.service.StockistService;
import com.primus.externals.store.model.Store;
import com.primus.externals.store.service.StoreService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.ILookupService;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.utils.Utils;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LookupExternalParties implements ILookupService {

    @Override
    public Map<String, String> lookupData(IRadsContext iRadsContext, String searchString, int i, int i1, String lookupParam, List<String> list) {
        Map<String,String> ans = new LinkedHashMap<String,String>();
        String condition = null;



        if("PTSTCK".equalsIgnoreCase(lookupParam)) {
            if (!Utils.isNull(searchString)) {
                searchString = searchString.replace("*", "%");
                condition =  " where stockist.name like  '" + searchString + "'" ;
            }
            StockistService service = ServiceFactory.getStockistService();
            List<? extends PrimusModel> companies = service.fetchAllLinked(condition, "",(ProductContext) iRadsContext);
            for (ModelObject obj :  companies) {
                ans.put(((Stockist)obj).getName(),((Stockist)obj).getName());
            }
        }else if ("PTSTRE".equalsIgnoreCase(lookupParam)) {
            if (!Utils.isNull(searchString)) {
                searchString = searchString.replace("*", "%");
                condition =  " where store.name like  '" + searchString + "'" ;
            }
            StoreService service = ServiceFactory.getStoreService();
            List<? extends PrimusModel> companies = service.fetchAllLinked(condition, "",(ProductContext) iRadsContext);
            for (ModelObject obj :  companies) {
                ans.put(((Store)obj).getName(),((Store)obj).getName());
            }
        }else if ("PTDCT".equalsIgnoreCase(lookupParam)) {
            if (!Utils.isNull(searchString)) {
                searchString = searchString.replace("*", "%");
                condition =  " where doctor.name like  '" + searchString + "'" ;
            }
            DoctorService service = ServiceFactory.getDoctorService();
            List<? extends PrimusModel> companies = service.fetchAllLinked(condition, "",(ProductContext) iRadsContext);
            for (ModelObject obj :  companies) {
                ans.put(((Doctor)obj).getName(),((Doctor)obj).getName());
            }
        }


        return ans;
    }

    @Override
    public IRadsContext generateContext(HttpServletRequest httpServletRequest, UIPage uiPage) {
        return CommonUtil.generateContext(httpServletRequest,uiPage);
    }
}
