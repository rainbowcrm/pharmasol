package com.primus.crm.appointmentplan;

import com.primus.common.ProductContext;
import com.primus.common.appointmentpreference.model.AppointmentPreference;
import com.primus.common.user.model.User;
import com.primus.crm.target.model.AgentVisitTarget;
import com.primus.crm.target.model.Target;
import com.primus.crm.target.model.TotalVisitTarget;
import com.primus.externals.IAppointmentEntity;
import com.techtrade.rads.framework.utils.Utils;

import java.sql.Time;
import java.text.SimpleDateFormat;
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
                 startTime.setTime(startTime.getTime() + timeinterval);
             }

             return ranges;
         }

        private List<Calendar> getPossibleTimings (Date startDate , Date endDate  ,  IAppointmentEntity appointmentEntity)
        {
            return  null;

        }
         private Calendar getPreferredTime (Date startDate , Date endDate  ,  IAppointmentEntity appointmentEntity)
         {
              Calendar retCalendar = new GregorianCalendar();
              Collections.sort((List<AppointmentPreference>) appointmentEntity.getAppointmentPreferences());
              for (AppointmentPreference preference :  appointmentEntity.getAppointmentPreferences()) {
                Date curDate = startDate  ;
                while(curDate.before(endDate))  {
                    Calendar iterateDate  = new GregorianCalendar();
                    iterateDate.setTime(curDate);
                    if(iterateDate.get(Calendar.DAY_OF_WEEK) ==  preference.getWeekday())  {
                        String prefTimeStr =  preference.getHhMM() ;
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                        String timeString = sdf.format(prefTimeStr) ;
                        String hhPart = timeString.substring(0,2);
                        String mmPart = timeString.substring(3,5);
                        iterateDate.set(Calendar.HOUR_OF_DAY,Integer.parseInt(hhPart));
                        iterateDate.set(Calendar.HOUR_OF_DAY,Integer.parseInt(mmPart));
                        return iterateDate ;
                    }
                }
             }
             return retCalendar;
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
     *  Preferred Agent Preferred Time
     */
    public void passOne(Target target , List<IAppointmentEntity> entities , ProductContext context  , Date from, Date to)
    {
        entities.forEach( entity ->  {
            List<TimeRange> intervals  = getInterval(entity,target);
            intervals.forEach( interval -> {     //Date iteration

                   User preferredAgent = getPreferredAgent(entity,target)   ;
                   Calendar calendar  =getPreferredTime(interval.getStart(),interval.getEnd(),entity);
                   AppointmentUnit appointmentUnit = createApptUnit(entity, calendar.getTime(), preferredAgent) ;
                   if (isFeasibleForAgent(appointmentUnit)) {
                       saveForUse (appointmentUnit) ;
                   }
            });

    });
    }

    private List<User> getAllAgents(Target  target  , ProductContext context)
    {
        return  null;
    }

    /**
     *  Preferred Time Non Preffered Agent
     * @param target
     * @param entities
     * @param context
     * @param from
     * @param to
     */
    public void passtwo(Target target , List<IAppointmentEntity> entities , ProductContext context  , Date from, Date to)
    {
        entities.forEach( entity ->  {
            List<TimeRange> intervals  = getInterval(entity,target);
            intervals.forEach( interval -> {     //Date iteration
                User preferredAgent = getPreferredAgent(entity,target)   ;
                List<User> allAgents=  getAllAgents(target, context);
                for  (User agent  : allAgents ) {
                    Calendar calendar = getPreferredTime(interval.getStart(), interval.getEnd(), entity);
                    AppointmentUnit appointmentUnit = createApptUnit(entity, calendar.getTime(), agent);
                    if (isFeasibleForAgent(appointmentUnit)) {
                        saveForUse(appointmentUnit);
                        break  ;
                    }
                }
            });

        });
    }


    /**
     * Non Prefferred Tim    Preffered Agent
     * @param target
     * @param entities
     * @param context
     * @param from
     * @param to
     */
    public void passThree(Target target ,List<IAppointmentEntity> entities , ProductContext context  , Date from, Date to)
    {
        entities.forEach( entity ->  {
            List<TimeRange> intervals  = getInterval(entity,target);
            intervals.forEach( interval -> {     //Date iteration

                User preferredAgent = getPreferredAgent(entity,target)   ;
                List<Calendar> calendars  =getPossibleTimings(interval.getStart(),interval.getEnd(),entity) ;
                for (Calendar calendar   : calendars) {
                    AppointmentUnit appointmentUnit = createApptUnit(entity, calendar.getTime(), preferredAgent);
                    if (isFeasibleForAgent(appointmentUnit)) {
                        saveForUse(appointmentUnit);
                        break ;
                    }
                }
            });

        });

    }

    public void passFour(Target target ,List<IAppointmentEntity> entities , ProductContext context  , Date from, Date to)
    {
        entities.forEach( entity ->  {
            List<TimeRange> intervals  = getInterval(entity,target);
            intervals.forEach( interval -> {     //Date iteration

                List<User> allAgents=  getAllAgents(target, context);
                List<Calendar> calendars  =getPossibleTimings(interval.getStart(),interval.getEnd(),entity) ;
  outer:         for (Calendar calendar   : calendars) {
                     for (User agent : allAgents ) {

                         AppointmentUnit appointmentUnit = createApptUnit(entity, calendar.getTime(), agent);
                         if (isFeasibleForAgent(appointmentUnit)) {
                             saveForUse(appointmentUnit);
                             break  outer;
                         }
                     }
                }
            });

        });


    }





}
