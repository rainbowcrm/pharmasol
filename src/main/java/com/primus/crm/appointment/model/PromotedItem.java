package com.primus.crm.appointment.model;

import com.primus.abstracts.PrimusBusinessModel;
import com.primus.common.FiniteValue;
import com.primus.merchandise.item.model.Item;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

import javax.persistence.*;

@Entity
@Table(name = "APPT_ITEMS_PROMOTED")
@AttributeOverrides({
        @AttributeOverride(name = "ID", column = @Column(name = "ID")),
        @AttributeOverride(name = "createdDate", column = @Column(name = "CREATED_DATE")),
        @AttributeOverride(name = "createdBy", column = @Column(name = "CREATED_BY")),
        @AttributeOverride(name = "lastUpdateDate", column = @Column(name = "LAST_UPDATED_DATE")),
        @AttributeOverride(name = "lastUpdatedBy", column = @Column(name = "LAST_UPDATED_BY")),
        @AttributeOverride(name = "version", column = @Column(name = "VERSION"))

})

public class PromotedItem extends PrimusBusinessModel {

Appointment appointment;
Item item;
FiniteValue recommendedBy;
String description ;
String promoDetails;


    @RadsPropertySet(excludeFromJSON = true, excludeFromXML = true, excludeFromMap = true)
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "APPOINTMENT_ID")
    public Appointment getAppointment() {
        return appointment;
    }

    @RadsPropertySet(excludeFromJSON = true, excludeFromXML = true, excludeFromMap = true)
    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    @RadsPropertySet(useBKForJSON = true, useBKForMap = true, useBKForXML = true)
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "ITEM_ID")
    public Item getItem() {
        return item;
    }

    @RadsPropertySet(useBKForJSON = true, useBKForMap = true, useBKForXML = true)
    public void setItem(Item item) {
        this.item = item;
    }

    @RadsPropertySet(useBKForJSON = true, useBKForMap = true, useBKForXML = true)
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "PROMO_RECOMMENDED_BY")
    public FiniteValue getRecommendedBy() {
        return recommendedBy;
    }

    public void setRecommendedBy(FiniteValue recommendedBy) {
        this.recommendedBy = recommendedBy;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "PROMO_DETAILS")
    public String getPromoDetails() {
        return promoDetails;
    }

    public void setPromoDetails(String promoDetails) {
        this.promoDetails = promoDetails;
    }
}
