package com.primus.abstracts;

import com.primus.common.Logger;
import com.primus.common.ProductContext;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;
import org.apache.log4j.LogManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
@Transactional
public abstract class AbstractService {

    public abstract AbstractDAO getDAO();

    @Transactional(propagation = Propagation.REQUIRED)
    public TransactionResult create(PrimusModel object, ProductContext productContext) {
        AbstractDAO dao = getDAO();
        object.setCreatedDate(new java.util.Date());
        object.setCreatedBy(productContext.getUser());
        object.setLastUpdatedBy(productContext.getUser());
        object.setLastUpdateDate(new java.util.Date());
        dao.create(object);
        Logger.logDebug("Object Created= " + object.toJSON() + "\n Context=" + productContext.toString(), this.getClass());
        return new TransactionResult();
    }

    protected TransactionUpdateDelta formDelta(Collection<? extends PrimusBusinessModel> oldList, Collection<? extends PrimusBusinessModel> newList) {
        Collection<PrimusBusinessModel> deletedRecords = new ArrayList<PrimusBusinessModel>();
        Collection<PrimusBusinessModel> newRecords = new ArrayList<PrimusBusinessModel>();
        TransactionUpdateDelta transactionUpdateDelta = new TransactionUpdateDelta();
        if (!Utils.isNullCollection(oldList)) {
            oldList.forEach(oldRecord -> {
                PrimusModel findMatchByPK = newList.stream().filter(newRecord -> oldRecord.getId() == newRecord.getId()).findFirst().orElse(null);
                if (findMatchByPK == null) {
                    oldRecord.setDeleted(true);
                    deletedRecords.add(oldRecord);
                   /* PrimusModel findMatchByBK = newList.stream().filter(newRecord -> oldRecord.getId() == newRecord.getId()).findFirst().orElse(null);
                    if(findMatchByBK == null)
                        deletedRecords.add(oldRecord);*/
                }
            });
        }
        if (!Utils.isNullCollection(newList)) {
            newList.forEach(newRecord -> {
                if (newRecord.getId() <= 0) {
                    newRecords.add(newRecord);
                }
            });
        }

        transactionUpdateDelta.setDeletedRecords(deletedRecords);
        transactionUpdateDelta.setNewRecords(newRecords);

        return transactionUpdateDelta;


    }

    protected void collateBeforUpdate(PrimusModel newObject, PrimusModel oldObject) {


    }

    @Transactional(propagation = Propagation.REQUIRED)
    public TransactionResult update(PrimusModel object, ProductContext productContext) {
        AbstractDAO dao = getDAO();
        collateBeforUpdate(object, productContext.getOldObject());
        object.setLastUpdatedBy(productContext.getUser());
        object.setLastUpdateDate(new java.util.Date());
        dao.update(object);
        ;
        Logger.logDebug("Object Updated= " + object.toJSON() + "\n Context=" + productContext.toString(), this.getClass());
        //  System.out.println(Integer.parseInt("xxx")) ;
        return new TransactionResult();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public TransactionResult batchUpdate(List<PrimusModel> objects, ProductContext context) throws Exception {
        List<RadsError> errors = new ArrayList<RadsError>();
        TransactionResult.Result result = TransactionResult.Result.SUCCESS;
        try {
            for (PrimusModel object : objects) {
                object.setLastUpdateDate(new java.sql.Timestamp(new java.util.Date().getTime()));
                object.setLastUpdatedBy(context.getUser());
            }
            getDAO().batchUpdate(objects);
        } catch (Exception ex) {
          Logger.logException("Mass update failed" ,this.getClass(),ex);
        }
        return new TransactionResult(result, errors);
    }

    public long getTotalRecordCount(ProductContext context, String whereCondition) {
        StringBuffer additionalCondition = new StringBuffer();
   /*     boolean allowAllDiv = CommonUtil.allowAllDivisionAccess(context);
        Metadata metadata = CommonUtil.getMetaDataforClass(getTableName());
        if (Utils.isNullString(whereCondition) ){
            additionalCondition = additionalCondition.append(" where company.id = " +  context.getLoggedinCompany()) ;
        }else {
            additionalCondition = additionalCondition.append(whereCondition +  " and company.id= " +  context.getLoggedinCompany()) ;
        }
        if (!allowAllDiv && metadata != null && metadata.isDivisionSpecific()) {
            additionalCondition = additionalCondition.append(" and division.id = "  +  context.getLoggedInUser().getDivision().getId());
        }*/

        if (Utils.isNullString(whereCondition)) {
            additionalCondition = additionalCondition.append(" where company.id = " + context.getLoggedinCompany());
        } else {
            additionalCondition = additionalCondition.append(whereCondition + " and company.id= " + context.getLoggedinCompany());
        }

        return getDAO().getTotalRecordCount(getDAO().getEntityClassName(), context, additionalCondition.toString());
    }

    public List<? extends PrimusModel> listData(int from, int to,
                                                String whereCondition, ProductContext context, SortCriteria sortCriteria) {
        return listData(getDAO().getEntityClassName(), from, to, whereCondition, null, context, sortCriteria);
    }

    public List<? extends PrimusModel> listData(String className, int from, int to,
                                                String whereCondition, String orderBy, ProductContext context, SortCriteria sortCriteria) {
        StringBuffer additionalCondition = new StringBuffer();
        additionalCondition = additionalCondition.append(" ");
        if (Utils.isNullString(whereCondition)) {
            additionalCondition = additionalCondition.append(" where company.id = " + context.getLoggedinCompany());
        } else {
            additionalCondition = additionalCondition.append(whereCondition + " and company.id= " + context.getLoggedinCompany());
        }
        return getDAO().listData(className, from, to, additionalCondition.toString(), orderBy);

    }

    public List<? extends PrimusModel> fetchAllActive(String whereCondition, String orderBy, ProductContext context) {
        StringBuffer additionalCondition = new StringBuffer();
        additionalCondition = additionalCondition.append(" ");
        if (Utils.isNullString(whereCondition)) {
            additionalCondition = additionalCondition.append(" where deleted = false and company.id = " + context.getLoggedinCompany());
        } else {
            additionalCondition = additionalCondition.append(whereCondition + " and deleted = false and company.id= " + context.getLoggedinCompany());
        }
        return getDAO().fetchAllActive(getDAO().getEntityClassName(), additionalCondition.toString(), orderBy);

    }

    public List<? extends PrimusModel> fetchAllActive(String className,
                                                      String whereCondition, String orderBy, ProductContext context) {
        StringBuffer additionalCondition = new StringBuffer();
        additionalCondition = additionalCondition.append(" ");
        if (Utils.isNullString(whereCondition)) {
            additionalCondition = additionalCondition.append(" where deleted = false and company.id = " + context.getLoggedinCompany());
        } else {
            additionalCondition = additionalCondition.append(whereCondition + " and deleted = false and company.id= " + context.getLoggedinCompany());
        }
        return getDAO().fetchAllActive(className, additionalCondition.toString(), orderBy);

    }


    public PrimusModel getById(Object PK) {
        return getDAO().getById(PK);
    }


    public PrimusModel getByBusinessKey(PrimusModel object, ProductContext context) {
        Map<String, Object> keys = object.getBK();
        StringBuffer condition = new StringBuffer();
        if (keys != null) {
            condition.append(" where ");
            Iterator it = keys.keySet().iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                Object val = keys.get(key);
                if (val == null) continue;
                if (val instanceof Integer || val instanceof Long || val instanceof Double || val instanceof Float)
                    condition = condition.append(Utils.initlower(key) + " = " + val);
                else
                    condition = condition.append(Utils.initlower(key) + " = '" + val + "'");
                if (it.hasNext()) {
                    condition.append(" and ");
                }
            }
        }
        if (condition.toString().equals(" where ")) return null;
        List<? extends PrimusModel> objects = fetchAllActive(object.getClass().getName(),condition.toString(), null, context);
        if (!Utils.isNullList(objects))
            return objects.get(0);
        else
            return null;
    }
}
