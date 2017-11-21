package com.primus.common.user.service;

import com.primus.abstracts.AbstractDAO;
import com.primus.abstracts.AbstractService;
import com.primus.abstracts.PrimusModel;
import com.primus.common.ProductContext;
import com.primus.common.user.dao.UserDAO;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class UserService extends AbstractService {


    @Autowired
    UserDAO userDAO ;

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

