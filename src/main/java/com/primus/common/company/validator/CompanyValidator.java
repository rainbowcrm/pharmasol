package com.primus.common.company.validator;

import com.primus.abstracts.*;
import com.primus.common.ProductContext;
import com.primus.common.company.model.Company;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.utils.Utils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompanyValidator  extends AbstractValidator{

    @Override
    public String getBusinessKeyField() {
        return "Company code";
    }

    @Override
    public List<RadsError> checkforMandatoryFields(PrimusModel model, ProductContext context) {
        Company company = (Company) model ;
        List<RadsError> results = new ArrayList<RadsError>();
        if(Utils.isNullString(company.getCode())) {
            results.add( getErrorforCode(context, CommonErrorCodes.CANNOT_BE_EMPTY,"Company_Code"));
        }
        if(Utils.isNullString(company.getName())) {
            results.add( getErrorforCode(context, CommonErrorCodes.CANNOT_BE_EMPTY,"Company_Name"));
        }
        if(Utils.isNullString(company.getAdminName())) {
            results.add( getErrorforCode(context, CommonErrorCodes.CANNOT_BE_EMPTY,"Admin_Name"));
        }
        return results;
    }

    @Override
    public List<RadsError> checkforValueRanges(PrimusModel model, ProductContext context) {

        return null;
    }

    @Override
    public List<RadsError> adaptFromUI(PrimusModel model, ProductContext context) {
        return null;
    }

    @Override
    public List<RadsError> validateForCreate(PrimusModel model, ProductContext context, AbstractService service) {
       List<RadsError> errors =  super.validateForCreate(model,context,service) ;
       return errors;
    }

    @Override
    public List<RadsError> validateForUpdate(PrimusModel model, ProductContext context, AbstractService service) {
        List<RadsError> errors =  super.validateForUpdate(model,context,service) ;
        return errors;
    }

    @Override
    public List<RadsError> validateForDelete(PrimusModel model, ProductContext context, AbstractService service) {
        List<RadsError> errors =  super.validateForDelete(model,context,service) ;
        return errors;
    }
}
