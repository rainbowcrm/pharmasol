package com.primus.merchandise.item.service;

import com.primus.common.CommonUtil;
import com.primus.common.ProductContext;
import com.primus.merchandise.item.model.Item;
import com.primus.merchandise.item.model.Sku;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.IAjaxLookupService;
import com.techtrade.rads.framework.utils.Utils;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class SkuSearchAjaxService implements IAjaxLookupService {


    @Override
    public String lookupValues(Map<String, String> searchFields,
                               IRadsContext ctx) {
        JSONObject json = new JSONObject();
        try {
            String code = searchFields.get("Code");
            SkuService service = (SkuService) CommonUtil.getSKUService();
            Sku sku = null;
            if(!Utils.isNullString(code)) {
                sku = (Sku) service.fetchOneActive( " where code='" + code + "' ", null, (ProductContext)ctx);
            }
            if (sku != null) {
                json.put("Code", sku.getCode());
                json.put("Name", sku.getName());
                json.put("UOM.Id", sku.getUom().getId());
                json.put("UOM.Code", sku.getUom().getCode());
                json.put("RetailRate", sku.getRetailRate());
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

