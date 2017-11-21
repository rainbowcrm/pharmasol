package com.primus.common.user.dao;

import com.primus.abstracts.AbstractDAO;
import com.primus.abstracts.PrimusModel;
import com.primus.common.user.model.User;
import org.springframework.stereotype.Component;

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
}
