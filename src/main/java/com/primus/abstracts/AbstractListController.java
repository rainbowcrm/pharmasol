package com.primus.abstracts;

import com.primus.common.CommonUtil;
import com.primus.common.ProductContext;
import com.primus.common.filter.model.PRMFilter;
import com.primus.common.filter.service.FilterService;
import com.primus.util.ServiceLibrary;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.ListController;
import com.techtrade.rads.framework.filter.Filter;
import com.techtrade.rads.framework.filter.FilterNode;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public abstract class AbstractListController extends ListController {
    protected final int recordsPerPage  = 12;

    protected  abstract  String getServiceName();

    protected  abstract String getValidatorName();

    protected  abstract  String getAddEditPageKey();

    public PageResult goToEdit(List<ModelObject> objects) {

        PageResult result = new PageResult();
        result.setNextPageKey(getAddEditPageKey());
        objects.forEach( object ->   {
            getValidator().adaptToUI((PrimusModel) object,(ProductContext) getContext());
        });

        return result;
    }
    public  ProductContext getProductContext()

    {
        return (ProductContext) getContext() ;
    }


    public Object getPrimaryKeyValue(ModelObject object) {
        PrimusModel item = (PrimusModel) object;
        if ( item instanceof  PrimusBusinessModel)
            return ((PrimusBusinessModel)item).getId();
        else
            return item.getPK();
    }

    private AbstractValidator getValidator()
    {
        String validatorName =  getValidatorName() ;
        AbstractValidator validator =  ServiceLibrary.services().getValidator(validatorName);
        return validator;

    }

    protected AbstractService getService ()
    {
        String serviceName = getServiceName() ;
        AbstractService service = ServiceLibrary.services().getService(serviceName) ;
        return        service ;
    }


    protected String getFilter(Filter filterData ) {
        StringBuffer whereCondition = new  StringBuffer();
        if (filterData!=null  && !Utils.isNullList(filterData.getNodeList()) ) {
            for (FilterNode node : filterData.getNodeList()) {
                if (!Utils.isNullString(String.valueOf(node.getValue())) && !"FilterName".equals(node.getField())) {
                    if (whereCondition.length() < 1)
                        whereCondition.append( " where  "  + getCondition(node) );
                    else
                        whereCondition.append( " and  "  + getCondition(node));
                }
            }
        }
        return whereCondition.toString().replace('*', '%');
    }


    @Override
    public PageResult print(List<ModelObject> objects) {
        return null;
    }
    protected static String getOperatorClose(FilterNode node) {

        if (node.getOperater() == FilterNode.Operator.IN || node.getOperater() == FilterNode.Operator.NOT_IN) {
            return " ) ";
        }
        else
            return "";
    }

    protected static String getOperator(FilterNode node) {
        if (node.getOperater() == null || node.getOperater() == FilterNode.Operator.EQUALS) {
            if (node.getValue() != null && node.getValue().toString().contains("*")) {
                //node.setValue(node.getValue().toString().replace('*', '%'));
                return " like " ;
            }else
                return "=";
        } else if (node.getOperater() == FilterNode.Operator.GREATER_THAN_EQUAL) {
            return ">=";
        }else if (node.getOperater() == FilterNode.Operator.GREATER_THAN) {
            return ">";
        }else if (node.getOperater() == FilterNode.Operator.LESS_THAN_EQUAL) {
            return "<=";
        }else if (node.getOperater() == FilterNode.Operator.LESS_THAN) {
            return "<";
        }else if (node.getOperater() == FilterNode.Operator.IN) {
            return " in ( ";
        }else if (node.getOperater() == FilterNode.Operator.NOT_IN) {
            return "  not in ( ";
        }
        return "=";
    }

    protected static String getCondition (FilterNode node)
    {
        if  (node.getOperater() != FilterNode.Operator.IN  && node.getOperater() != FilterNode.Operator.NOT_IN )
            return Utils.initlower(node.getField())  + getOperator(node) +  "'" +  node.getValue() + "'";
        else
            return Utils.initlower(node.getField())  + getOperator(node) +    node.getValue()  + getOperatorClose(node) ;

    }
    @Override

    public void saveFilter(Filter filter) {
        PRMFilter crmFilter= PRMFilter.parseRadsFilter(this, filter);
        FilterService filterService = CommonUtil.getFilterService() ;
        PRMFilter existing =(PRMFilter) filterService.findByKey(((ProductContext) getContext()).getUser(),
                this.getClass().getName(),crmFilter.getName());
        if (existing == null)
            filterService.create(crmFilter,(ProductContext) getContext());
        else{
            // crmFilter.setId(existing.getId());
            crmFilter.getPrimusFilterDetails().forEach( prmFilterDetails ->  {
                prmFilterDetails.setPrimusFilter(existing);
            });
            existing.setPrimusFilterDetails((crmFilter.getPrimusFilterDetails()));
            filterService.update(existing,(ProductContext) getContext());
        }
    }

    public Map <String, String > getSavedFiters() {
        Map<String, String> ans = new LinkedHashMap<String, String>();
        ans.put("null", "---Select one---") ;
        FilterService filterService = CommonUtil.getFilterService() ;
        ans.putAll(filterService.findAllByPage(this.getContext().getUser(), this.getClass().getName()));

        return ans;

    }


    @Override
    public long getTotalNumberofRecords(Filter filter) {
        long totalRecords = getService().getTotalRecordCount((ProductContext) getContext(),getFilter(filter));
        return totalRecords;
    }

    public int getTotalNumberofPages(Filter filter) {
        long totalRecords = getService().getTotalRecordCount((ProductContext) getContext(),getFilter(filter));
        int rem = (int)totalRecords % recordsPerPage ;
        if (rem > 0 )
            return (int)( totalRecords /recordsPerPage ) + 1;
        else
            return(int)( totalRecords /recordsPerPage );
    }

    @Override
    public List<ModelObject> getData(int pageNumber, Filter filter,SortCriteria sortCriteria) {
        int from  = (pageNumber-1)*recordsPerPage ;
        AbstractService serv = (AbstractService)getService();
        return (List)serv.listData(from,  from + recordsPerPage, getFilter(filter),(ProductContext)getContext(),sortCriteria);

    }

    public String getCompanyName() {
        return  "Primus Solutions";
    }

    public IRadsContext generateContext(HttpServletRequest request, HttpServletResponse response, UIPage page) {
        return CommonUtil.generateContext(request,page);

    }



    @Override
    public IRadsContext generateContext(String authToken, UIPage page) {
        return CommonUtil.generateContext(authToken,page);

    }
    @Override
    public PageResult delete(List<ModelObject> objects) {
        for (ModelObject object : objects) {
           ((PrimusModel)object).setDeleted(true);
        }
        AbstractService serv = (AbstractService)getService();
        try {
            return new PageResult(serv.batchUpdate((List)objects,(ProductContext)getContext()));
        }catch(Exception ex) {
            PageResult result= new PageResult(TransactionResult.Result.FAILURE, Arrays.asList(new RadsError("",ex.getMessage())),null);
            return result;
        }
       // return  null;

    }
    @Override
    public List<ModelObject> populateFullObjectfromPK(List<ModelObject> objects) {
        AbstractService serv = getService();
        List<ModelObject> fullObjects = new ArrayList<ModelObject>();
        if (!Utils.isNullList(objects)) {
            for (ModelObject obj : objects) {
                ModelObject fullObj = (ModelObject)serv.getById(getPrimaryKeyValue(obj));
                fullObjects.add(fullObj);
            }
        }
        return fullObjects;
    }

    @Override
    public List<RadsError> validateforDelete(List<ModelObject> list) {
        return null;
    }

    @Override
    public List<RadsError> validateforEdit(List<ModelObject> list) {
        if(list  == null || list.size() != 1   ) {
            RadsError error = AbstractValidator.getErrorforCode(getProductContext(), CommonErrorCodes.ONE_ROW_REQUIRED,"");
           return Arrays.asList(error);
        }
        return null;
    }

    @Override
    public PageResult submit(List<ModelObject> list, String s) {
        return null;
    }

    public Map<String,String> getFiniteValues(String groupCode)
    {
        return CommonUtil.getFiniteValues(groupCode);

    }
}
