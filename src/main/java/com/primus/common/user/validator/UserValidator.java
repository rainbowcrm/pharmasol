package com.primus.common.user.validator;

import com.primus.abstracts.AbstractService;
import com.primus.abstracts.AbstractValidator;
import com.primus.abstracts.CommonErrorCodes;
import com.primus.abstracts.PrimusModel;
import com.primus.common.CommonUtil;
import com.primus.common.ProductContext;
import com.primus.common.company.model.Company;
import com.primus.common.company.service.CompanyService;
import com.primus.common.user.model.User;
import com.primus.util.ServiceLibrary;
import com.primus.admin.division.model.Division;
import com.primus.admin.division.service.DivisionService;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserValidator extends AbstractValidator {

    @Override
    public String getBusinessKeyField() {
        return "User_Id";
    }

    public  List<RadsError> validateForCreate(PrimusModel model, ProductContext context, AbstractService service){
        List<RadsError> results = new ArrayList<RadsError>();
        String businessKey = getBusinessKeyField();
        User user= (User)model ;
        if (user.getCompany() == null && context.getLoggedinCompany() > 1 ) {
            CompanyService compService  = CommonUtil.getCompanyService() ;
            Company company = (Company) compService.getById(context.getLoggedinCompany()) ;
            user.setCompany(company);
        }

        PrimusModel existing = service.getByBusinessKey(model,context) ;
        if(existing != null )
            results.add( getErrorforCode(context,CommonErrorCodes.ALREADY_EXIST,businessKey));
        if (user.getCompany() != null){
            CompanyService companyService =  CommonUtil.getCompanyService() ;
            Company company = (Company)companyService.getByBusinessKey(user.getCompany(),context) ;
            user.setUserId(user.getPrefix() + "@" + company.getCode());
            user.setCompany(company);
        }

        List<RadsError> mandatoryErrors = checkforMandatoryFields(model,context);
        if(!Utils.isNullList(mandatoryErrors)) {
            results.addAll(mandatoryErrors);
        }
        List<RadsError> valueErrors =  checkforValueRanges(model,context) ;
        if(!Utils.isNullList(valueErrors)) {
            results.addAll(valueErrors);
        }
        return results;
    }

    @Override
    public List<RadsError> checkforMandatoryFields(PrimusModel model, ProductContext context) {
        List<RadsError> results =new ArrayList<RadsError>();
        User user = (User)  model ;
        if (user.getCompany() == null && context.getLoggedinCompany()  ==1  ) {
            results.add( getErrorforCode(context, CommonErrorCodes.CANNOT_BE_EMPTY,"Company"));
        }

        if(Utils.isNullString(user.getUserId()) ) {
            results.add( getErrorforCode(context, CommonErrorCodes.CANNOT_BE_EMPTY,"User_Id"));
        }
        if(Utils.isNullString(user.getEmail()) ) {
            results.add( getErrorforCode(context, CommonErrorCodes.CANNOT_BE_EMPTY,"Email"));
        }
        if(Utils.isNullString(user.getPhone()) ) {
            results.add( getErrorforCode(context, CommonErrorCodes.CANNOT_BE_EMPTY,"Phone"));
        }
        return results;


    }

    @Override
    public List<RadsError> checkforValueRanges(PrimusModel model, ProductContext context) {
        return null;
    }

    @Override
    public List<RadsError> adaptFromUI(PrimusModel model, ProductContext context) {
        User user = (User)  model ;
        List<RadsError> results =new ArrayList<RadsError>();
        CompanyService companyService = CommonUtil.getCompanyService() ;
        if (user.getCompany() != null) {
            Company commpany = (Company)companyService.getByBusinessKey(user.getCompany(),context) ;
            user.setCompany(commpany);
            if (user.getCompany() == null) {
                results.add( getErrorforCode(context, CommonErrorCodes.NOT_FOUND,"Company"));
            }
        }

        if(user.getDivision() != null && !Utils.isNullString(user.getDivision().getName() )) {
            DivisionService divisionService = CommonUtil.getDivisionService();
            Division curDivision =divisionService.getByName(user.getDivision().getName(),context) ;
            user.setDivision(curDivision);
            if (curDivision == null)
                results.add( getErrorforCode(context, CommonErrorCodes.NOT_FOUND,"Company"));

        }




        return results;

    }
}
