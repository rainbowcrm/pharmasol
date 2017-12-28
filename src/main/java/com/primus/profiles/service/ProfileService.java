package com.primus.profiles.service;

import com.primus.common.ProductContext;
import com.primus.crm.appointment.model.Appointment;
import com.primus.crm.appointment.model.PrescriptionSurvey;
import com.primus.crm.appointment.service.AppointmentService;
import com.primus.externals.doctor.model.Doctor;
import com.primus.externals.doctor.service.DoctorService;
import com.primus.externals.doctor.validator.DoctorValidator;
import com.primus.externals.store.model.Store;
import com.primus.externals.store.service.StoreService;
import com.primus.externals.store.validator.StoreValidator;
import com.primus.profiles.model.DoctorProfile;
import com.primus.profiles.model.StoreProfile;
import org.hibernate.boot.jaxb.hbm.internal.CacheAccessTypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProfileService {

    @Autowired
    DoctorService doctorService ;

    @Autowired
    DoctorValidator doctorValidator ;

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    StoreService storeService ;

    @Autowired
    StoreValidator storeValidator ;

    public StoreProfile getStoreProfile(Store store, ProductContext context)
    {
        StoreProfile profile = new StoreProfile();
        store = (Store) storeService.getFullObject(store,context) ;
        storeValidator.adaptToUI(store,context) ;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,1990);
        calendar.set(Calendar.MONTH,1);
        calendar.set(Calendar.DAY_OF_MONTH,1);

        List<Appointment> appointments = appointmentService.getStoreAppointments(store,calendar.getTime(),context) ;
        profile.setStore(store);
        profile.setAppointments(appointments);

        profile.setPrescriptionSurveys( new ArrayList<>());
        profile.setStoreVisitOrderLines( new ArrayList<>());
        profile.setCompetitorSalesLines(new ArrayList<>());
        appointments.forEach(appointment ->  {

            profile.getPrescriptionSurveys().addAll(
                    appointment.getPrescriptionSurveys().stream().filter(
                            survey -> !survey.isDeleted()).collect(Collectors.toList())
            );
            profile.getStoreVisitOrderLines().addAll(
                    appointment.getOrderLines().stream().filter(
                            orderLine -> !orderLine.isDeleted()).collect(Collectors.toList())
            );

            profile.getCompetitorSalesLines().addAll(
                    appointment.getCompetitorSalesLines().stream().filter(
                            competitorSalesLine -> !competitorSalesLine.isDeleted()).collect(Collectors.toList())
            );

        });

        return  profile ;

    }
    public DoctorProfile getDoctorProfile(Doctor doctor, ProductContext context)
    {
        DoctorProfile profile = new DoctorProfile();
        doctor = (Doctor)doctorService.getFullObject(doctor,context) ;

        doctorValidator.adaptToUI(doctor,context);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,1990);
        calendar.set(Calendar.MONTH,1);
        calendar.set(Calendar.DAY_OF_MONTH,1);

        List<Appointment> appointments = appointmentService.getDoctorAppointments(doctor,calendar.getTime(),context) ;
        profile.setDoctor(doctor);
        profile.setAppointments(appointments);
        profile.setPromotedItems( new ArrayList<>());
        appointments.forEach(appointment ->  {
           /* profile.getPromotedItems().addAll(appointment.getPromotedItems().stream().filter(
                    promotedItem -> {!promotedItem.isDeleted()}).collect(Collectors.toCollection()));*/
            profile.getPromotedItems().addAll(
            appointment.getPromotedItems().stream().filter(
                    promotedItem -> !promotedItem.isDeleted()).collect(Collectors.toList())
            );
        });

        List<PrescriptionSurvey> surveys =   appointmentService.getDoctorSurveys(doctor,calendar.getTime(),context);
        profile.setPrescriptionSurveys(surveys);

        return profile ;


    }
}
