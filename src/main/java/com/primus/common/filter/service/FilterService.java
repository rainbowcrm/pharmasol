package com.primus.common.filter.service;

import com.primus.abstracts.AbstractDAO;
import com.primus.abstracts.AbstractService;
import com.primus.common.filter.dao.FilterDAO;
import com.primus.common.filter.model.PRMFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FilterService extends AbstractService {

    @Autowired
    FilterDAO filterDAO ;

    @Override
    public AbstractDAO getDAO() {
        return filterDAO;
    }


    public Map<String,String> findAllByPage(String userId, String page)
    {
        return filterDAO.findAllByPage(userId,page);

    }

    public PRMFilter findByKey(String user, String page, String filterName )
    {
        return filterDAO.findByKey(user,page,filterName);
    }

}
