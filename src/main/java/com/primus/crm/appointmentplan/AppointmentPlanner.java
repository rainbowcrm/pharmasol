package com.primus.crm.appointmentplan;

import com.primus.common.FVConstants;
import com.primus.common.Logger;
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

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class AppointmentPlanner{


    Map<IAppointmentEntity,List<AppointmentUnit>> appointmentUnitMap  = new HashMap < IAppointmentEntity,List<AppointmentUnit>>();

    Map<User,List<AppointmentUnit>> agentAppointmentUnitMap  = new HashMap < User,List<AppointmentUnit>>();

    Map<User,AtomicInteger> agentCounts=new HashMap<>() ;
    Map<Integer,AtomicInteger> dayCount =  new HashMap<> ();
    Map<Integer,AtomicInteger> timeCount =  new HashMap<> ();
    Map<IAppointmentEntity,Integer> entityExpectedApptCount= new HashMap<> ();


    private  List<IAppointmentEntity> getEntities(TotalVisitTarget totalVisitTarget,ProductContext context)
    {
        List<IAppointmentEntity> entities = new ArrayList<>();

        if (FVConstants.VISIT_TO.ALL_STORE.equalsIgnoreCase(totalVisitTarget.getVisitingType().getCode())) {
            StoreService service  =  ServiceFactory.getStoreService() ;
            List allStores = service.fetchAllActive( " where  storeAssociation.location.id =  " + totalVisitTarget.getTarget().getLocation().getId(), "" ,context  );
            entities.addAll(allStores);
        }

        if (FVConstants.VISIT_TO.ALL_DOCTOR.equalsIgnoreCase(totalVisitTarget.getVisitingType().getCode())) {
            DoctorService service  =  ServiceFactory.getDoctorService();
            List allDoctors = service.fetchAllActive( " where  doctorAssociation.location.id =  " + totalVisitTarget.getTarget().getLocation().getId(), "" ,context  );
            entities.addAll(allDoctors);
        }

        if (FVConstants.VISIT_TO.ALL_STOCKIST.equalsIgnoreCase(totalVisitTarget.getVisitingType().getCode())) {
            StockistService service  =  ServiceFactory.getStockistService();
            List allStockists = service.fetchAllActive( "  where stockistAssociation.location.id =  " + totalVisitTarget.getTarget().getLocation().getId(), "" ,context  );
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

    private void initCounters()
    {
        dayCount.put(new Integer(1),new AtomicInteger(0));
        dayCount.put(new Integer(2),new AtomicInteger(0));
        dayCount.put(new Integer(3),new AtomicInteger(0));
        dayCount.put(new Integer(4),new AtomicInteger(0));
        dayCount.put(new Integer(5),new AtomicInteger(0));
        dayCount.put(new Integer(6),new AtomicInteger(0));

        timeCount.put(new Integer(0),new AtomicInteger(0));
        timeCount.put(new Integer(1),new AtomicInteger(0));
        timeCount.put(new Integer(2),new AtomicInteger(0));
        timeCount.put(new Integer(3),new AtomicInteger(0));
        timeCount.put(new Integer(4),new AtomicInteger(0));
        timeCount.put(new Integer(5),new AtomicInteger(0));
        timeCount.put(new Integer(6),new AtomicInteger(0));
        timeCount.put(new Integer(7),new AtomicInteger(0));
        timeCount.put(new Integer(8),new AtomicInteger(0));
        timeCount.put(new Integer(9),new AtomicInteger(0));
        timeCount.put(new Integer(10),new AtomicInteger(0));
        timeCount.put(new Integer(11),new AtomicInteger(0));
        timeCount.put(new Integer(12),new AtomicInteger(0));
        timeCount.put(new Integer(13),new AtomicInteger(0));
        timeCount.put(new Integer(14),new AtomicInteger(0));
        timeCount.put(new Integer(15),new AtomicInteger(0));
        timeCount.put(new Integer(16),new AtomicInteger(0));
        timeCount.put(new Integer(17),new AtomicInteger(0));
        timeCount.put(new Integer(18),new AtomicInteger(0));



    }


    private boolean isCountReached(IAppointmentEntity entity) {
        return  appointmentUnitMap.get(entity)!=null && appointmentUnitMap.get(entity).size() >=entityExpectedApptCount.get(entity).intValue() ;
    }


    public  void generateAppointments (Target target, ProductContext context)
    {

        initCounters();
        List<IAppointmentEntity> entities  = new ArrayList<>() ;
        Set<TotalVisitTarget> totalVisitTargets = sortTargetVisits( (List)target.getTotalVisitTargets()) ;
        totalVisitTargets.forEach( totalVisitTarget  ->   {
            List<IAppointmentEntity> selectEntities =  getEntities(totalVisitTarget,context);
            selectEntities.forEach( selectEntity -> {
                if (!entities.contains(selectEntity)) {
                    entities.add(selectEntity);
                }
                entityExpectedApptCount.put(selectEntity,new Integer(totalVisitTarget.getTargettedVisit())) ;

            } );


        });
        passOne(target,entities,context);
        passtwo(target,entities,context);
        passThree(target,entities,context);
        passFour(target,entities,context);


        appointmentUnitMap.keySet().forEach(  appointmentEntity ->    {
                List< AppointmentUnit >  appointmentUnits =  appointmentUnitMap.get(appointmentEntity) ;

            Logger.logDebug(appointmentEntity.getName(),this.getClass());
            appointmentUnits.forEach(appointmentUnit ->   {
                Logger.logDebug(appointmentUnit.shortDisplay(),this.getClass());
            });

        });


    }




         private AppointmentUnit createApptUnit(IAppointmentEntity entity , Date curDate,   User agent, Target target ) {
             AppointmentUnit appointmentUnit = new AppointmentUnit(entity,agent,target.getManager(),new java.sql.Timestamp(curDate.getTime()),60);
             return appointmentUnit ;
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

             SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

             String timeString = sdf.format(appointmentUnit.getApptTime()) ;
             String hhPart = timeString.substring(0,2);
             String mmPart = timeString.substring(3,5);


             AtomicInteger counter  =  timeCount.get(new Integer((Integer.parseInt(hhPart)))) ;
             counter.incrementAndGet() ;
             Calendar calendar = new GregorianCalendar();
             calendar.setTime(appointmentUnit.getApptTime());
             int dayOfWeek=  calendar.get(Calendar.DAY_OF_WEEK);

             AtomicInteger counterDay  =  dayCount.get(new Integer((dayOfWeek))) ;
             counterDay.incrementAndGet();

             AtomicInteger counterAgents = agentCounts.get(appointmentUnit.getAgent());
             if(counterAgents  ==  null) {
                 counterAgents = new AtomicInteger(1);
                 agentCounts.put(appointmentUnit.getAgent(),counterAgents);
             }else
                 counterAgents.incrementAndGet();


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
             Date startTime =  new java.util.Date (target.getFromDate().getTime()) ;
             for(int i = 0 ; i  < count ; i  ++  )  {
                 TimeRange range = new TimeRange(new Date(startTime.getTime()),new Date(startTime.getTime() + timeinterval)) ;
                 startTime.setTime(startTime.getTime() + timeinterval);
                 ranges.add(range);
             }

             return ranges;
         }


    private   Set<TotalVisitTarget> sortTargetVisits(List <TotalVisitTarget> listToBeSorted)
    {
        Map<TotalVisitTarget,String> mapToBeSorted  = new HashMap<>();
        for (TotalVisitTarget totalVisitTarget : listToBeSorted) {
            mapToBeSorted.put(totalVisitTarget,totalVisitTarget.getVisitingType().getCode());
        }
        List<Map.Entry<TotalVisitTarget, String>> list = new LinkedList<Map.Entry<TotalVisitTarget, String>>(mapToBeSorted.entrySet());
        // Sorting the list based on valueents
        Collections.sort(list, new Comparator<Map.Entry<TotalVisitTarget, String>>()
        {
            public int compare(Map.Entry<TotalVisitTarget, String> o1,
                               Map.Entry<TotalVisitTarget, String> o2)
            {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<TotalVisitTarget, String> sortedMap = new LinkedHashMap<TotalVisitTarget, String>();
        for (Map.Entry<TotalVisitTarget, String> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap.keySet();

    }

    private Set< User> sortAgentByCount (Map<User,AtomicInteger> mapToBeSorted)
    {
        List<Map.Entry<User, AtomicInteger>> list = new LinkedList<Map.Entry<User, AtomicInteger>>(mapToBeSorted.entrySet());
        // Sorting the list based on valueents
        Collections.sort(list, new Comparator<Map.Entry<User, AtomicInteger>>()
        {
            public int compare(Map.Entry<User, AtomicInteger> o1,
                               Map.Entry<User, AtomicInteger> o2)
            {
                return (o2.getValue().get()) > (o1.getValue().get())?1:0;
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<User, AtomicInteger> sortedMap = new LinkedHashMap<User, AtomicInteger>();
        for (Map.Entry<User, AtomicInteger> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap.keySet();
    }


    private Set< Integer> sortByCount (Map<Integer,AtomicInteger> mapToBeSorted)
         {
             List<Map.Entry<Integer, AtomicInteger>> list = new LinkedList<Map.Entry<Integer, AtomicInteger>>(mapToBeSorted.entrySet());
             // Sorting the list based on values
             Collections.sort(list, new Comparator<Map.Entry<Integer, AtomicInteger>>()
             {
                 public int compare(Map.Entry<Integer, AtomicInteger> o1,
                                    Map.Entry<Integer, AtomicInteger> o2)
                 {
                         return (o2.getValue().get()) < (o1.getValue().get())?1:0;
                 }
             });

             // Maintaining insertion order with the help of LinkedList
             Map<Integer, AtomicInteger> sortedMap = new LinkedHashMap<Integer, AtomicInteger>();
             for (Map.Entry<Integer, AtomicInteger> entry : list)
             {
                 sortedMap.put(entry.getKey(), entry.getValue());
             }

             return sortedMap.keySet();
         }

        private List<Calendar> getPossibleTimings (Date startDate , Date endDate  ,  IAppointmentEntity appointmentEntity, int roundRobinDay, int roundRobinTime)
        {
            List<Calendar> newCalendars =  new ArrayList<>() ;
            Set< Integer> sortedDays =  sortByCount(dayCount)  ;
            Set<Integer> sortedTimings = sortByCount(timeCount)  ;
            Map<Calendar,AtomicInteger>  calendarScores=  new LinkedHashMap<>() ;
            Date curDate = startDate  ;
            long MAXDAYSCORE =  100 +  sortedDays.size()  ;
            long MAXTIMESCORE=  sortedTimings.size() ;

            long dayScore = MAXDAYSCORE ;

                for (Integer day : sortedDays) {
                    long timeScore= MAXTIMESCORE ;
                    for (Integer timing : sortedTimings) {
                        while(curDate.before(endDate)) {
                            Calendar currentDay = new GregorianCalendar()  ;
                            curDate.setTime(curDate.getTime()   + (24 * 60 *  60 *  1000 ));
                            Date curTime = new java.util.Date() ;
                            curTime.setTime(curDate.getTime()  + (10 * 60 * 60 * 1000 ));
                            currentDay.setTime(curTime);
                            if(currentDay.get(Calendar.DAY_OF_WEEK) == 7){
                                continue;
                            }
                                for (int hh = 10 ; hh <= 17 ; hh ++) {
                                    int currentScore =  1;
                                     if (hh == timing.intValue()) {
                                         currentScore *=  timeScore ;
                                     }
                                     if(currentDay.get(Calendar.DAY_OF_WEEK) == day.intValue()){
                                         currentScore *=  dayScore ;
                                     }
                                     if(currentScore > 1)  {
                                         currentDay.setTime(new java.util.Date(curDate.getTime()  +  (hh * 60 * 60 * 1000 ) ));
                                         newCalendars.add(currentDay) ;
                                     }

                                }
                        }
                        timeScore --;
                    }
                    dayScore--  ;
                }
                return newCalendars ;




            /*Calendar retCalendar = new GregorianCalendar();
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

            return calendars ;*/
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
                        String timeString = sdf.format(preference.getPreferredTime()) ;
                        String hhPart = timeString.substring(0,2);
                        String mmPart = timeString.substring(3,5);
                        iterateDate.set(Calendar.HOUR_OF_DAY,Integer.parseInt(hhPart));
                        iterateDate.set(Calendar.MINUTE,Integer.parseInt(mmPart));
                        return iterateDate ;
                    }
                    curDate.setTime(curDate.getTime()   + (24 * 60 * 60 * 1000 ));
                }
             }
             return null;
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
            if( ! isCountReached(entity)) {
                List<TimeRange> intervals = getInterval(entity, target);
                intervals.forEach(interval -> {     //Date iteration

                    User preferredAgent = getPreferredAgent(entity, target);
                    Calendar calendar = getPreferredTime(interval.getStart(), interval.getEnd(), entity);
                    if (calendar != null) {
                        AppointmentUnit appointmentUnit = createApptUnit(entity, calendar.getTime(), preferredAgent, target);
                        if (isFeasibleForAgent(appointmentUnit)) {
                            saveForUse(appointmentUnit);
                        }
                    }
                });
            }

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
                    if( ! isCountReached(entity)) {
                        List<TimeRange> intervals = getInterval(entity, target);
                        intervals.forEach(interval -> {     //Date iteration
                            User preferredAgent = getPreferredAgent(entity, target);
                            List<User> allAgents = getAllAgents(target, context);
                            for (User agent : allAgents) {
                                Calendar calendar = getPreferredTime(interval.getStart(), interval.getEnd(), entity);
                                if (calendar != null) {
                                    AppointmentUnit appointmentUnit = createApptUnit(entity, calendar.getTime(), agent, target);
                                    if (isFeasibleForAgent(appointmentUnit)) {
                                        saveForUse(appointmentUnit);
                                        break;
                                    }
                                }
                            }
                        });
                    }
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
            if( ! isCountReached(entity)) {
                List<TimeRange> intervals = getInterval(entity, target);
                intervals.forEach(interval -> {     //Date iteration

                    User preferredAgent = getPreferredAgent(entity, target);
                    List<Calendar> calendars = getPossibleTimings(interval.getStart(), interval.getEnd(), entity, -1, -1);
                    for (Calendar calendar : calendars) {
                        AppointmentUnit appointmentUnit = createApptUnit(entity, calendar.getTime(), preferredAgent, target);
                        if (isFeasibleForAgent(appointmentUnit)) {
                            saveForUse(appointmentUnit);
                            break;
                        }
                    }
                });
            }

        });

    }

    public void passFour(Target target ,List<IAppointmentEntity> entities , ProductContext context  )
    {
        entities.forEach( entity ->  {
            if( ! isCountReached(entity)) {
                List<TimeRange> intervals = getInterval(entity, target);
                intervals.forEach(interval -> {     //Date iteration

                    List<User> allAgents = getAllAgents(target, context);
                    List<Calendar> calendars = getPossibleTimings(interval.getStart(), interval.getEnd(), entity, -1, -1);
                    outer:
                    for (Calendar calendar : calendars) {
                        for (User agent : allAgents) {

                            AppointmentUnit appointmentUnit = createApptUnit(entity, calendar.getTime(), agent, target);
                            if (isFeasibleForAgent(appointmentUnit)) {
                                saveForUse(appointmentUnit);
                                break outer;
                            }
                        }
                    }
                });
            }
        });


    }





}
