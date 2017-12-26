package com.primus.profiles.controller;

import com.primus.abstracts.AbstractTransactionController;
import com.primus.common.ServiceFactory;
import com.primus.profiles.model.DoctorProfile;
import com.primus.profiles.model.ProfileInit;
import com.primus.profiles.service.ProfileService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class ProfileInitController extends AbstractTransactionController{

    @Override
    protected String getServiceName() {
        return "ProfileService";
    }

    @Override
    protected String getValidatorName() {
        return null;
    }

    @Override
    public PageResult submit(ModelObject object, String actionParam) {
        PageResult result = new PageResult() ;
        if("VIEWPROFILE".equalsIgnoreCase(actionParam)) {
            ProfileInit profileInit = (ProfileInit) object;
            ProfileService profileService = ServiceFactory.getProfileService() ;
            DoctorProfile profile = profileService.getDoctorProfile(profileInit.getDoctor() , getProductContext()) ;
            result.setObject(profile);
            result.setNextPageKey("");
            result.setResult(TransactionResult.Result.SUCCESS);

        }
        return super.submit(object, actionParam);
    }
}
