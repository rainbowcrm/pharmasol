package com.primus.merchandise.item.service;

import com.primus.common.CommonUtil;
import com.primus.common.ProductContext;
import com.primus.merchandise.item.model.Item;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.IAjaxLookupService;
import com.techtrade.rads.framework.utils.Utils;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ItemSearchAjaxService   implements IAjaxLookupService {


    @Override
    public String lookupValues(Map<String, String> searchFields,
                               IRadsContext ctx) {
        JSONObject json = new JSONObject();
        try {
            String code = searchFields.get("Code");
            ItemService service = (ItemService) CommonUtil.getItemService();
            Item item = null;
            if(!Utils.isNullString(code)) {
                item = (Item) service.fetchOneActive( " where code='" + code + "' ", null, (ProductContext)ctx);
            }
            if (item != null) {
                json.put("Code", item.getCode());
                json.put("Name", item.getName());
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
