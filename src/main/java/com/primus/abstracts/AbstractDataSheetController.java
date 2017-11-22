package com.primus.abstracts;

import com.primus.common.CommonUtil;
import com.primus.common.ProductContext;
import com.primus.common.filter.model.PRMFilter;
import com.primus.common.filter.service.FilterService;
import com.primus.util.ServiceLibrary;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.DataSheetController;
import com.techtrade.rads.framework.filter.Filter;
import com.techtrade.rads.framework.filter.FilterNode;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractDataSheetController extends DataSheetController {

    protected final int recordsPerPage  = 12;

    protected  abstract  String getServiceName();

    protected  abstract String getValidatorName();

    protected  abstract  String getAddEditPageKey();

    private AbstractValidator getValidator()
    {
        String validatorName =  getValidatorName() ;
        AbstractValidator validator =  ServiceLibrary.services().getValidator(validatorName);
        return validator;

    }

    private AbstractService getService ()
    {
        String serviceName = getServiceName() ;
        AbstractService service = ServiceLibrary.services().getService(serviceName) ;
        return        service ;
    }


    public AbstractDataSheetController() {
        super();
    }

    @Override
    public IRadsContext generateContext(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, UIPage uiPage) {
        return null;
    }

    @Override
    public IRadsContext generateContext(String s, UIPage uiPage) {
        return null;
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

    public Map<String, String > getSavedFiters() {
        Map<String, String> ans = new LinkedHashMap<String, String>();
        ans.put("null", "---Select one---") ;
        FilterService filterService = CommonUtil.getFilterService() ;
        ans.putAll(filterService.findAllByPage(this.getContext().getUser(), this.getClass().getName()));

        return ans;

    }


    @Override
    public List<ModelObject> getData(int i, Filter filter, SortCriteria sortCriteria) {
        return null;
    }



    @Override
    public int getTotalNumberofPages(Filter filter) {
        return 0;
    }

    @Override
    public long getTotalNumberofRecords(Filter filter) {
        return 0;
    }

    @Override
    public PageResult delete(List<ModelObject> list) {
        return null;
    }

    @Override
    public PageResult submit(List<ModelObject> list, String s) {
        return null;
    }

    @Override
    public List<RadsError> validateforDelete(List<ModelObject> list) {
        return null;
    }

    @Override
    public List<RadsError> validateforEdit(List<ModelObject> list) {
        return null;
    }

    @Override
    public PageResult goToEdit(List<ModelObject> list) {
        return null;
    }

    @Override
    public List<ModelObject> populateFullObjectfromPK(List<ModelObject> list) {
        return null;
    }

    @Override
    public List<RadsError> validateforCreate() {
        return null;
    }

    @Override
    public List<RadsError> validateforUpdate() {
        return null;
    }

    @Override
    public PageResult create() {
        return null;
    }

    @Override
    public PageResult delete() {
        return null;
    }

    @Override
    public void read() {

    }

    @Override
    public PageResult update() {
        return null;
    }

    public Map<String,String> getFiniteValues(String groupCode)
    {
        return CommonUtil.getFiniteValues(groupCode);

    }
}
