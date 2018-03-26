package com.primus.crm.snapshot.service;

import com.primus.admin.region.model.Location;
import com.primus.common.ProductContext;
import com.primus.crm.snapshot.model.SnapShot;
import com.primus.crm.snapshot.sqls.SnapShotSQLs;
import com.primus.crm.target.model.Target;
import com.primus.crm.target.service.TargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SnapShotService    {

    @Autowired
    SnapShotSQLs snapShotSQLs;

    @Autowired
    TargetService targetService  ;

    public SnapShot   getSnapShot(ProductContext context , Date currentDate, Location location)
    {
        SnapShot snapShot  = new SnapShot() ;
        Target target =  targetService.getTargetforDate(location,currentDate,context);
        if(target != null  )  {
            snapShot.setPeriodFrom(target.getFromDate());
            snapShot.setPeriodTo(target.getToDate());

        }


        return snapShot ;
    }
}
