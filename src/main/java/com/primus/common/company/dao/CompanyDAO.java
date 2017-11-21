package com.primus.common.company.dao;

import com.primus.abstracts.AbstractDAO;
import com.primus.common.company.model.Company;
import com.techtrade.rads.framework.utils.Utils;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;

@Component
public class CompanyDAO extends AbstractDAO {

    @Override
    public String getEntityClassName() {
        return "Company";
    }

    @Override
    public Class getEntityClass() {
        return Company.class;
    }

    public Company findByCode(String code) {
        Company company = null;

        Query query =  em.createQuery(" from Company where code = :code  " ) ;
        query.setParameter("code", code);
        List lst = query.getResultList();
        if (!Utils.isNullList(lst))
            company = (Company) lst.get(0) ;

        return company;
    }

    public List<Company> getAllActiveCompanies() {
        Company company = null;

        Query query =  em.createQuery(" from Company where active = true  " ) ;
        List lst = query.getResultList();

        return lst;


    }
    public Company findByName(String name) {
        Company company = null;

        Query query =  em.createQuery(" from Company where name = :name  " ) ;
        query.setParameter("name", name);
        List lst = query.getResultList();
        if (!Utils.isNullList(lst))
            company = (Company) lst.get(0) ;

        return company;
    }


}
