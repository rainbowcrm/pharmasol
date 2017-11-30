package com.primus.externals.doctor.controller;

import com.primus.abstracts.AbstractCRUDController;
import com.primus.admin.region.model.Location;
import com.primus.admin.region.model.Region;
import com.primus.admin.region.service.RegionService;
import com.primus.common.CommonUtil;
import com.primus.common.ServiceFactory;
import com.primus.externals.doctor.model.DoctorAssociation;
import com.primus.externals.doctor.service.DoctorService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DoctorAssociationController extends AbstractCRUDController {

    @Override
    protected String getServiceName() {
        return "DoctorService";
    }


    @Override
    protected String getValidatorName() {
        return "DoctorValidator";
    }

    @Override
    public PageResult submit(ModelObject object, String actionParam) {
        if ("Save".equalsIgnoreCase(actionParam)) {
            DoctorAssociation association = (DoctorAssociation) object;

            DoctorService service = ServiceFactory.getDoctorService();
            service.associateDoctor(association, getProductContext());
        }
        PageResult result = new PageResult();
        result.setNextPageKey("doctors");
        result.setResult(TransactionResult.Result.SUCCESS);
        return result;
    }


}
