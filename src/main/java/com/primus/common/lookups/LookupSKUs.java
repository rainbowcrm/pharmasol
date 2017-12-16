package com.primus.common.lookups;

import com.primus.abstracts.PrimusModel;
import com.primus.common.CommonUtil;
import com.primus.common.ProductContext;
import com.primus.common.ServiceFactory;
import com.primus.merchandise.item.model.Item;
import com.primus.merchandise.item.model.Sku;
import com.primus.merchandise.item.service.ItemService;
import com.primus.merchandise.item.service.SkuService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.ILookupService;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.utils.Utils;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LookupSKUs  implements ILookupService {

    @Override
    public Map<String, String> lookupData(IRadsContext iRadsContext, String searchString, int i, int i1, String s1, List<String> additionalFields) {
        Map<String,String> ans = new LinkedHashMap<String,String>();
        String condition = null;
        if (!Utils.isNull(searchString)) {
            searchString = searchString.replace("*", "%");
            condition =  " where name like  '" + searchString + "'" ;
        }
        SkuService service = ServiceFactory.getSKUService();
        List<? extends PrimusModel> companies = service.fetchAllActive(condition, "",(ProductContext) iRadsContext);
        for (ModelObject obj :  companies) {
            StringBuffer key = new StringBuffer(((Sku)obj).getName());
            if(additionalFields != null && additionalFields.contains("code") )
                key.append("|" + ((Sku)obj).getCode());
            ans.put(key.toString(),((Sku)obj).getName() );
        }

        return ans;
    }

    @Override
    public IRadsContext generateContext(HttpServletRequest httpServletRequest, UIPage uiPage) {
        return CommonUtil.generateContext(httpServletRequest,uiPage);
    }
}
