package com.primus.common.filter.dao;

import com.primus.abstracts.AbstractDAO;
import com.primus.common.filter.model.PRMFilter;
import com.techtrade.rads.framework.utils.Utils;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FilterDAO  extends AbstractDAO{

    @Override
    public String getEntityClassName() {
        return "PRMFilter";
    }

    @Override
    public Class getEntityClass() {
        return PRMFilter.class;
    }

    public Map<String,String> findAllByPage(String user, String page ) {
        Map<String,String> ans = new HashMap<String,String>();

        Query query = em.createQuery(" from PRMFilter where user.userId = :user    and page = :page " ) ;
        query.setParameter("user", user);
        query.setParameter("page", page);
        List<PRMFilter> lst = query.getResultList();
        if(!Utils.isNullList(lst)) {
            for (PRMFilter filter :  lst) {
                ans.put(String.valueOf(filter.getId()),filter.getName());
            }
        }


        return ans;
    }

    public PRMFilter findByKey(String user, String page,String filterName ) {

        PRMFilter filter =null;
        Query query = em.createQuery(" from PRMFilter where user.userId = :user    and page = :page and name=:name " ) ;
        query.setParameter("user", user);
        query.setParameter("page", page);
        query.setParameter("name", filterName);
        List<PRMFilter> lst = query.getResultList();
        if (!Utils.isNullList(lst))
            filter = (PRMFilter) lst.get(0) ;
        return filter;
    }
}
