package com.primus.externals.store.model;

import com.primus.common.appointmentpreference.model.AppointmentPreference;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

import javax.persistence.*;

@Entity
@Table(name = "STORE_PREF_APPT_TIMES")
@AttributeOverrides({
        @AttributeOverride(name="ID", column=@Column(name="ID")),
        @AttributeOverride(name="createdDate", column=@Column(name="CREATED_DATE")),
        @AttributeOverride(name="createdBy", column=@Column(name="CREATED_BY")),
        @AttributeOverride(name="lastUpdateDate", column=@Column(name="LAST_UPDATED_DATE")),
        @AttributeOverride(name="lastUpdatedBy", column=@Column(name="LAST_UPDATED_BY")),
        @AttributeOverride(name="version", column=@Column(name="VERSION"))

})

public class StoreAppointmentPreference extends AppointmentPreference {


    Store store ;

    @ManyToOne(cascade=CascadeType.DETACH  )
    @JoinColumn(name  ="STORE_ID" )
    @RadsPropertySet(excludeFromMap = true, excludeFromJSON = true, excludeFromXML = true)
    public Store getDoctor() {
        return store;
    }

    @RadsPropertySet(excludeFromMap = true, excludeFromJSON = true, excludeFromXML = true)
    public void setDoctor(Store store) {
        this.store = store;
    }
}
