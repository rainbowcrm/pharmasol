package com.primus.operations.iborder.service;

import com.primus.abstracts.AbstractDAO;
import com.primus.abstracts.AbstractService;
import com.primus.abstracts.PrimusModel;
import com.primus.abstracts.TransactionUpdateDelta;
import com.primus.operations.iborder.dao.InboundOrderDAO;
import com.primus.operations.iborder.model.InboundOrder;
import com.primus.operations.iborder.model.InboundOrderLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class InboundOrderService extends AbstractService {

    @Autowired
    InboundOrderDAO inboundOrderDAO ;

    @Override
    public AbstractDAO getDAO() {
        return inboundOrderDAO;
    }

    @Override
    protected void collateBeforUpdate(PrimusModel newObject, PrimusModel oldObject) {
        InboundOrder newObj = (InboundOrder) newObject;
        InboundOrder oldObj = (InboundOrder) oldObject;
        TransactionUpdateDelta delta = formDelta(oldObj.getInboundOrderLines(),  newObj.getInboundOrderLines()) ;
        newObj.getInboundOrderLines().addAll((List<InboundOrderLine>)delta.getDeletedRecords());
    }
}


