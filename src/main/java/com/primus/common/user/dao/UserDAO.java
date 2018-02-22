package com.primus.common.user.dao;

import com.primus.abstracts.AbstractDAO;
import com.primus.abstracts.PrimusModel;
import com.primus.common.user.model.User;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;

@Component
public class UserDAO extends AbstractDAO {

    @Override
    public String getEntityClassName() {
        return "User";
    }

    @Override
    public Class getEntityClass() {
        return User.class;
    }

    @Override
    public PrimusModel getById(Object PK) {

        return (User)em.find(User.class,PK);
    }


    public List<User> getAllDirectReportees(User manager , int companyId)
    {
        Query query = em.createQuery("from  User where managerUser.userId = :user  and company.id = :company and " +
                "  deleted =false  ");
        query.setParameter("user", manager.getUserId());
        query.setParameter("company", companyId);
        List<User> ans = query.getResultList();
        return ans;

    }
}
