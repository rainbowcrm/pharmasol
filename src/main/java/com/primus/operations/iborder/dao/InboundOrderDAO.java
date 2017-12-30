package com.primus.operations.iborder.dao;

import com.primus.abstracts.AbstractDAO;
import com.primus.operations.iborder.model.InboundOrder;
import org.springframework.stereotype.Component;

@Component
public class InboundOrderDAO extends AbstractDAO {


    @Override
    public String getEntityClassName() {
        return "InboundOrder";
    }

    @Override
    public Class getEntityClass() {
        return InboundOrder.class;
    }
}

