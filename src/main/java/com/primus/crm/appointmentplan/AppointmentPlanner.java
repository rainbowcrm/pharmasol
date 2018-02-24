package com.primus.crm.appointmentplan;

import com.primus.common.FVConstants;
import com.primus.common.ProductContext;
import com.primus.common.ServiceFactory;
import com.primus.common.appointmentpreference.model.AppointmentPreference;
import com.primus.common.user.model.User;
import com.primus.crm.target.model.AgentVisitTarget;
import com.primus.crm.target.model.Target;
import com.primus.crm.target.model.TotalVisitTarget;
import com.primus.externals.IAppointmentEntity;
import com.primus.externals.doctor.service.DoctorService;
import com.primus.externals.stockist.service.StockistService;
import com.primus.externals.store.service.StoreService;
import com.techtrade.rads.framework.utils.Utils;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class AppointmentPlanner{



    private  List<IAppointmentEntity> getEntities(TotalVisitTarget totalVisitTarget,ProductContext context)
    {
        List<IAppointmentEntity> entities = new ArrayList<>();

        if (FVConstants.VISIT_TO.ALL_STORE.equalsIgnoreCase(totalVisitTarget.getVisitingType().getCode())) {
            StoreService service  =  ServiceFactory.getStoreService() ;
            List allStores = service.fetchAllActive( " storeAssociation.location.id =  " + totalVisitTarget.getTarget().getLocation().getId(), "" ,context  );
            entities.addAll(allStores);
        }

        if (FVConstants.VISIT_TO.ALL_DOCTOR.equalsIgnoreCase(totalVisitTarget.getVisitingType().getCode())) {
            DoctorService service  =  ServiceFactory.getDoctorService();
            List allDoctors = service.fetchAllActive( " doctorAssociation.location.id =  " + totalVisitTarget.getTarget().getLocation().getId(), "" ,context  );
            entities.addAll(allDoctors);
        }

        if (FVConstants.VISIT_TO.ALL_STOCKIST.equalsIgnoreCase(totalVisitTarget.getVisitingType().getCode())) {
            StockistService service  =  ServiceFactory.getStockistService();
            List allStockists = service.fetchAllActive( " doctorAssociation.location.id =  " + totalVisitTarget.getTarget().getLocation().getId(), "" ,context  );
            entities.addAll(allStockists);
        }

        if (FVConstants.VISIT_TO.IND_DOCTOR.equalsIgnoreCase(totalVisitTarget.getVisitingType().getCode())) {
            entities.add(totalVisitTarget.getDoctor());
        }
        if (FVConstants.VISIT_TO.IND_STORE.equalsIgnoreCase(totalVisitTarget.getVisitingType().getCode())) {
            entities.add(totalVisitTarget.getStore());
        }
        if (FVConstants.VISIT_TO.IND_STOCKIST.equalsIgnoreCase(totalVisitTarget.getVisitingType().getCode())) {
            entities.add(totalVisitTarget.getStockist());
        }



        return entities;

    }

    public  void generateAppointments (Target target, ProductContext context)
    {
        target.getTotalVisitTargets().forEach( totalVisitTarget  ->   {
            List<IAppointmentEntity> entities =  getEntities(totalVisitTarget,context);
            passOne(target,entities,context);
        });
        target.getTotalVisitTargets().forEach( totalVisitTarget  ->   {
            List<IAppointmentEntity> entities =  getEntities(totalVisitTarget,context);
            passtwo(target,entities,context);
        });
        target.getTotalVisitTargets().forEach( totalVisitTarget  ->   {
            List<IAppointmentEntity> entities =  getEntities(totalVisitTarget,context);
            passThree(target,entities,context);
        });
        target.getTotalVisitTargets().forEach( totalVisitTarget  ->   {
            List<IAppointmentEntity> entities =  getEntities(totalVisitTarget,context);
            passFour(target,entities,context);
        });





    }

        Map<IAppointmentEntity,List<AppointmentUnit>> appointmentUnitMap  = new HashMap < IAppointmentEntity,List<AppointmentUnit>>();

        Map<User,List<AppointmentUnit>> agentAppointmentUnitMap  = new HashMap < User,List<AppointmentUnit>>();

        int dayofWeeks[] ={ 1,2,3,4,5,6}  ;
        String timeofDays[] = {"10.00" , "11.00"  , "12.00"  , "13.00" ,  "14.00" , "15.00" , "16.00" , "17.00"} ;


         private AppointmentUnit createApptUnit(IAppointmentEntity entity , Date curDate, User agent ) {
             return null ;
         }

         private boolean closeSlots(Timestamp t1, Timestamp t2)
         {
             if(Math.abs(t1.getTime() - t2.getTime()) < (3600 * 1000) ) return true;
             return false;
         }

         private boolean isFeasibleForAgent (AppointmentUnit appointmentUnit)
         {
             AtomicBoolean isFeasible = new AtomicBoolean(true) ;
             List<AppointmentUnit> agentUnits =agentAppointmentUnitMap.get(appointmentUnit.getAgent())       ;
             if(agentUnits != null )   {
                agentUnits.forEach( agentUnit ->  {
                    if (  closeSlots(appointmentUnit.getApptTime(),agentUnit.getApptTime()) ){
                        isFeasible.set(false);
                    }

                });
             }
             return isFeasible.get();
         }



         public void saveForUse(AppointmentUnit appointmentUnit)
         {
             List units=   appointmentUnitMap.get(appointmentUnit.getEntity());
             if(units == null) {
                 units = new ArrayList()  ;
             }
             units.add(appointmentUnit);
             appointmentUnitMap.put(appointmentUnit.getEntity(),units) ;

             List agentUnits =agentAppointmentUnitMap.get(appointmentUnit.getAgent())       ;
             if (agentUnits==null){
                 agentUnits =new ArrayList() ;
             }
             agentUnits.add(appointmentUnit);
             agentAppointmentUnitMap.put(appointmentUnit.getAgent(),agentUnits) ;



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

        private List<Calendar> getPossibleTimings (Date startDate , Date endDate  ,  IAppointmentEntity appointmentEntity, int roundRobinDay, int roundRobinTime)
        {
            Calendar retCalendar = new GregorianCalendar();
            Map <Calendar , Integer > calendarSores =  new HashMap<>() ;
            //Collections.sort((List<AppointmentPreference>) appointmentEntity.getAppointmentPreferences());
            List<Calendar>  calendars = new ArrayList<>()  ;
            Date curDate = startDate  ;
            while(curDate.before(endDate))  {
                int score  = -1 ;
                Calendar iterateDate  = new GregorianCalendar();
                iterateDate.setTime(curDate);
                if(iterateDate.get(Calendar.DAY_OF_WEEK) ==  roundRobinDay)  {
                    score ++  ;
                }
                if(iterateDate.get(Calendar.HOUR_OF_DAY) ==  roundRobinTime)  {
                    score ++  ;
                }

                curDate.setTime(curDate.getTime()   + (24 * 60 *  1000 ));

            }

            return calendars ;
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
                    curDate.setTime(curDate.getTime()   + (24 * 60 * 60 * 1000 ));
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
    public void passOne(Target target , List<IAppointmentEntity> entities , ProductContext context  )
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
        return ServiceFactory.getUserService().getAllDirectReportees(target.getManager(),context) ;

    }

    /**
     *  Preferred Time Non Preffered Agent
     * @param target
     * @param entities
     * @param context
     */
    public void passtwo(Target target , List<IAppointmentEntity> entities , ProductContext context  )
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

     */
    public void passThree(Target target ,List<IAppointmentEntity> entities , ProductContext context )
    {
        entities.forEach( entity ->  {
            List<TimeRange> intervals  = getInterval(entity,target);
            intervals.forEach( interval -> {     //Date iteration

                User preferredAgent = getPreferredAgent(entity,target)   ;
                List<Calendar> calendars  =getPossibleTimings(interval.getStart(),interval.getEnd(),entity,-1,-1) ;
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

    public void passFour(Target target ,List<IAppointmentEntity> entities , ProductContext context  )
    {
        entities.forEach( entity ->  {
            List<TimeRange> intervals  = getInterval(entity,target);
            intervals.forEach( interval -> {     //Date iteration

                List<User> allAgents=  getAllAgents(target, context);
                List<Calendar> calendars  =getPossibleTimings(interval.getStart(),interval.getEnd(),entity,-1,-1) ;
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
