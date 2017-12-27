package com.primus.profiles.service;

import com.primus.common.ProductContext;
import com.primus.crm.appointment.model.Appointment;
import com.primus.crm.appointment.model.PrescriptionSurvey;
import com.primus.crm.appointment.service.AppointmentService;
import com.primus.externals.doctor.model.Doctor;
import com.primus.externals.doctor.service.DoctorService;
import com.primus.externals.doctor.validator.DoctorValidator;
import com.primus.profiles.model.DoctorProfile;
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
