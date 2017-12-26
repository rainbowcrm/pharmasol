package com.primus.profiles.service;

import com.primus.common.ProductContext;
import com.primus.crm.appointment.model.Appointment;
import com.primus.crm.appointment.service.AppointmentService;
import com.primus.externals.doctor.model.Doctor;
import com.primus.externals.doctor.service.DoctorService;
import com.primus.profiles.model.DoctorProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProfileService {

    @Autowired
    DoctorService doctorService ;

    @Autowired
    AppointmentService appointmentService;

    public DoctorProfile getDoctorProfile(Doctor doctor, ProductContext context)
    {
        DoctorProfile profile = new DoctorProfile();
        doctor = (Doctor)doctorService.getById(doctor.getId()) ;
        List<Appointment> appointments = appointmentService.getDoctorAppointments(doctor,null,context) ;
        profile.setDoctor(doctor);
        profile.setAppointments(appointments);
        profile.setPromotedItems( new ArrayList<>());
        appointments.forEach(appointment ->  {
           /* profile.getPromotedItems().addAll(appointment.getPromotedItems().stream().filter(
                    promotedItem -> {!promotedItem.isDeleted()}).collect(Collectors.toCollection()));*/
        });

        return profile ;


    }
}
