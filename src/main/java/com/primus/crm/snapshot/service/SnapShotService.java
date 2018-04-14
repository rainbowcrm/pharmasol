package com.primus.crm.snapshot.service;

import com.primus.admin.region.model.Location;
import com.primus.common.FVConstants;
import com.primus.common.ProductContext;
import com.primus.common.user.model.User;
import com.primus.crm.appointment.model.Appointment;
import com.primus.crm.appointment.service.AppointmentService;
import com.primus.crm.snapshot.model.FeedbackDetail;
import com.primus.crm.snapshot.model.OrderFigure;
import com.primus.crm.snapshot.model.POBFigure;
import com.primus.crm.snapshot.model.SnapShot;
import com.primus.crm.snapshot.sqls.SnapShotSQLs;
import com.primus.crm.target.model.Target;
import com.primus.crm.target.service.TargetService;
import com.techtrade.rads.framework.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class SnapShotService    {

    @Autowired
    SnapShotSQLs snapShotSQLs;

    @Autowired
    TargetService targetService  ;


   @Autowired
    AppointmentService appointmentService  ;

    public SnapShot   getSnapShot(ProductContext context , Date currentDate)
    {
        SnapShot snapShot  = new SnapShot() ;
        snapShot.setOrderFigure(new OrderFigure());
        snapShot.setPobFigure(new POBFigure());
        User user =  context.getLoggedInUser();
        Location location =null  ;
        if (user.getUserPortfolio().getAccessLevel().equals(FVConstants.USER_ACCESS.LOCATION)) {
            location = user.getUserPortfolio().getLocation() ;
        }

        Target target =  targetService.getTargetforDate(location,currentDate,context);
        if(target != null  )  {
            snapShot.setPeriodFrom(target.getFromDate());
            snapShot.setPeriodTo(target.getToDate());

        }
       /* List<String> lastFeedBacks   =  snapShotSQLs.getFeedBacksForLocation(location.getId(),context.getLoggedinCompany() );if
        snapShot.setFeedbackDetailList(lastFeedBacks);*/

        List<FeedbackDetail> lastFeedBacks = new ArrayList<>();
        OrderFigure  orderFigure  = new OrderFigure() ;
        List<Appointment> completedAppointmentsForLocation =  appointmentService.getAllCompletedAppointmentsForLocation(location,target.getFromDate(),target.getToDate(),context);
        if (!Utils.isNullCollection(completedAppointmentsForLocation)) {
            completedAppointmentsForLocation.forEach( appointment ->   {
                FeedbackDetail feedbackDetail  = new FeedbackDetail();
                feedbackDetail.setGivenBy(appointment.getPartyName());
                feedbackDetail.setDate(appointment.getApptDate());
                feedbackDetail.setFeedback(appointment.getFeedBack());
                lastFeedBacks.add(feedbackDetail);
                if   (!Utils.isNullCollection(appointment.getStockistVisitOrderLines())) {
                    appointment.getStockistVisitOrderLines().forEach( stockistVisitOrderLine ->   {
                       double lineTotal = stockistVisitOrderLine.getRate() * stockistVisitOrderLine.getQty()  ;
                       snapShot.getOrderFigure().setAchievedAmount(snapShot.getOrderFigure().getAchievedAmount() + lineTotal) ;
                    });
                }
                if   (!Utils.isNullCollection(appointment.getOrderLines())) {
                    appointment.getOrderLines().forEach( orderLine ->   {
                        double lineTotal = orderLine.getRate() * orderLine.getQty()  ;
                        snapShot.getPobFigure().setAchievedAmount(snapShot.getPobFigure().getAchievedAmount() + lineTotal);

                    });

                }
            });
        }
        snapShot.setFeedbackDetailList(lastFeedBacks);
        snapShot.setOrderFigure(orderFigure);

        List<Appointment> recentAppointments =  new ArrayList<>();

        //snapShot.set


        return snapShot ;
    }
}
