package com.primus.crm.appointment.model;

import com.primus.abstracts.PrimusBusinessModel;
import com.primus.externals.competitor.model.Competitor;
import com.primus.merchandise.category.model.Category;
import com.techtrade.rads.framework.annotations.RadsPropertySet;
import com.techtrade.rads.framework.utils.Utils;

import javax.persistence.*;

@Entity
@Table(name = "COMPETITOR_SALES")
@AttributeOverrides({
        @AttributeOverride(name = "ID", column = @Column(name = "ID")),
        @AttributeOverride(name = "createdDate", column = @Column(name = "CREATED_DATE")),
        @AttributeOverride(name = "createdBy", column = @Column(name = "CREATED_BY")),
        @AttributeOverride(name = "lastUpdateDate", column = @Column(name = "LAST_UPDATED_DATE")),
        @AttributeOverride(name = "lastUpdatedBy", column = @Column(name = "LAST_UPDATED_BY")),
        @AttributeOverride(name = "version", column = @Column(name = "VERSION"))

})
public class CompetitorSalesLine extends PrimusBusinessModel {

    Appointment appointment ;
    Category category ;
    Competitor competitor ;
    String medicine;
    String description;
    double qty ;
    double amount;

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
    @JoinColumn(name = "CATEGORY_ID")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @RadsPropertySet(useBKForJSON = true, useBKForMap = true, useBKForXML = true ,isBK = true)
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "COMPETITOR_ID")
    public Competitor getCompetitor() {
        return competitor;
    }

    public void setCompetitor(Competitor competitor) {
        this.competitor = competitor;
    }

    @Column(name = "MEDICINE")
    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "QTY")
    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    @Column(name = "AMOUNT")
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Transient
    public boolean isEmpty ()
    {
        return  ((category == null ||  category.getId() <0 ) &&  (competitor == null || Utils.isNull(competitor.getName()) ) );
    }
}
