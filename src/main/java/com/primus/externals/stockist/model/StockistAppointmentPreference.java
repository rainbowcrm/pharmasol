package com.primus.externals.stockist.model;

import com.primus.common.appointmentpreference.model.AppointmentPreference;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

import javax.persistence.*;

@Entity
@Table(name = "STOCKIST_PREF_APPT_TIMES")
@AttributeOverrides({
        @AttributeOverride(name="ID", column=@Column(name="ID")),
        @AttributeOverride(name="createdDate", column=@Column(name="CREATED_DATE")),
        @AttributeOverride(name="createdBy", column=@Column(name="CREATED_BY")),
        @AttributeOverride(name="lastUpdateDate", column=@Column(name="LAST_UPDATED_DATE")),
        @AttributeOverride(name="lastUpdatedBy", column=@Column(name="LAST_UPDATED_BY")),
        @AttributeOverride(name="version", column=@Column(name="VERSION"))

})
public class StockistAppointmentPreference extends AppointmentPreference {


    Stockist stockist ;

    @ManyToOne(cascade= CascadeType.DETACH  )
    @JoinColumn(name  ="STOCKIST_ID" )
    @RadsPropertySet(excludeFromMap = true, excludeFromJSON = true, excludeFromXML = true)
    public Stockist getStockist() {
        return stockist;
    }

    @RadsPropertySet(excludeFromMap = true, excludeFromJSON = true, excludeFromXML = true)
    public void setStockist(Stockist stockist) {
        this.stockist = stockist;
    }
}

