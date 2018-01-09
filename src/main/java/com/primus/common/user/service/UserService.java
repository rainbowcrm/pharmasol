package com.primus.common.user.service;

import com.primus.abstracts.*;
import com.primus.admin.region.model.Location;
import com.primus.admin.region.model.Region;
import com.primus.common.ProductContext;
import com.primus.common.user.dao.UserDAO;
import com.primus.common.user.model.User;
import com.primus.common.user.model.UserRegion;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@Transactional
public class UserService extends AbstractService {


    @Autowired
    UserDAO userDAO ;

    protected TransactionUpdateDelta formRegionDelta(Collection<UserRegion> oldList, Collection<UserRegion> newList) {

        Collection<PrimusBusinessModel> deletedRecords = new ArrayList<PrimusBusinessModel>();
        Collection<PrimusBusinessModel> newRecords = new ArrayList<PrimusBusinessModel>();
        TransactionUpdateDelta transactionUpdateDelta = new TransactionUpdateDelta();
        if (!Utils.isNullCollection(oldList)) {
            oldList.forEach(oldRecord -> {
                UserRegion findMatchByRegion = newList.stream().filter(newRecord -> oldRecord.getRegion().getId() == newRecord.getRegion().getId()).findFirst().orElse(null);
                if (findMatchByRegion == null) {
                    oldRecord.setDeleted(true);
                    oldRecord.setAccessAlowed(false);
                    deletedRecords.add(oldRecord);

                }else  {
                    findMatchByRegion.setId(oldRecord.getId());
                    findMatchByRegion.setVersion(oldRecord.getVersion());
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

    @Override
    protected void collateBeforUpdate(PrimusModel newObject, PrimusModel oldObject) {
        User newObj = (User) newObject;
        User oldObj = (User) oldObject;
        TransactionUpdateDelta delta = formRegionDelta(oldObj.getUserRegions(),  newObj.getUserRegions()) ;
        if(!Utils.isNullCollection(newObj.getUserRegions()))
            newObj.getUserRegions().addAll((List<UserRegion>)delta.getDeletedRecords());

    }

    @Override
    public AbstractDAO getDAO() {
        return userDAO;
    }

    public List<? extends PrimusModel> listData(String className, int from, int to,
                                                String whereCondition, String orderBy, ProductContext context, SortCriteria sortCriteria) {
        StringBuffer additionalCondition = new StringBuffer();
        additionalCondition = additionalCondition.append( " " );
        if (context.getLoggedinCompany() == 1)  {
            if (!Utils.isNullString(whereCondition)) {
                additionalCondition = additionalCondition.append(whereCondition);
            }
        }else {
            if (Utils.isNullString(whereCondition)) {
                additionalCondition = additionalCondition.append(" where company.id = " + context.getLoggedinCompany());
            } else {
                additionalCondition = additionalCondition.append(whereCondition + " and company.id= " + context.getLoggedinCompany());
            }
        }
        return  getDAO().listData(className ,from, to, additionalCondition.toString() , orderBy);

    }

}

