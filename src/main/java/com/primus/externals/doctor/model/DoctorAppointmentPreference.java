package com.primus.externals.doctor.model;

import com.primus.common.appointmentpreference.model.AppointmentPreference;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

import javax.persistence.*;

@Entity
@Table(name = "DOCTOR_PREF_APPT_TIMES")
@AttributeOverrides({
        @AttributeOverride(name="ID", column=@Column(name="ID")),
        @AttributeOverride(name="createdDate", column=@Column(name="CREATED_DATE")),
        @AttributeOverride(name="createdBy", column=@Column(name="CREATED_BY")),
        @AttributeOverride(name="lastUpdateDate", column=@Column(name="LAST_UPDATED_DATE")),
        @AttributeOverride(name="lastUpdatedBy", column=@Column(name="LAST_UPDATED_BY")),
        @AttributeOverride(name="version", column=@Column(name="VERSION"))

})

public class DoctorAppointmentPreference extends AppointmentPreference {


    Doctor doctor ;

    @ManyToOne(cascade=CascadeType.DETACH  )
    @JoinColumn(name  ="DOCTOR_ID" )
    @RadsPropertySet(excludeFromMap = true, excludeFromJSON = true, excludeFromXML = true)
    public Doctor getDoctor() {
        return doctor;
    }

    @RadsPropertySet(excludeFromMap = true, excludeFromJSON = true, excludeFromXML = true)
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
