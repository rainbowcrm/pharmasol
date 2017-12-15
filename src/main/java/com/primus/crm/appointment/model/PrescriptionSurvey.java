package com.primus.crm.appointment.model;

import com.primus.abstracts.PrimusBusinessModel;
import com.primus.externals.doctor.model.Doctor;
import com.primus.merchandise.item.model.Sku;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

import javax.persistence.*;
import javax.print.Doc;
import java.util.Date;


@Entity
@Table(name = "PRESCRIPTION_SURVERY")
@AttributeOverrides({
        @AttributeOverride(name = "ID", column = @Column(name = "ID")),
        @AttributeOverride(name = "createdDate", column = @Column(name = "CREATED_DATE")),
        @AttributeOverride(name = "createdBy", column = @Column(name = "CREATED_BY")),
        @AttributeOverride(name = "lastUpdateDate", column = @Column(name = "LAST_UPDATED_DATE")),
        @AttributeOverride(name = "lastUpdatedBy", column = @Column(name = "LAST_UPDATED_BY")),
        @AttributeOverride(name = "version", column = @Column(name = "VERSION"))

})
public class PrescriptionSurvey extends PrimusBusinessModel {

    Appointment appointment ;
    Sku sku;
    Doctor doctor;
    String doctorName;
    Date prescriptionDate;
    double qty;
    String description;


    @RadsPropertySet(excludeFromJSON = true, excludeFromXML = true, excludeFromMap = true ,isBK = true)
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "APPOINTMENT_ID")
    public Appointment getAppointment() {
        return appointment;
    }

    @RadsPropertySet(excludeFromJSON = true, excludeFromXML = true, excludeFromMap = true ,isBK = true)
    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    @RadsPropertySet(useBKForJSON = true, useBKForMap = true, useBKForXML = true ,isBK = true)
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "SKU_ID")
    public Sku getSku() {
        return sku;
    }

    @RadsPropertySet(useBKForJSON = true, useBKForMap = true, useBKForXML = true ,isBK = true)
    public void setSku(Sku sku) {
        this.sku = sku;
    }

    @RadsPropertySet(useBKForJSON = true, useBKForMap = true, useBKForXML = true )
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "DOCTOR_ID")
    public Doctor getDoctor() {
        return doctor;
    }

    @RadsPropertySet(useBKForJSON = true, useBKForMap = true, useBKForXML = true )
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    @Column(name = "DOCTOR_NAME")
    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    @Column(name = "PRESC_DATE")
    public Date getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(Date prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    @Column(name = "QTY")
    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
