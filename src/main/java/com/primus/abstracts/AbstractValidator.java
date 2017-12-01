package com.primus.abstracts;

import com.primus.common.CommonUtil;
import com.primus.common.Externalize;
import com.primus.common.ProductContext;
import com.primus.common.company.model.Company;
import com.primus.common.company.service.CompanyService;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.utils.Utils;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.*;

@Component
public abstract class AbstractValidator {
    public static ResourceBundle resourceBundle = null;

    private static  Externalize externalize = new Externalize();

    public abstract String getBusinessKeyField() ;

    public  List<RadsError> adaptFromUI (PrimusModel model, ProductContext context)
    {
        if (model instanceof  PrimusBusinessModel) {
            CompanyService companyService = CommonUtil.getCompanyService();
            Company company = (Company) companyService.getById(context.getLoggedinCompany());
            ((PrimusBusinessModel) model).setCompany(company);
        }
        return null;
    }

    public  List<RadsError>  adaptToUI (PrimusModel model, ProductContext context)
    {
        return null;
    }

    public abstract List<RadsError> checkforMandatoryFields(PrimusModel model, ProductContext context);

    public abstract List<RadsError> checkforValueRanges(PrimusModel model, ProductContext context);

    public static ResourceBundle getResourceBundle(ProductContext context )
    {
        if (resourceBundle == null) {
            resourceBundle = ResourceBundle.getBundle("com.primus.common.ErrorMessages");
        }
        return resourceBundle ;
    }

    public static  RadsError getErrorforCode(ProductContext context,int errorCode,String ... params) {
        for (int i = 0 ; i < params.length ; i ++ ) {
            params[i] = externalize.externalize(context,params[i]);
        }
        String property = (String)getResourceBundle(context).getObject(String.valueOf(errorCode));
        String converted = MessageFormat.format(property, params);
        RadsError error = new RadsError(String.valueOf(errorCode),converted);

        return error;
    }

    public  List<RadsError> businessValidations(PrimusModel model, ProductContext context, AbstractService service){

        return Arrays.asList();
    }

    public  List<RadsError> validateForCreate(PrimusModel model, ProductContext context, AbstractService service){
        List<RadsError> results = new ArrayList<RadsError>();
        String businessKey = getBusinessKeyField();
        PrimusModel existing = service.getByBusinessKey(model,context) ;
        if(existing != null )
            results.add( getErrorforCode(context,CommonErrorCodes.ALREADY_EXIST,businessKey));
        List<RadsError> mandatoryErrors = checkforMandatoryFields(model,context);
        if(!Utils.isNullList(mandatoryErrors)) {
            results.addAll(mandatoryErrors);
        }
        List<RadsError> valueErrors =  checkforValueRanges(model,context) ;
        if(!Utils.isNullList(valueErrors)) {
            results.addAll(valueErrors);
        }
        results.addAll(businessValidations(model,context,service));
        return results;
    }


    public  List<RadsError> validateForUpdate(PrimusModel model, ProductContext context, AbstractService service) {
        List<RadsError> results = new ArrayList<RadsError>();
        String businessKey = getBusinessKeyField();
        PrimusModel existing = service.getByBusinessKey(model,context) ;
        context.setOldObject(existing);
        if (model instanceof  PrimusBusinessModel) {
            if (existing != null && ((PrimusBusinessModel)existing).getId() != ((PrimusBusinessModel)model).getId())
                results.add(getErrorforCode(context, CommonErrorCodes.ALREADY_EXIST, businessKey));
        }
        List<RadsError> mandatoryErrors = checkforMandatoryFields(model,context);
        if(!Utils.isNullList(mandatoryErrors)) {
            results.addAll(mandatoryErrors);
        }
        List<RadsError> valueErrors =  checkforValueRanges(model,context) ;
        if(!Utils.isNullList(valueErrors)) {
            results.addAll(valueErrors);
        }
        results.addAll(businessValidations(model,context,service));
        return results;
    }

    public  List<RadsError> validateForDelete(PrimusModel model, ProductContext context, AbstractService service) {

        return null;
    }

    public  List<RadsError> validateForCancel(PrimusModel model, ProductContext context, AbstractService service) {

        return null;
    }

}
