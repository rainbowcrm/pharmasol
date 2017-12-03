package com.primus.abstracts;

import com.primus.common.ProductContext;
import com.techtrade.rads.framework.utils.Utils;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Component
@Transactional
public abstract class AbstractDAO {

    @PersistenceContext
    protected  EntityManager em;

    public abstract  String getEntityClassName() ;

    public abstract Class getEntityClass();

    public PrimusModel getById( Object PK) {
        Class className =  getEntityClass() ;
        int companyId = Integer.parseInt(String.valueOf(PK));
        return (PrimusModel)em.find(className,PK);
        }

    public PrimusModel getById( Class className, Object PK) {
          return (PrimusModel)em.find(className,PK);
    }


    @Transactional
    public void create(PrimusModel model) {
        em.merge(model);
    }

    @Transactional
    public void update(PrimusModel model) {
        em.merge(model);
    }

    public void batchUpdate(List<? extends PrimusModel> objects)  throws Exception{

        boolean success = false;
        try {
            for (PrimusModel object : objects) {
                em.merge(object);
            }

            success = true;
        }catch(Exception ex) {

            //throw new DatabaseException(ex,DatabaseException.PERSISTENCE_ERROR);
        }
    }

    public long getTotalRecordCount(String entityName, ProductContext context, String whereCondition ) {

        try  {
            String queryString = " Select count(*) from " + entityName  + " " +  whereCondition;
            Query query =  em.createQuery(queryString);
            List lst = query.getResultList();
            if(!Utils.isNull(lst)) {
                Object obj = lst.get(0);
                if (obj!=null && obj instanceof Long) {
                    return(((Long)obj).longValue());
                } if (obj!=null && obj instanceof Integer) {
                    return(((Integer)obj).intValue());
                }
            }
            return 1;
        }finally{

        }
    }


    public List<PrimusModel> listData(String table, int from , int to , String whereCondition, String orderby ) {
/*        Session session = openSession(false);
        Query query = session.createQuery("from " + table   +  ((Utils.isNull(whereCondition))?"":whereCondition) +
                " " + ((Utils.isNull(orderby))?"": (" order by " + orderby) ) );
        query.setFirstResult(from);
        query.setMaxResults(to-from);
        List<CRMModelObject> ans = query.list();
        supplement(ans);
        closeSession(session,false);*/

        Query query =  em.createQuery("from " + table   +  ((Utils.isNull(whereCondition))?"":whereCondition) +
                " " + ((Utils.isNull(orderby))?"": (" order by " + orderby) ) );
        query.setFirstResult(from);
        query.setMaxResults(to-from);
        List<PrimusModel> ans = query.getResultList();

        return ans;
    }

    public List<PrimusModel> fetchAllActive(String table,  String whereCondition, String orderby ) {

        Query query =  em.createQuery("from " + table   +  ((Utils.isNull(whereCondition))?"":whereCondition) +
                " " + ((Utils.isNull(orderby))?"": (" order by " + orderby) ) );

        List<PrimusModel> ans = query.getResultList();

        return ans;
    }
}
