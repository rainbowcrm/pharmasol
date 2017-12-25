package com.primus.crm.appointment.model;

import com.primus.abstracts.PrimusBusinessModel;
import com.primus.common.FiniteValue;
import com.primus.merchandise.item.model.Sku;
import com.primus.merchandise.item.model.UOM;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "STOCKIST_VISIT_ORDER_LINES")
@AttributeOverrides({
        @AttributeOverride(name = "ID", column = @Column(name = "ID")),
        @AttributeOverride(name = "createdDate", column = @Column(name = "CREATED_DATE")),
        @AttributeOverride(name = "createdBy", column = @Column(name = "CREATED_BY")),
        @AttributeOverride(name = "lastUpdateDate", column = @Column(name = "LAST_UPDATED_DATE")),
        @AttributeOverride(name = "lastUpdatedBy", column = @Column(name = "LAST_UPDATED_BY")),
        @AttributeOverride(name = "version", column = @Column(name = "VERSION"))

})
public class StockistVisitOrderLine extends PrimusBusinessModel {

    Appointment appointment ;
    Sku sku;
    double qty;
    String description;
    UOM uom;
    double rate;
    Date requiredDate;
    FiniteValue lineStatus;

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

    @RadsPropertySet(useBKForJSON = true, useBKForMap = true, useBKForXML = true ,isBK = true)
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "UOM_ID")
    public UOM getUom() {
        return uom;
    }

    @RadsPropertySet(useBKForJSON = true, useBKForMap = true, useBKForXML = true ,isBK = true)
    public void setUom(UOM uom) {
        this.uom = uom;
    }

    @Column(name = "RATE")
    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Column(name = "REQD_DATE")
    public Date getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(Date requiredDate) {
        this.requiredDate = requiredDate;
    }

    @RadsPropertySet(useBKForJSON = true, useBKForMap = true, useBKForXML = true)
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "LINE_STATUS")
    public FiniteValue getLineStatus() {
        return lineStatus;
    }

    @RadsPropertySet(useBKForJSON = true, useBKForMap = true, useBKForXML = true)
    public void setLineStatus(FiniteValue lineStatus) {
        this.lineStatus = lineStatus;
    }

    @Transient
    public boolean isEmpty ()
    {
        return  (sku == null || ( sku.getName() == null &&  sku.getCode() == null) );
    }
}
