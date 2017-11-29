package com.primus.common.company.service;

import com.primus.abstracts.AbstractDAO;
import com.primus.abstracts.AbstractService;
import com.primus.abstracts.PrimusModel;
import com.primus.common.ProductContext;
import com.primus.common.company.dao.CompanyDAO;
import com.primus.common.company.model.Company;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class CompanyService extends AbstractService {

    @Autowired
    CompanyDAO companyDAO;

    @Override
    public AbstractDAO getDAO() {
        return companyDAO;
    }


    public Company findByCode(String code) {
        return ((CompanyDAO)getDAO()).findByCode(code);
    }


    public Company findByName(String name) {
        return ((CompanyDAO)getDAO()).findByName(name);
    }


    @Override
    public List<? extends PrimusModel> listData(String className, int from, int to, String whereCondition, String orderBy, ProductContext context, SortCriteria sortCriteria) {
        return  getDAO().listData(className ,from, to, whereCondition , orderBy);
    }



    public List<? extends PrimusModel> fetchAllActive(String whereCondition, String orderBy, ProductContext context) {
        StringBuffer additionalCondition = new StringBuffer();
        additionalCondition = additionalCondition.append(" ");
        if (Utils.isNullString(whereCondition)) {
            additionalCondition = additionalCondition.append(" where deleted = false  ");
        } else {
            additionalCondition = additionalCondition.append(whereCondition + " and deleted = false ");
        }
        return getDAO().fetchAllActive(getDAO().getEntityClassName(), additionalCondition.toString(), orderBy);

    }

    public List<? extends PrimusModel> fetchAllActive(String className,
                                                      String whereCondition, String orderBy, ProductContext context) {
        StringBuffer additionalCondition = new StringBuffer();
        additionalCondition = additionalCondition.append(" ");
        if (Utils.isNullString(whereCondition)) {
            additionalCondition = additionalCondition.append(" where deleted = false ");
        } else {
            additionalCondition = additionalCondition.append(whereCondition + " and deleted = false ");
        }
        return getDAO().fetchAllActive(className, additionalCondition.toString(), orderBy);

    }

}
