package com.primus.profiles.model;

import com.primus.abstracts.PrimusModel;
import com.primus.crm.appointment.model.Appointment;
import com.primus.crm.appointment.model.PrescriptionSurvey;
import com.primus.crm.appointment.model.PromotedItem;
import com.primus.externals.doctor.model.Doctor;

import java.util.List;

public class DoctorProfile extends PrimusModel {

    Doctor doctor ;
    List<PromotedItem> promotedItems ;
    List<PrescriptionSurvey> prescriptionSurveys ;
    List<Appointment> appointments ;

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public List<PromotedItem> getPromotedItems() {
        return promotedItems;
    }

    public void setPromotedItems(List<PromotedItem> promotedItems) {
        this.promotedItems = promotedItems;
    }

    public List<PrescriptionSurvey> getPrescriptionSurveys() {
        return prescriptionSurveys;
    }

    public void setPrescriptionSurveys(List<PrescriptionSurvey> prescriptionSurveys) {
        this.prescriptionSurveys = prescriptionSurveys;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
