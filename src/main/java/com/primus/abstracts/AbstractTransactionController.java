package com.primus.abstracts;

import com.primus.admin.division.model.Division;
import com.primus.admin.division.service.DivisionService;
import com.primus.admin.reasoncode.model.ReasonCode;
import com.primus.admin.reasoncode.service.ReasonCodeService;
import com.primus.admin.region.model.Location;
import com.primus.admin.region.model.Region;
import com.primus.admin.region.service.RegionService;
import com.primus.common.CommonUtil;
import com.primus.common.ProductContext;
import com.primus.common.ServiceFactory;
import com.primus.common.application.Product;
import com.primus.util.ServiceLibrary;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.TransactionController;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.utils.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public  abstract  class AbstractTransactionController extends TransactionController {

    protected  abstract  String getServiceName();

    protected  abstract String getValidatorName();

    public String getCompanyName() {
        return  "Primus Solutions";
    }

    public  ProductContext getProductContext()
    {
        return (ProductContext) getContext() ;
    }

    protected AbstractService getService ()
    {
        String serviceName = getServiceName() ;
        AbstractService service = ServiceLibrary.services().getService(serviceName) ;
        return        service ;
    }

    private AbstractValidator getValidator()
    {
        String validatorName =  getValidatorName() ;
        AbstractValidator validator =  ServiceLibrary.services().getValidator(validatorName);
        return validator;

    }

    @Override
    public List<RadsError> validateforCreate() {
        return getValidator().validateForCreate((PrimusModel) getObject(),(ProductContext) getContext(),getService());
    }

    @Override
    public List<RadsError> validateforUpdate() {
        return getValidator().validateForUpdate((PrimusModel) getObject(),(ProductContext) getContext(),getService());
    }


    public List<RadsError> validateforDelete() {
        return getValidator().validateForDelete((PrimusModel) getObject(),(ProductContext) getContext(),getService());
    }

    @Override
    public List<RadsError> validateforCancel() {
        return getValidator().validateForCancel((PrimusModel) getObject(),(ProductContext) getContext(),getService());
    }

    @Override
    public PageResult create() {
        getService().create((PrimusModel) getObject(),(ProductContext) getContext()) ;
        return new PageResult();
    }

    @Override
    public PageResult delete() {
        return null;
    }

    @Override
    public PageResult read() {
        PageResult result = new PageResult() ;
        result.setObject(getService().getById(getObject().getPK()));
        result.setResult(TransactionResult.Result.SUCCESS);
        return result;
    }

    @Override
    public PageResult update() {
        getService().update((PrimusModel) getObject(),(ProductContext) getContext()) ;
        return new PageResult();
    }

    @Override
    public ModelObject populateFullObjectfromPK(ModelObject modelObject) {
        return  getService().getById(modelObject.getPK());
        //return null;
    }

    @Override
    public void setContext(IRadsContext context) {
        super.setContext(context);
    }

    @Override
    public IRadsContext generateContext(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, UIPage uiPage) {
        return CommonUtil.generateContext(httpServletRequest,uiPage);
    }

    @Override
    public IRadsContext generateContext(String s, UIPage uiPage) {
        return CommonUtil.generateContext(s,uiPage);
    }

    public Map<String,String> getFiniteValues(String groupCode)
    {
        return CommonUtil.getFiniteValues(groupCode);

    }

    public Map<String,String> getFiniteValuesWithSelect(String groupCode)
    {
        return CommonUtil.getFiniteValuesWithSelect(groupCode);

    }

    @Override
    public List<RadsError> adaptfromUI(ModelObject modelObject) {
        return getValidator().adaptFromUI((PrimusModel) modelObject, (ProductContext) getContext());
    }

    @Override
    public List<RadsError> adapttoUI(ModelObject modelObject) {
        return  getValidator().adaptToUI((PrimusModel) modelObject, (ProductContext) getContext());

    }

    @Override
    public PageResult print() {
        return null;
    }




    public Map<String,String> getReasonCodes(String groupCode)
    {
        Map<String,String> ans = new LinkedHashMap<>();
        ReasonCodeService service =ServiceFactory.getReasonCodeService();
        List<ReasonCode> reasonCodes = (List<ReasonCode>)service.fetchAllActive(" where reasonType.code ='" + groupCode  + "'","",getProductContext());
        if(!Utils.isNullList(reasonCodes)) {
            reasonCodes.forEach( reasonCode ->  {
                ans.put(String.valueOf(reasonCode.getId()),reasonCode.getReason());
            });
        }
        return ans ;
    }

    public Map<String,String> getAllRegions()
    {
        Map ans = new LinkedHashMap() ;
        RegionService service = ServiceFactory.getRegionService() ;
        List<Region> regions =(List<Region> ) service.fetchAllActive(null,"name", getProductContext()) ;
        regions.forEach( region ->  {
            ans.put(String.valueOf(region.getId()),region.getName());
        });
        return ans;

    }

    public Map<String,String> getAllLocations()
    {
        Map ans = new LinkedHashMap() ;
        RegionService service = ServiceFactory.getRegionService() ;
        List<Location> locations =(List<Location> ) service.fetchAllActive("com.primus.admin.region.model.Location",null,"name", getProductContext()) ;
        locations.forEach( location ->  {
            ans.put(String.valueOf(location.getId()),location.getName());
        });
        return ans;

    }

    public Map<String,String> getAllDivisions(String useSelect)
    {
        Map ans = new LinkedHashMap() ;
        DivisionService service = ServiceFactory.getDivisionService();
        if( "true".equalsIgnoreCase(useSelect) )
            ans.put("-1","--Select one--");
        List<Division> locations =(List<Division> ) service.fetchAllActive("com.primus.admin.region.model.Division",null,"name", getProductContext()) ;
        locations.forEach( location ->  {
            ans.put(String.valueOf(location.getId()),location.getName());
        });
        return ans;

    }
}
