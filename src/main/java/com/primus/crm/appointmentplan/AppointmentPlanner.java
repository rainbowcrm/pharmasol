package com.primus.crm.appointmentplan;

import com.primus.common.ProductContext;
import com.primus.common.user.model.User;
import com.primus.crm.target.model.AgentVisitTarget;
import com.primus.crm.target.model.Target;
import com.primus.crm.target.model.TotalVisitTarget;
import com.primus.externals.IAppointmentEntity;
import com.techtrade.rads.framework.utils.Utils;

import java.sql.Time;
import java.util.*;

public class AppointmentPlanner{



    public static void generateAppointments (Target target, ProductContext context)
    {



    }

        Map<IAppointmentEntity,List<AppointmentUnit>> appointmentUnitMap  = new HashMap < IAppointmentEntity,List<AppointmentUnit>>();

        Map<User,List<AppointmentUnit>> agentAppointmentUnitMap  = new HashMap < User,List<AppointmentUnit>>();


         private AppointmentUnit createApptUnit(IAppointmentEntity entity , Date curDate, User agent ) {
             return null ;
         }

         private boolean isFeasibleForAgent (AppointmentUnit appointmentUnit)
         {
             return false;
         }

         public void saveForUse(AppointmentUnit appointmentUnit)
         {

         }
         private List<TimeRange> getInterval(IAppointmentEntity entity, Target target)
         {
             int count ;
             TotalVisitTarget totalSpecificTarget =  target.getTotalVisitTargets().stream().filter(totalVisitTarget ->
             entity.getIndividualVisitType().equalsIgnoreCase(totalVisitTarget.getVisitingType().getCode())  &&
                     (totalVisitTarget.getEntityId() == entity.getId() ) ).findFirst().orElse(null);

             if(totalSpecificTarget != null ) {

                 count  = totalSpecificTarget.getTargettedVisit() ;

             }else {
                 TotalVisitTarget totalGroupTarget = target.getTotalVisitTargets().stream().filter(totalVisitTarget ->
                         entity.getTotalVisitType().equalsIgnoreCase(totalVisitTarget.getVisitingType().getCode()) ).findFirst().orElse(null);
                 count  = totalGroupTarget.getTargettedVisit() ;
             }

             if (count == 0) return  null;
             long timeinterval = (target.getToDate().getTime() -  target.getFromDate().getTime()) / count ;
             List<TimeRange> ranges = new ArrayList<>() ;
             Date startTime =  target.getFromDate() ;
             for(int i = 0 ; i  < count ; i  ++  )  {
                 TimeRange range = new TimeRange(startTime,new Date(startTime.getTime() + timeinterval)) ;
                 
                 ranges.add(range);
                 startTime.setTime(startTime.getTime() + timeinterval);



             }

             return ranges;
         }

         private User getPreferredAgent (IAppointmentEntity entity, Target target)
         {

             AgentVisitTarget agentSpecificTarget =  target.getAgentVisitTargets().stream().filter(agentVisitTarget ->
                     entity.getIndividualVisitType().equalsIgnoreCase(agentVisitTarget.getVisitingType().getCode())  &&
                             (agentVisitTarget.getEntityId() == entity.getId() ) ).findFirst().orElse(null);
             if(agentSpecificTarget != null )
                 return  agentSpecificTarget.getAgent() ;
             else  {
                 AgentVisitTarget agentGroupTarget = target.getAgentVisitTargets().stream().filter(agentVisitTarget ->
                         entity.getIndividualVisitType().equalsIgnoreCase(agentVisitTarget.getVisitingType().getCode()) ).findFirst().orElse(null);
                 if(agentGroupTarget != null   )
                     return agentGroupTarget.getAgent() ;

             }

             if  (!Utils.isNullCollection(entity.getAppointmentPreferences())) {
                 return entity.getAppointmentPreferences().stream().findFirst().orElse(null).getPreferredAgent();
             }
             return null;
         }

    /**
     *  Sets all the preferred to
     */
    public void passOne(Target target , List<IAppointmentEntity> entities , ProductContext context  , Date from, Date to)
    {

        entities.forEach( entity ->  {
            int countoFappts = -1;
            List<TimeRange> intervals  = getInterval(entity,target);
            intervals.forEach( interval -> {     //Date iteration

                   Date date  = interval.getStart()  ;
                   User preferredAgent = getPreferredAgent(entity,target)   ;
                   AppointmentUnit appointmentUnit = createApptUnit(entity, date, preferredAgent) ;
                   if (isFeasibleForAgent(appointmentUnit)) {
                       saveForUse (appointmentUnit) ;
                   }
            });

    });


    }

    public void passtwo(Target target , List<IAppointmentEntity> entities , ProductContext context  , Date from, Date to)
    {

        entities.forEach( entity ->  {
            int countoFappts = -1;
            List<TimeRange> intervals  = getInterval(entity,target);
            intervals.forEach( interval -> {  //Date iteration

                Date date = null;
                for (; ; ) { // iteratewith Other Agent=
                    User otherAgent = null;
                    AppointmentUnit otherAgentappointmentUnit = createApptUnit(entity, date, otherAgent);
                    if (isFeasibleForAgent(otherAgentappointmentUnit)) {
                        saveForUse(otherAgentappointmentUnit);
                    }
                }
            });

        });
    }


    public void passThree(Target target ,List<IAppointmentEntity> entities , ProductContext context  , Date from, Date to)
    {

        entities.forEach( entity ->  {
            int countoFappts = -1;
            List<TimeRange> intervals  = getInterval(entity,target);
            intervals.forEach( interval -> {      //Date iteration

                Date nonPreferreddate = null;
                for (; ; ) { // iteratewith Other Agent=
                    User prefferedAgent = null;
                    AppointmentUnit noPrefTimeappointmentUnit = createApptUnit(entity, nonPreferreddate, prefferedAgent);
                    if (isFeasibleForAgent(noPrefTimeappointmentUnit)) {
                        saveForUse(noPrefTimeappointmentUnit);
                    }
                }
            });

        });
    }

    public void passFour(Target target ,List<IAppointmentEntity> entities , ProductContext context  , Date from, Date to)
    {

        entities.forEach( entity ->  {
            int countoFappts = -1;
            List<TimeRange> intervals  = getInterval(entity,target);
            intervals.forEach( interval -> {      //Date iteration

                Date nonPreferreddate = null;
                for (; ; ) { // iteratewith Other Agent=
                    User notPrefferedAgent = null;
                    AppointmentUnit noPrefTimeappointmentUnit = createApptUnit(entity, nonPreferreddate, notPrefferedAgent);
                    if (isFeasibleForAgent(noPrefTimeappointmentUnit)) {
                        saveForUse(noPrefTimeappointmentUnit);
                    }
                }
            });

        });
    }





}
