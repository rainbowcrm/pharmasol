package com.primus.operations.iborder.validator;

import com.primus.abstracts.AbstractValidator;
import com.primus.abstracts.CommonErrorCodes;
import com.primus.abstracts.PrimusModel;
import com.primus.common.FVConstants;
import com.primus.common.ProductContext;
import com.primus.common.ServiceFactory;
import com.primus.framework.nextup.NextUpGenerator;
import com.primus.merchandise.item.model.Sku;
import com.primus.merchandise.item.service.SkuService;
import com.primus.operations.iborder.model.InboundOrder;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.utils.Utils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class InboundOrderValidator extends AbstractValidator {

    @Override
    public String getBusinessKeyField() {
        return "DocNo";
    }

    @Override
    public List<RadsError> checkforMandatoryFields(PrimusModel model, ProductContext context) {
        InboundOrder order = (InboundOrder) model;
        List<RadsError> results = new ArrayList<RadsError>();
        if (Utils.isNull(order.getDocNo())) {
            results.add(getErrorforCode(context, CommonErrorCodes.CANNOT_BE_EMPTY, "Doc_No"));
        }

        if (Utils.isNull(order.getRequestedDate())) {
            results.add(getErrorforCode(context, CommonErrorCodes.CANNOT_BE_EMPTY, "Requested_Date"));
        }

        if (Utils.isNull(order.getRegion())) {
            results.add(getErrorforCode(context, CommonErrorCodes.CANNOT_BE_EMPTY, "Region"));
        }


        if (Utils.isNull(order.getDivision())) {
            results.add(getErrorforCode(context, CommonErrorCodes.CANNOT_BE_EMPTY, "Division"));
        }
        if(Utils.isNullCollection(order.getInboundOrderLines()))  {
            results.add(getErrorforCode(context, CommonErrorCodes.CANNOT_BE_EMPTY, "Order_Lines"));
        } else
        {
            order.getInboundOrderLines().forEach(line -> {
                if (Utils.isNull(line.getSku()))  {
                    results.add(getErrorforCode(context, CommonErrorCodes.CANNOT_BE_EMPTY, "Sku"));
                }

                if (Utils.isNull(line.getQty()))  {
                    results.add(getErrorforCode(context, CommonErrorCodes.CANNOT_BE_EMPTY, "Qty"));
                }
            });
        }


        return results;

    }

    @Override
    public List<RadsError> checkforValueRanges(PrimusModel model, ProductContext context) {
        return null;
    }

    @Override
    public List<RadsError> adaptFromUI(PrimusModel model, ProductContext context) {
        super.adaptFromUI(model, context);
        InboundOrder object = (InboundOrder) model;
        SkuService skuService = ServiceFactory.getSKUService() ;
        if(Utils.isNull(object.getDocNo() )){
            String no = NextUpGenerator.getNextNumber(FVConstants.PGM_INBOUNDORDER,context,null,null,null);
           object.setDocNo(no);
        }
        if (!Utils.isNullCollection(object.getInboundOrderLines())) {
            AtomicInteger index = new AtomicInteger(0);
            object.getInboundOrderLines().forEach(line -> {
                //payScaleSplit.setLineNumber(index.incrementAndGet());
                line.setCompany(object.getCompany());
                line.setInboundOrder(object);

                Sku sku = (Sku)skuService.getFullObject(line.getSku(),context) ;
                line.setSku(sku);

            });
        }
        return null;
    }

}
