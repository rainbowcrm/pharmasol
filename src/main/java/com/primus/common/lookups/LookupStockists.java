package com.primus.common.lookups;

import com.primus.abstracts.PrimusModel;
import com.primus.admin.department.model.Department;
import com.primus.admin.department.service.DepartmentService;
import com.primus.common.CommonUtil;
import com.primus.common.ProductContext;
import com.primus.common.ServiceFactory;
import com.primus.externals.stockist.model.Stockist;
import com.primus.externals.stockist.model.StockistAssociation;
import com.primus.externals.stockist.service.StockistService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.ILookupService;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.utils.Utils;
import org.hibernate.service.spi.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LookupStockists  implements ILookupService {

    @Override
    public Map<String, String> lookupData(IRadsContext iRadsContext, String searchString, int i, int i1, String lookupParam, List<String> list) {
        Map<String,String> ans = new LinkedHashMap<String,String>();
        String condition = null;
        if (!Utils.isNull(searchString)) {
            searchString = searchString.replace("*", "%");
            condition =  " where stockist.name like  '" + searchString + "'" ;
        }
        StockistService service = ServiceFactory.getStockistService() ;
        List<? extends PrimusModel> companies = service.fetchAllLinked(condition, "",(ProductContext) iRadsContext);
        for (ModelObject obj :  companies) {
            ans.put(((StockistAssociation)obj).getStockist().getName(),((StockistAssociation)obj).getStockist().getName());
        }

        return ans;
    }

    @Override
    public IRadsContext generateContext(HttpServletRequest httpServletRequest, UIPage uiPage) {
        return CommonUtil.generateContext(httpServletRequest,uiPage);
    }
}
